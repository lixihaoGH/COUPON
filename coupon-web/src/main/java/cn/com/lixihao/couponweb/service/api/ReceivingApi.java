package cn.com.lixihao.couponweb.service.api;

import cn.com.lixihao.couponweb.constant.ApiConstants;
import cn.com.lixihao.couponweb.constant.SysConstants;
import cn.com.lixihao.couponweb.entity.CouponReceiving;
import cn.com.lixihao.couponweb.entity.CouponStock;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.commons.http.HiveHttpEntityType;
import com.hiveview.commons.http.HiveHttpGet;
import com.hiveview.commons.http.HiveHttpPost;
import com.hiveview.commons.http.HiveHttpResponse;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by lixihao on 2017/12/12.
 **/
@Service
public class ReceivingApi {


    public JSONObject queryTemplate(String release_id) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "template/get.json");
        httpUrl.append("?release_id=").append(release_id);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString)) {
            return null;
        }
        JSONObject jsonResponse = JSONObject.parseObject(httpResponse.entityString);
        if (jsonResponse.isEmpty() || "0".equals(jsonResponse.getString("return_code"))) {
            return null;
        }
        return jsonResponse;
    }

    public JSONObject getRelease(String release_id) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "release/get.json");
        httpUrl.append("?release_id=").append(release_id);
        httpUrl.append("&get_type=").append("WEB");
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString)) {
            return null;
        }
        JSONObject jsonResponse = JSONObject.parseObject(httpResponse.entityString);
        if (jsonResponse.isEmpty() || "0".equals(jsonResponse.getString("return_code"))) {
            return null;
        }
        return jsonResponse;
    }


    public Integer countRemainingRelease(String release_id) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/count_remaining_release.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("release_id", release_id);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return Integer.parseInt(httpResponse.entityString);
    }


    public JSONObject countUserReceiving(String release_id, Integer device_type, String phone_number, String stock_id_list) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/count_user_receiving.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("release_id", release_id);
        requestMap.put("device_type", device_type + "");
        requestMap.put("phone_number", phone_number);
        requestMap.put("stock_id_list", stock_id_list);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseObject(httpResponse.entityString);
    }


    public List<CouponStock> getCouponStock(String stock_id_list) {
        return this.getYougouStock(stock_id_list);
    }

    public List<CouponStock> getYougouStock(String stock_id_list) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/yougou_stock.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("stock_id_list", stock_id_list);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseArray(httpResponse.entityString, CouponStock.class);
    }

    public String receivingCoupon(String user_id, String phone_number, String openid, String release_id, String stock_id_list, Integer device_type) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/add.json");
        Map<String, String> requestMap = this.getReceivingRequestMap(phone_number, openid, release_id, stock_id_list, device_type);
        requestMap.put("user_id", user_id);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public String receivingCoupon(String phone_number, String openid, String release_id, String stock_id_list, Integer device_type) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/add.json");
        Map<String, String> requestMap = this.getReceivingRequestMap(phone_number, openid, release_id, stock_id_list, device_type);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public List<CouponReceiving> querylist(String user_id, String phone_number, Integer coupon_status, Integer page_index, Integer page_size) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/getlist.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("phone_number", phone_number);
        requestMap.put("user_id", user_id);
        requestMap.put("coupon_status", coupon_status + "");
        requestMap.put("page_index", page_index + "");
        requestMap.put("page_size", page_size + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseArray(httpResponse.entityString, CouponReceiving.class);
    }

    public List<CouponReceiving> queryYougouCoupon(String user_id, String phone_number, Integer price, String grade_1st_category_sn, String grade_2nd_category_sn, String grade_3rd_category_sn, String good_sku_sn, String goods_category) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/yougou_coupon.json");
        Map<String, String> requestMap = this.getRequestMap(price, grade_1st_category_sn, grade_2nd_category_sn, grade_3rd_category_sn, good_sku_sn, goods_category);
        requestMap.put("phone_number", phone_number);
        requestMap.put("user_id", user_id);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseArray(httpResponse.entityString, CouponReceiving.class);
    }


    public JSONObject verifyYougouCoupon(String coupon_stock_id, Integer price, String grade_1st_category_sn, String grade_2nd_category_sn, String grade_3rd_category_sn, String good_sku_sn, String goods_category) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/verify_yougou_coupon.json");
        Map<String, String> requestMap = this.getRequestMap(price, grade_1st_category_sn, grade_2nd_category_sn, grade_3rd_category_sn, good_sku_sn, goods_category);
        requestMap.put("coupon_stock_id", coupon_stock_id);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString);
    }

    public String updateReceivingUserInfo(String user_id, String phone_number) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/update_user_info.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("user_id", user_id);
        requestMap.put("phone_number", phone_number);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public CouponReceiving getReceiving(String coupon_id, String user_id, String phone_number, String coupon_stock_id, Integer coupon_status) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/get.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("coupon_id", coupon_id);
        requestMap.put("user_id", user_id);
        requestMap.put("phone_number", phone_number);
        requestMap.put("coupon_stock_id", coupon_stock_id);
        requestMap.put("coupon_status", coupon_status + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString, CouponReceiving.class);
    }

    public CouponReceiving getReceiving(String coupon_id, String coupon_stock_id) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "receiving/get.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("coupon_id", coupon_id);
        requestMap.put("coupon_stock_id", coupon_stock_id);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString, CouponReceiving.class);
    }

    public String queryOpenid(String appid, String code){
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("receiving/openid.json");
        httpUrl.append("?appid=").append(appid).append("&code=").append(code);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    private Map<String, String> getRequestMap(Integer price, String grade_1st_category_sn, String grade_2nd_category_sn, String grade_3rd_category_sn, String good_sku_sn, String goods_category) {
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("reach_amount", price + "");
        requestMap.put("selected_first_level_list", grade_1st_category_sn);
        requestMap.put("selected_second_level_list", grade_2nd_category_sn);
        requestMap.put("selected_third_level_list", grade_3rd_category_sn);
        requestMap.put("selected_goods_list", good_sku_sn);
        requestMap.put("selected_goods_category", goods_category);
        requestMap.put("excluded_first_level_list", grade_1st_category_sn);
        requestMap.put("excluded_second_level_list", grade_2nd_category_sn);
        requestMap.put("excluded_third_level_list", grade_3rd_category_sn);
        requestMap.put("excluded_goods_category", goods_category);
        requestMap.put("excluded_goods_list", good_sku_sn);
        return requestMap;
    }

    private Map<String, String> getReceivingRequestMap(String phone_number, String openid, String release_id, String stock_id_list, Integer device_type) {
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("phone_number", phone_number);
        requestMap.put("release_id", release_id);
        requestMap.put("openid", openid);
        requestMap.put("device_type", device_type + "");
        String receiving_time = new DateTime().toString(SysConstants.DATE_FORMAT);
        requestMap.put("stock_id_list", stock_id_list);
        requestMap.put("receiving_time", receiving_time);
        return requestMap;
    }

}
