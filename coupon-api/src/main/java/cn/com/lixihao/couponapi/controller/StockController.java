package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.service.StockService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping(value = "/coupon_stock")
public class StockController {

    private Logger DATA = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public JSONObject get(String coupon_stock_id, Integer type) {    //type 用以标记特殊请求情况
        DATA.info("[conpon_stock]get: coupon_stock_id->{},type->{}", coupon_stock_id, type);
        JSONObject jsonObject;
        try {
            StockCondition stockCondition = new StockCondition();
            stockCondition.coupon_stock_id = coupon_stock_id;
            jsonObject = stockService.get(stockCondition, type);
        } catch (Exception e) {
            DATA.error("[coupon_stock]get: exception->{}", e.getMessage());
            jsonObject = new JSONObject();
            jsonObject.put("return_code", UnifiedResponse.FAIL);
            jsonObject.put("return_value", "NOT_FOUND!");
        }
        DATA.info("[conpon_stock]get: response->{}", jsonObject.toJSONString());
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public PageResponse getList(StockCondition stock) {
        DATA.info("[coupon_stock]getList: condition->{}", JSON.toJSONString(stock));
        PageResponse pageResponse = stockService.getList(stock);
        DATA.info("[conpon_stock]get: response->{}", JSONObject.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public PageResponse getList(@RequestBody String stock_ids) {
        DATA.info("[coupon_stock]getList: stock_ids->{}", stock_ids);
        PageResponse pageResponse = stockService.getList(stock_ids);
        DATA.info("[conpon_stock]get: response->{}", JSONObject.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/save/yougou", method = RequestMethod.POST)
    public UnifiedResponse saveYougou(@RequestBody String json) {
        DATA.info("[coupon_stock]save: condition->{}", json);
        UnifiedResponse unifiedResponse;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String stockJson = jsonObject.getString("coupon_stock");
            String yougouRestrictionJson = jsonObject.getString("yougou_restriction");
            StockCondition stockCondition = JSON.parseObject(stockJson, StockCondition.class);
            YougouRestrictionCondition yougouCondition = JSON.parseObject(yougouRestrictionJson, YougouRestrictionCondition.class);
            try {
                unifiedResponse = stockService.saveYougouCoupon(stockCondition, yougouCondition);
            } catch (DuplicateKeyException e) {
                DATA.error("[coupon_stock]save yougou: exception->{}", e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "该红包名称已存在!");
            } catch (Exception e) {
                DATA.error("[coupon_stock]save: exception->{}", "创建失败!" + e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "创建红包失败!");
            }
        } catch (Exception e) {
            DATA.error("json为空或内容格式错误!exception->{}", e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "json为空或内容格式错误!");
        }
        DATA.info("[coupon_stock]save: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public UnifiedResponse delete(String coupon_stock_id) {
        DATA.info("[coupon_stock]delete: coupon_stock_id->{}", coupon_stock_id);
        UnifiedResponse unifiedResponse;
        try {
            StockCondition stockCondition = new StockCondition();
            stockCondition.coupon_stock_id = coupon_stock_id;
            unifiedResponse = stockService.delete(stockCondition);
        } catch (Exception e) {
            DATA.error("[coupon_stock]delete: exception->{}", "删除失败!" + e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "删除失败!");
        }
        DATA.info("[coupon_stock]delete: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/update/yougou", method = RequestMethod.POST)
    public UnifiedResponse updateYougou(@RequestBody String json) {
        DATA.info("[coupon_stock]update_yougou: condition->{}", json);
        UnifiedResponse unifiedResponse;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String stockJson = jsonObject.getString("coupon_stock");
            String yougouRestrictionJson = jsonObject.getString("yougou_restriction");
            StockCondition stockCondition = JSON.parseObject(stockJson, StockCondition.class);
            YougouRestrictionCondition yougouCondition = JSON.parseObject(yougouRestrictionJson, YougouRestrictionCondition.class);
            try {
                unifiedResponse = stockService.updateYougou(stockCondition, yougouCondition);
            } catch (DuplicateKeyException e) {
                DATA.error("[coupon_stock]update yougou: exception->{}", e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "该红包名称已存在!");
            } catch (Exception e) {
                DATA.error("[coupon_stock]update_yougou: exception->{}", "更新失败!" + e.getMessage());
                unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "更新失败!");
            }
        } catch (Exception e) {
            DATA.error("[coupon_stock]update_yougou: exception->{}", "json为空或内容格式错误!" + e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "json为空或内容格式错误!");
        }
        DATA.info("[coupon_stock]update_yougou: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    public UnifiedResponse getCount(String coupon_stock_name) {
        DATA.info("[coupon_stock]getCount: coupon_stock_name->{}", coupon_stock_name);
        StockCondition stockCondition = new StockCondition();
        stockCondition.coupon_stock_name = coupon_stock_name;
        Integer result = stockService.getCount(stockCondition);
        UnifiedResponse unifiedResponse = new UnifiedResponse(UnifiedResponse.SUCCESS, result + "");
        DATA.info("[coupon_stock]getCount: response->{}", unifiedResponse);
        return unifiedResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/checkReleaseStatus", method = RequestMethod.GET)
    public UnifiedResponse checkReleaseStatus(String coupon_stock_id) {
        DATA.info("[coupon_stock]checkReleaseStatus: coupon_stock_id->{}", coupon_stock_id);
        UnifiedResponse unifiedResponse;
        try {
            unifiedResponse = stockService.checkReleaseStatus(coupon_stock_id);
        } catch (Exception e) {
            DATA.error("[coupon_stock]checkReleaseStatus: exception->{}", "查询错误!" + e.getMessage());
            unifiedResponse = new UnifiedResponse(UnifiedResponse.FAIL, "查询错误,请重试!");
        }
        DATA.info("[coupon_stock]checkReleaseStatus: response->{}", unifiedResponse);
        return unifiedResponse;
    }
}
