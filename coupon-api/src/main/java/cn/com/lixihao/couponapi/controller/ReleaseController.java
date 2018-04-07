package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;
import cn.com.lixihao.couponapi.entity.condition.ReleaseConditon;
import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;
import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.ReleaseStatusType;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.service.ReleaseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(value = "/release")
@Controller
public class ReleaseController {

    private Logger DATA = LoggerFactory.getLogger(ReleaseController.class);

    @Autowired
    private ReleaseService releaseService;

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public JSONObject get(String release_id, String get_type) {
        DATA.info("[release]get: release_id->{},get_type->{}", new Object[]{release_id,get_type});
        JSONObject jsonObject;
        try {
            ReleaseConditon releaseConditon = new ReleaseConditon();
            releaseConditon.release_id = release_id;
            jsonObject = releaseService.get(releaseConditon,get_type);
        } catch (Exception e) {
            DATA.error("[release]get: exception->{}","查询信息不存在!" + e.getMessage());
            jsonObject = new JSONObject();
            jsonObject.put("return_code", UnifiedResponse.FAIL);
            jsonObject.put("return_value", "NOT_FOUND!");
        }
        DATA.info("[release]get: response->{}", jsonObject.toJSONString());
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public PageResponse getList(ReleaseConditon releaseConditon) {
        DATA.info("[release]getList: release_name->{},release_status->{}", releaseConditon.getRelease_name(),releaseConditon.getRelease_status());
        PageResponse pageResponse = releaseService.getList(releaseConditon);
        DATA.info("[release]getList: response->{}", JSONObject.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public PageResponse getList(String release_ids) {
        DATA.info("[release]getList: release_ids->{}", release_ids);
        PageResponse pageResponse = releaseService.getList(release_ids);
        DATA.info("[release]getList: response->{}", JSONObject.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public UnifiedResponse save(@RequestBody String json) {
        DATA.info("[release]save: condition->{}", json);
        UnifiedResponse unifiedResponse;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String releaseJson = jsonObject.getString("release");
            String templateJson = jsonObject.getString("template");
            String receivingJson = jsonObject.getString("receiving_restriction");
            ReleaseConditon releaseConditon = JSONObject.parseObject(releaseJson, ReleaseConditon.class);
            TemplateCondition templateCondition = JSONObject.parseObject(templateJson, TemplateCondition.class);
            ReceivingRestrictionCondition restrictionCondition = JSONObject.parseObject(receivingJson, ReceivingRestrictionCondition.class);
            try {
                unifiedResponse = releaseService.save(releaseConditon, templateCondition,restrictionCondition);
            } catch (DuplicateKeyException e){
                DATA.error("[release]save: exception->{}:" , e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "该策略名称已存在!");
            }catch (Exception e) {
                DATA.error("[release]save: exception->{}:" , "添加失败!" + e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "添加投放策略失败,请检查数据重试!");
            }
        } catch (Exception e) {
            DATA.error("[release]save: exception->{}:" , "json为空或格式错误!" + e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "json为空或格式错误!");
        }
        DATA.info("[release]save: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public UnifiedResponse delete(String release_id) {
        DATA.info("[release]delete: release_id->{}", release_id);
        UnifiedResponse unifiedResponse;
        try {
            ReleaseConditon releaseConditon = new ReleaseConditon();
            releaseConditon.release_id = release_id;
            unifiedResponse = releaseService.delete(releaseConditon);
        } catch (Exception e) {
            DATA.error("[release]delete: exception->{}","删除失败,异常信息:" + e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "删除失败!请重试!");
        }
        DATA.info("[release]delete: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public UnifiedResponse update(@RequestBody String json) {
        DATA.info("[release]update: condition->{}", json);
        UnifiedResponse unifiedResponse;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String releaseJson = jsonObject.getString("release");
            String templateJson = jsonObject.getString("template");
            String receivingRestriction = jsonObject.getString("receiving_restriction");
            ReleaseConditon release = JSONObject.parseObject(releaseJson, ReleaseConditon.class);
            TemplateCondition template = JSONObject.parseObject(templateJson, TemplateCondition.class);
            ReceivingRestrictionCondition restrictionCondition = JSONObject.parseObject(receivingRestriction, ReceivingRestrictionCondition.class);
            try {
                unifiedResponse = releaseService.update(release, template,restrictionCondition);
            } catch (DuplicateKeyException e){
                DATA.error("[release]update: exception->{}:" , e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "该策略名称已存在!");
            }catch (Exception e) {
                DATA.error("[release]update: exception->{}", "策略更新失败,异常信息:" + json + "," + e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "策略更新失败!");
            }
        } catch (Exception e) {
            DATA.error("[release]update: exception->{}", "json为空或格式错误,json:" + json + "," + e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "json为空或格式错误!");
        }
        DATA.info("[release]update: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    public UnifiedResponse updateStatus(ReleaseConditon releaseConditon) {
        DATA.info("[release]updateStatus: release_id->{},release_status->{}", releaseConditon.getRelease_id(),releaseConditon.getRelease_status());
        UnifiedResponse unifiedResponse;
        try {
            unifiedResponse = releaseService.updateStatus(releaseConditon);
        } catch (Exception e) {
            DATA.error("[release]updateStatus: exception->{}",e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, e.getMessage());
        }
        DATA.info("[release]updateStatus: response->{}", JSON.toJSONString(unifiedResponse));
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method = RequestMethod.GET)
    public UnifiedResponse getCount(ReleaseConditon releaseConditon) {
        DATA.info("[release]getCount: release_name->{},release_status->{}", releaseConditon.getRelease_name(),releaseConditon.getRelease_status());
        UnifiedResponse unifiedResponse = new UnifiedResponse(UnifiedResponse.SUCCESS,"0");
        Integer count = releaseService.getCount(releaseConditon);
        if (count > 0) {
            unifiedResponse.setReturn_value(count + "");
        }
        DATA.info("[release]getCount: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/checkStatus",method = RequestMethod.GET)
    public UnifiedResponse checkStatus(String release_id) {
        DATA.info("[release]checkStatus: release_id->{}", release_id);
        UnifiedResponse unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL,"不可操作!");
        ReleaseConditon releaseConditon = new ReleaseConditon();
        releaseConditon.release_id = release_id;
        Integer status;
        try {
            status = releaseService.releaseStatus(releaseConditon.release_id);
            if (status == ReleaseStatusType.NO_RELEASE) {
                unifiedResponse.setReturn_code(UnifiedResponse.SUCCESS);
                unifiedResponse.setReturn_value(status + "");
            } else {
                unifiedResponse.setReturn_value(status + "");
            }
        } catch (Exception e) {
            DATA.error("[release]checkStatus: exception->{}","查询错误!" + e.getMessage());
            status = ReleaseStatusType.EIIECTIVE;
            unifiedResponse.setReturn_code(UnifiedResponse.FAIL);
            unifiedResponse.setReturn_value(status + "");
        }
        DATA.info("[release]checkStatus: response->{}", unifiedResponse);
        return unifiedResponse;
    }
}
