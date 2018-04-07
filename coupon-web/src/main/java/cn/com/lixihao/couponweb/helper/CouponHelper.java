package cn.com.lixihao.couponweb.helper;

import com.hiveview.commons.http.HiveHttpQueryString;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

/**
 * create by lixihao on 2018/2/6.
 **/

public class CouponHelper {

    public static boolean verifyMD5Sign(Map<String, String> parameterMap, String key) {
        String sign = parameterMap.get("sign");
        return CouponHelper.generateMD5Sign(parameterMap, key).equals(sign);
    }

    public static String generateMD5Sign(Map<String, String> parameterMap, String key) {
        parameterMap.remove("sign");
        String data = HiveHttpQueryString.buildQuery(parameterMap);
        return CouponHelper.generateMD5Sign(data, key);
    }

    public static String generateMD5Sign(String queryString, String key) {
        return DigestUtils.md5Hex(queryString + "&key=" + key).toUpperCase();
    }
}
