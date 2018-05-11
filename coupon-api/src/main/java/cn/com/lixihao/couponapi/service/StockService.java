package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.entity.condition.StatCondition;
import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.StockResponse;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.helper.GenerateIdentifier;
import cn.com.lixihao.couponapi.dao.StatDao;
import cn.com.lixihao.couponapi.dao.StockDao;
import cn.com.lixihao.couponapi.dao.YougouRestrictionDao;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockDao stockDao;
    @Autowired
    private YougouRestrictionDao yougouRestrictionDao;
    @Autowired
    private StatDao statDao;

    public JSONObject get(StockCondition stockCondition, Integer type) {
        JSONObject jsonObject = new JSONObject();
        if (type != null) {
            //用以单独查询限制条件
            if (type == 1) {
                YougouRestrictionCondition yougouCondition = new YougouRestrictionCondition();
                yougouCondition.coupon_stock_id = stockCondition.coupon_stock_id;
                yougouCondition = yougouRestrictionDao.get(yougouCondition);
                jsonObject.put("yougou_restriction", yougouCondition);
                jsonObject.put("return_code", UnifiedResponse.SUCCESS);
                jsonObject.put("return_value", "ok");
                return jsonObject;
            }
        }
        StockResponse stockResponse = stockDao.get(stockCondition);
        YougouRestrictionCondition yougouCondition = new YougouRestrictionCondition();
        yougouCondition.coupon_stock_id = stockResponse.coupon_stock_id;
        YougouRestrictionCondition yougouRestrictionResponse = yougouRestrictionDao.get(yougouCondition);
        stockResponse.preferential_amount = stockResponse.preferential_amount / 100;
        yougouRestrictionResponse.reach_amount = yougouRestrictionResponse.reach_amount / 100;
        jsonObject.put("coupon_stock", stockResponse);
        jsonObject.put("yougou_restriction", yougouRestrictionResponse);
        jsonObject.put("return_code", UnifiedResponse.SUCCESS);
        jsonObject.put("return_value", "ok");
        return jsonObject;
    }

    public PageResponse getList(StockCondition stockCondition) {
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        List<StockResponse> list = stockDao.getList(stockCondition);
        Integer total = stockDao.getCount(stockCondition);
        if (list.size() > 0) {
            pageResponse.setRows(list);
            pageResponse.setTotal(total);
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
            pageResponse.setReturn_value("ok");
            return pageResponse;
        }
        return pageResponse;
    }

    public PageResponse getList(String stock_ids) {
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        String[] stock_id_list = stock_ids.split(",");
        List<StockResponse> list = new ArrayList<StockResponse>();
        for (String stock_id : stock_id_list) {
            if (!StringUtils.isBlank(stock_id)) {
                StockCondition condition = new StockCondition();
                condition.coupon_stock_id = stock_id;
                StockResponse response = stockDao.get(condition);
                list.add(response);
            }
        }
        if (list.size() > 0) {
            pageResponse.setRows(list);
            pageResponse.setTotal(list.size());
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
            pageResponse.setReturn_value("ok");
        }
        return pageResponse;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UnifiedResponse saveYougouCoupon(StockCondition stockCondition, YougouRestrictionCondition yougouCondition) {
        stockCondition.setCreate_time(DateTime.now().toString(SysConstants.DATE_FORMAT));
        if (yougouCondition.getEffective_duration() != null) {
            yougouCondition.setEffective_time(null);
            yougouCondition.setExpired_time(null);
        }
        stockCondition.preferential_amount = stockCondition.preferential_amount * 100;
        yougouCondition.reach_amount = yougouCondition.reach_amount * 100;
        String coupon_stock_id = GenerateIdentifier.generateCouponStockId(yougouCondition.selected_goods_category == null ? "0" : yougouCondition.selected_goods_category);
        stockCondition.coupon_stock_id = coupon_stock_id;
        yougouCondition.coupon_stock_id = coupon_stock_id;
        Integer stockResult = stockDao.insert(stockCondition);
        Integer yougouResult = yougouRestrictionDao.insert(yougouCondition);
        if (stockResult != 1 || yougouResult != 1) {
            throw new RuntimeException("创建红包批次失败! " + stockCondition);
        }
        return new UnifiedResponse(UnifiedResponse.SUCCESS, "创建成功!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UnifiedResponse delete(StockCondition stockCndition) {
        UnifiedResponse checkResult = this.checkReleaseStatus(stockCndition.coupon_stock_id);
        if (checkResult.getReturn_code() == UnifiedResponse.FAIL) {
            return checkResult;
        }
        YougouRestrictionCondition yougouCondition = new YougouRestrictionCondition();
        yougouCondition.coupon_stock_id = stockCndition.coupon_stock_id;
        Integer yougouResult = yougouRestrictionDao.delete(yougouCondition);
        Integer stockResult = stockDao.delete(stockCndition);
        if (yougouResult == 0 || stockResult == 0) {
            throw new RuntimeException("删除失败!该红包不存在或数据有误!");
        }
        return new UnifiedResponse(UnifiedResponse.SUCCESS, "ok");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UnifiedResponse updateYougou(StockCondition stockCondition, YougouRestrictionCondition yougouCondition) {
        UnifiedResponse unifiedResponse = null;
        yougouCondition.coupon_stock_id = stockCondition.coupon_stock_id;
        Integer stockResult = stockDao.update(stockCondition);
        Integer yougouResult = yougouRestrictionDao.update(yougouCondition);
        if (stockResult == 0 || yougouResult == 0) {
            throw new RuntimeException("更新失败!该红包不存在或数据有误!");
        }
        unifiedResponse = new UnifiedResponse(UnifiedResponse.SUCCESS, "更新成功!");

        return unifiedResponse;
    }

    public Integer getCount(StockCondition stockCondition) {
        return stockDao.getCount(stockCondition);
    }

    public UnifiedResponse checkReleaseStatus(String coupon_stock_id) {
        UnifiedResponse unifiedResponse;
        StatCondition statCondition = new StatCondition();
        statCondition.coupon_stock_id = coupon_stock_id;
        Integer result = statDao.getCount(statCondition);
        if (result > 0) {
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "该红包已加入投放策略!");
        } else {
            unifiedResponse = new UnifiedResponse(UnifiedResponse.SUCCESS, "该红包未加入投放策略!");
        }
        return unifiedResponse;
    }
}
