package cn.com.lixihao.couponweb.service.api;

import cn.com.lixihao.couponweb.constant.ApiConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hiveview.commons.http.HiveHttpEntityType;
import com.hiveview.commons.http.HiveHttpPost;
import com.hiveview.commons.http.HiveHttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * create by lixihao on 2018/1/19.
 **/
@Service
public class TradeApi {

    public String addTrade(String trade_no, String coupon_id, String coupon_stock_id, String user_id, Integer total_amount, Integer deduction_amount, Integer payment_amount, Integer trade_status) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "trade/add.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("trade_no", trade_no);
        requestMap.put("coupon_id", coupon_id);
        requestMap.put("coupon_stock_id", coupon_stock_id);
        requestMap.put("user_id", user_id);
        requestMap.put("total_amount", total_amount + "");
        requestMap.put("deduction_amount", deduction_amount + "");
        requestMap.put("payment_amount", payment_amount + "");
        requestMap.put("trade_status", trade_status + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public String updateTrade(String coupon_id, String trade_no, Integer trade_status) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "trade/update.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("trade_no", trade_no);
        requestMap.put("coupon_id", coupon_id);
        requestMap.put("trade_status", trade_status + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public JSONArray getTradeList(String trade_no, Integer trade_status) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "trade/getlist.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("trade_no", trade_no);
        requestMap.put("trade_status", trade_status + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseArray(httpResponse.entityString);
    }

    public String cancelExpiredTrade(Integer trade_status, String create_time) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "trade/cancel.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("create_time", create_time);
        requestMap.put("trade_status", trade_status + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }
}
