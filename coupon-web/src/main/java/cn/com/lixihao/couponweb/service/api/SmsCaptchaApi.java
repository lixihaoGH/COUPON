package cn.com.lixihao.couponweb.service.api;

import cn.com.lixihao.couponweb.constant.ApiConstants;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.commons.http.HiveHttpEntityType;
import com.hiveview.commons.http.HiveHttpPost;
import com.hiveview.commons.http.HiveHttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * create by lixihao on 2018/2/26.
 **/
@Service
public class SmsCaptchaApi {

    public JSONObject sendSmsCaptcha(String phone, Integer captchaSize, Integer intervalSecond, String contentTitle, Integer effectiveMinutes) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "/captcha/sms/send.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("phone", phone);
        requestMap.put("captchaSize", captchaSize + "");
        requestMap.put("intervalSecond", intervalSecond + "");
        requestMap.put("contentTitle", contentTitle);
        requestMap.put("effectiveMinutes", effectiveMinutes + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString);
    }

    public JSONObject verifySmsCaptcha(String phone, String smsCaptcha) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API + "/captcha/sms/verify.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("phone", phone);
        requestMap.put("smsCaptcha", smsCaptcha);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString);
    }
}
