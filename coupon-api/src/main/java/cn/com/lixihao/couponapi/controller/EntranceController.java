package cn.com.lixihao.couponapi.controller;


import cn.com.lixihao.couponapi.constants.ApiConstants;
import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import cn.com.lixihao.couponapi.entity.result.EntranceResponse;
import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.service.EntranceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(value = "/entrance")
@Controller
public class EntranceController {

    private Logger DATA = LoggerFactory.getLogger(EntranceController.class);

    @Autowired
    private EntranceService entranceService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String entrance(@PathVariable("id") Integer id) {
        DATA.info("[entrance]id->{}", id);
        String targetUrl;
        try {
            EntranceCondition condition = new EntranceCondition();
            condition.setId(id);
            String release_id = entranceService.entrance(condition);
            targetUrl = ApiConstants.COUPON_WEB_URL + "coupon/authorize.json?release_id=" + release_id;
        } catch (Exception e) {
            DATA.error("[entrance]exception->{}", e.getMessage());
            targetUrl = ApiConstants.COUPON_WEB_URL + "coupon/authorize.json?release_id=0";
        }
        DATA.info("[entrance]response->{}", targetUrl);
        return "redirect:" + targetUrl;
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public JSONObject get(Integer id) {
        DATA.info("[entrance]get: id->{}", id);
        JSONObject jsonObject;
        try {
            EntranceCondition condition = new EntranceCondition();
            condition.setId(id);
            EntranceResponse entranceResponse = entranceService.get(condition);
            jsonObject = (JSONObject) JSON.toJSON(entranceResponse);
        } catch (Exception e) {
            DATA.error("[entrance]get: exception->{}", e.getMessage());
            jsonObject = new JSONObject();
            jsonObject.put("return_code", 0);
            jsonObject.put("return_value", "查询失败!");
        }
        DATA.info("[entrance]get: response->{}", jsonObject.toJSONString());
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public PageResponse getList(EntranceCondition condition) {
        DATA.info("[entrance]getList: entrance_name->{}", condition.getEntrance_name());
        PageResponse pageResponse;
        try {
            pageResponse = entranceService.getList(condition);
        } catch (Exception e) {
            DATA.error("[entrance]getList: exception->{}", e.getMessage());
            pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        }
        DATA.info("[entrance]getList: response->{}", JSON.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UnifiedResponse save(@RequestBody EntranceCondition condition) {
        DATA.info("[entrance]save: entrance_name->{}", condition.getEntrance_name());
        UnifiedResponse unifiedResponse;
        try {
            unifiedResponse = entranceService.save(condition);
        } catch (DuplicateKeyException e) {
            return new UnifiedResponse(UnifiedResponse.FAIL, "该入口名称已存在!");
        } catch (Exception e) {
            DATA.error("[entrance]save: exception->{}", e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "创建失败!信息: " + e.getMessage());
        }
        DATA.info("[entrance]save: response->{}", JSON.toJSONString(unifiedResponse));
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public UnifiedResponse update(@RequestBody EntranceCondition condition) {
        DATA.info("[entrance]update: condition->{}", condition);
        UnifiedResponse unifiedResponse;
        try {
            unifiedResponse = entranceService.update(condition);
        } catch (DuplicateKeyException e) {
            DATA.error("[entrance]update: exception->{}:", e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "该入口名称已存在!");
        } catch (Exception e) {
            DATA.error("[entrance]update: exception->{}", e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "更新失败!信息: " + e.getMessage());
        }
        DATA.info("[entrance]update: response->{}", JSON.toJSONString(unifiedResponse));
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public UnifiedResponse delete(Integer id) {
        DATA.info("[entrance]delete: id->{}", id);
        UnifiedResponse unifiedResponse;
        try {
            EntranceCondition condition = new EntranceCondition();
            condition.setId(id);
            unifiedResponse = entranceService.delete(condition);
        } catch (Exception e) {
            DATA.error("[entrance]delete: exception->{}", e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "删除失败!信息: " + e.getMessage());
        }
        DATA.info("[entrance]delete: response->{}", JSON.toJSONString(unifiedResponse));
        return unifiedResponse;
    }
}
