package cn.com.lixihao.couponapi.helper;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TokenHelper {
    public static String getToken(Map map, String key) {
        List<String> list = new ArrayList(map.keySet());
        Collections.sort(list);
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String keyStr = list.get(i);
            Object value = map.get(keyStr);
            if (!StringUtils.isBlank(value.toString())) {
                sbd.append("&" + keyStr).append("=").append(value);
            }
        }
        sbd.append("&key=").append(key);
        return DigestUtils.md5Hex(sbd.substring(1)).toUpperCase();
    }

    public static String getToken(Object object, String key) {
        String json = JSONObject.toJSONString(object);
        Map map = JSONObject.parseObject(json, Map.class);
        return getToken(map, key);
    }
}
