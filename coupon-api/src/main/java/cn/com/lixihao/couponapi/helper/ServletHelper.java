package cn.com.lixihao.couponapi.helper;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ServletHelper {

    public static String getParametersString(Object obj) {
        String json = JSONObject.toJSONString(obj);
        StringBuilder sbd = new StringBuilder();
        Map<String, Object> map = JSONObject.parseObject(json, Map.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = (entry.getValue()).toString();
            if (!StringUtils.isBlank(value)) {
                if (key.contains("time") || key.contains("list")) {
                    try {
                        value = URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                sbd.append(key).append("=").append(value).append("&");
            }
        }
        return sbd.substring(0, sbd.length() - 1);
    }

    public static void main(String[] args) {

    }

}
