package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;
import cn.com.lixihao.couponapi.entity.condition.ReleaseConditon;
import cn.com.lixihao.couponapi.entity.condition.StatCondition;
import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;
import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.ReleaseResponse;
import cn.com.lixihao.couponapi.entity.result.ReleaseStatusType;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.helper.GenerateIdentifier;
import cn.com.lixihao.couponapi.dao.ReceivingRestrictionDao;
import cn.com.lixihao.couponapi.dao.ReleaseDao;
import cn.com.lixihao.couponapi.dao.StatDao;
import cn.com.lixihao.couponapi.dao.TemplateDao;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private StatDao statDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private ReceivingRestrictionDao receivingRestrictionDao;

    public JSONObject get(ReleaseConditon releaseConditon, String get_type) {
        JSONObject jsonObject = new JSONObject();
        ReleaseResponse release = releaseDao.get(releaseConditon);
        if (StringUtils.isEmpty(get_type) || !get_type.equals("WEB")) {
            TemplateCondition tmpTemplate = new TemplateCondition();
            tmpTemplate.release_id = releaseConditon.release_id;
            TemplateCondition template = templateDao.get(tmpTemplate);
            jsonObject.put("template", template);
        }
        ReceivingRestrictionCondition tmpRestriction = new ReceivingRestrictionCondition();
        tmpRestriction.release_id = release.release_id;
        ReceivingRestrictionCondition receivingRestriction = receivingRestrictionDao.get(tmpRestriction);
        if (receivingRestriction == null) {
            jsonObject = new JSONObject();
            jsonObject.put("return_code", UnifiedResponse.FAIL);
            return jsonObject;
        }
        jsonObject.put("release", release);
        jsonObject.put("receiving_restriction", receivingRestriction);
        jsonObject.put("return_code", UnifiedResponse.SUCCESS);
        return jsonObject;
    }

    public PageResponse getList(String release_ids) {
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        String[] release_id_list = release_ids.split(",");
        List<ReleaseResponse> pageList = new ArrayList<ReleaseResponse>();
        for (String release_id : release_id_list) {
            if (!StringUtils.isBlank(release_id)) {
                ReleaseConditon conditon = new ReleaseConditon();
                conditon.release_id = release_id;
                ReleaseResponse response = releaseDao.get(conditon);
                Integer release_status = this.releaseStatus(release_id);
                response.release_status = release_status;
                pageList.add(response);
            }
        }
        if (pageList.size() > 0) {
            pageResponse.setRows(pageList);
            pageResponse.setTotal(pageList.size());
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
            pageResponse.setReturn_value("ok");
        }
        return pageResponse;
    }

    public PageResponse getList(ReleaseConditon releaseConditon) {
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        List<ReleaseResponse> pageList = releaseDao.getList(releaseConditon);
        Integer total = releaseDao.getCount(releaseConditon);
        if (pageList.size() > 0) {
            for (ReleaseResponse releaseResponse : pageList) {
                releaseResponse.release_status = this.releaseStatus(releaseResponse.release_id);
            }
            pageResponse.setRows(pageList);
            pageResponse.setTotal(total);
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
            pageResponse.setReturn_value("ok");
            return pageResponse;
        }
        return pageResponse;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UnifiedResponse save(ReleaseConditon releaseConditon
            , TemplateCondition templateCondition
            , ReceivingRestrictionCondition restrictionCondition) {
        String release_id = GenerateIdentifier.generateReleaseId();
        //创建模板
        templateCondition.release_id = release_id;
        int templateResult = templateDao.insert(templateCondition);
        if (templateResult != 1) {
            return new UnifiedResponse(UnifiedResponse.FAIL, "添加投放策略失败,请检查数据重试!");
        }
        //添加投放策略记录
        releaseConditon.release_id = release_id;
        releaseConditon.release_status = 1;
        int releaseResult = releaseDao.insert(releaseConditon);
        if (releaseResult != 1) {
            throw new RuntimeException("添加投放策略失败,请检查数据重试!");
        }
        //创建领取限制记录
        restrictionCondition.release_id = releaseConditon.release_id;
        int receivingReuslt = receivingRestrictionDao.insert(restrictionCondition);
        if (receivingReuslt != 1) {
            throw new RuntimeException("添加领取限制失败,请检查数据重试!");
        }
        //创建批次剩余量记录
        String[] couponStockIds = releaseConditon.stock_id_list.split(",");
        for (String couponStockId : couponStockIds) {
            StatCondition statCondition = new StatCondition();
            statCondition.coupon_stock_id = couponStockId;
            statCondition.release_id = releaseConditon.release_id;
            statCondition.remaining_count = releaseConditon.release_count;
            int result = statDao.insert(statCondition);
            if (result != 1) {
                throw new RuntimeException("添加投放策略失败,请检查数据重试!");
            }
        }
        return new UnifiedResponse(UnifiedResponse.SUCCESS, "添加投放策略成功!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UnifiedResponse delete(ReleaseConditon releaseConditon) {
        ReleaseResponse releaseResponse = releaseDao.get(releaseConditon);
        int status = this.releaseStatus(releaseResponse.release_id);
        if (status != ReleaseStatusType.NO_RELEASE) {
            return new UnifiedResponse(UnifiedResponse.FAIL, "该策略处于投放状态,不可删除!");
        }
        //删除策略记录
        Integer releaseResult = releaseDao.delete(releaseConditon);
        if (releaseResult == 0) {
            throw new RuntimeException("策略记录删除失败!");
        }
        //删除策略对应的模板
        TemplateCondition templateCondition = new TemplateCondition();
        templateCondition.release_id = releaseConditon.release_id;
        templateCondition = templateDao.get(templateCondition);
        Integer templateResult = templateDao.delete(templateCondition);
        if (templateResult == 0) {
            throw new RuntimeException("策略对应的模板删除失败!");
        }
        //删除领取限制记录
        ReceivingRestrictionCondition restrictionCondition = new ReceivingRestrictionCondition();
        restrictionCondition.release_id = releaseConditon.release_id;
        Integer receivingResult = receivingRestrictionDao.delete(restrictionCondition);
        if (receivingResult == 0) {
            throw new RuntimeException("领取限制记录删除失败!");
        }
        //删除剩余量记录
        StatCondition statCondition = new StatCondition();
        statCondition.release_id = releaseResponse.release_id;
        Integer statResult = statDao.delete(statCondition);
        if (statResult == 0) {
            throw new RuntimeException("剩余量记录删除失败!信息: " + statCondition);
        }

        return new UnifiedResponse(UnifiedResponse.SUCCESS, "删除成功!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UnifiedResponse update(ReleaseConditon releaseConditon
            , TemplateCondition templateCondition
            , ReceivingRestrictionCondition restrictionCondition) {
        ReleaseResponse releaseResponse = releaseDao.get(releaseConditon);
        int status = this.releaseStatus(releaseResponse.release_id);
        if (status != ReleaseStatusType.NO_RELEASE) {
            return new UnifiedResponse(UnifiedResponse.FAIL, "该策略处于投放状态,不可修改!");
        }
        //修改策略记录
        Integer releaseResult = releaseDao.update(releaseConditon);
        if (releaseResult == 0) {
            throw new RuntimeException("策略记录修改失败!");
        }
        //修改页面模板记录
        templateCondition.release_id = releaseConditon.release_id;
        TemplateCondition originalTemplate = new TemplateCondition();
        originalTemplate.release_id = releaseConditon.release_id;
        //originalTemplate = templateDao.get(templateCondition);
        Integer templateResult = templateDao.update(templateCondition);
        if (templateResult == 0) {
            throw new RuntimeException("策略对应的模板修改失败!");
        }
        //修改领取限制记录
        restrictionCondition.release_id = releaseConditon.release_id;
        Integer receivingReuslt = receivingRestrictionDao.update(restrictionCondition);
        if (receivingReuslt == 0) {
            throw new RuntimeException("修改领取限制失败,请检查数据重试!");
        }
        //修改剩余量纪录
        String[] newStockIds = StringUtils.split(releaseConditon.stock_id_list, ",");
        StatCondition statOldCondition = new StatCondition();
        statOldCondition.release_id = releaseConditon.release_id;
        Integer statOldResult = statDao.delete(statOldCondition);
        if (statOldResult == 0) {
            throw new RuntimeException("剩余量记录删除失败!信息: " + statOldCondition);
        }
        for (String coupon_stock_id : newStockIds) {
            StatCondition statCondition = new StatCondition();
            statCondition.coupon_stock_id = coupon_stock_id;
            statCondition.release_id = releaseConditon.release_id;
            statCondition.remaining_count = releaseConditon.release_count;
            Integer statResult = statDao.insert(statCondition);
            if (statResult != 1) {
                throw new RuntimeException("剩余量记录添加失败!信息: " + statCondition);
            }
        }
        return new UnifiedResponse(UnifiedResponse.SUCCESS, "策略修改成功!");
    }

    public UnifiedResponse updateStatus(ReleaseConditon releaseConditon) {
        UnifiedResponse unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "停发失败!");
        if (releaseConditon.release_status == null) {
            releaseConditon.release_status = 2;
        }
        Integer result = releaseDao.update(releaseConditon);
        if (result == 1) {
            unifiedResponse.setReturn_code(UnifiedResponse.SUCCESS);
            unifiedResponse.setReturn_value("ok");
        }
        return unifiedResponse;
    }

    public int releaseStatus(String release_id) {
        DateTime now = DateTime.now();
        ReleaseConditon release = new ReleaseConditon();
        release.release_id = release_id;
        ReleaseResponse releaseResponse = releaseDao.get(release);
        Integer status = releaseResponse.release_status;
        if (status != ReleaseStatusType.EIIECTIVE) {
            return status;  //暂停投放
        }
        String startDateStr = releaseResponse.release_start_time;
        String endDateStr = releaseResponse.release_end_time;
        DateTimeFormatter dtf = DateTimeFormat.forPattern(SysConstants.DATE_FORMAT);
        DateTime startDate = DateTime.parse(startDateStr, dtf);
        DateTime endDate = DateTime.parse(endDateStr, dtf);
        if (now.compareTo(startDate) <= 0) {
            return ReleaseStatusType.NO_RELEASE; //未开始
        }
        if (now.compareTo(endDate) > 0) {
            return ReleaseStatusType.END;
        }
        StatCondition statCondition = new StatCondition();
        statCondition.release_id = releaseResponse.release_id;
        List<StatCondition> list = statDao.getList(statCondition);
        for (StatCondition tmpStat : list) {
            if (tmpStat.getRemaining_count() == 0) {
                return ReleaseStatusType.END;
            }
        }
        return ReleaseStatusType.EIIECTIVE;
    }

    public Integer getCount(ReleaseConditon releaseConditon) {

        return releaseDao.getCount(releaseConditon);
    }

}
