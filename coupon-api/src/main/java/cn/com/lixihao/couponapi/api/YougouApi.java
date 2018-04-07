package cn.com.lixihao.couponapi.api;

import cn.com.lixihao.couponapi.constants.ApiConstants;
import cn.com.lixihao.couponapi.helper.TokenUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.commons.http.HiveHttpEntityType;
import com.hiveview.commons.http.HiveHttpGet;
import com.hiveview.commons.http.HiveHttpResponse;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Repository
public class YougouApi {

    public String get(Object object, String path) {
        String url = ApiConstants.YOUGOU_URL + ":8082" + path;
        String params = JSONObject.toJSONString(object);
        String token = "";
        if (object instanceof Map) {
            token = TokenUtils.youGouToken((Map) object);
        } else {
            JSONObject mapObj = JSONObject.parseObject(JSON.toJSONString(object));
            Map map = JSONObject.toJavaObject(mapObj, Map.class);
            token = TokenUtils.youGouToken(map);
        }
        try {
            url = url + "?params=" + URLEncoder.encode(params, "UTF-8") + "&token=" + token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HiveHttpResponse response = HiveHttpGet.getEntity(url, HiveHttpEntityType.STRING);
        return response.entityString;
    }

}
