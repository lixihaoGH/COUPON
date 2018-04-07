package cn.com.lixihao.couponweb.service.api;

import cn.com.lixihao.couponweb.constant.ApiConstants;
import cn.com.lixihao.couponweb.constant.SysConstants;
import com.alibaba.fastjson.JSONObject;
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
public class BindingApi {

    public JSONObject getBinding(Integer param_type, String param_value) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "binding/get.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        if (param_type.equals(SysConstants.GET_BINDING_PARAM_TYPE_OPENID)) {
            requestMap.put("openid", param_value);
        } else if (param_type.equals(SysConstants.GET_BINDING_PARAM_TYPE_PHONE)) {
            requestMap.put("phone_number", param_value);
        }
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString);
    }

    public String addBinding(String phone_number, Integer paramType, String paramValue) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "binding/add.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("phone_number", phone_number);
        if (paramType.equals(SysConstants.ADD_BINDING_PARAM_OPENID)) {
            requestMap.put("openid", paramValue);
        } else if (paramType.equals(SysConstants.ADD_BINDING_PARAM_USERID)) {
            requestMap.put("user_id", paramValue);
        }
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public String updateBinding(String phone_number, Integer paramType, String paramValue) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "binding/update.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("phone_number", phone_number);
        if (paramType.equals(SysConstants.ADD_BINDING_PARAM_OPENID)) {
            requestMap.put("openid", paramValue);
        } else if (paramType.equals(SysConstants.ADD_BINDING_PARAM_USERID)) {
            requestMap.put("user_id", paramValue);
        }
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

}
