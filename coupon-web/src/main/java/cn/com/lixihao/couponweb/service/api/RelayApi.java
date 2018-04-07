package cn.com.lixihao.couponweb.service.api;

import cn.com.lixihao.couponweb.constant.ApiConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.commons.http.HiveHttpEntityType;
import com.hiveview.commons.http.HiveHttpGet;
import com.hiveview.commons.http.HiveHttpPost;
import com.hiveview.commons.http.HiveHttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haoweige on 2017/8/30.
 */
@Service
public class RelayApi {

    private Logger DATA = LoggerFactory.getLogger(RelayApi.class);

    public String getOauth2Url(String appid, String redirect_uri, String state) {
        StringBuffer weixinUri = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize");
        weixinUri.append("?appid=").append(appid);
        weixinUri.append("&redirect_uri=").append(redirect_uri);
        weixinUri.append("&response_type=code&scope=snsapi_base");
        weixinUri.append("&state=").append(state);
        weixinUri.append("#wechat_redirect");
        String oauth2Uri = weixinUri.toString();
        DATA.info("[getOauth2Url]{}", weixinUri);
        return oauth2Uri;
    }

    public JSONObject getEventMaterial(String openid, String event_key) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("event/match.json");
        httpUrl.append("?openid=").append(openid).append("&event_key=").append(event_key);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseObject(httpResponse.entityString);
    }

    public JSONObject getKeywordsMaterial(String openid, String keywords) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("keywords/match.json");
        httpUrl.append("?openid=").append(openid).append("&keywords=").append(keywords);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseObject(httpResponse.entityString);
    }

    public String getOpenid(String appid, String code) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("relay/openid.json");
        httpUrl.append("?appid=").append(appid).append("&code=").append(code);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public String getTicket(String appid) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("relay/ticket.json");
        httpUrl.append("?appid=").append(appid);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public String getShortUrl(String appid, String long_url) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("relay/shorturl.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("appid", appid);
        requestMap.put("long_url", long_url);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        String short_url = httpResponse.entityString;
        DATA.info("[getShortUrl]{}", short_url);
        return short_url;
    }

    public String createScene(String appid, Integer scene_id, Integer expire_seconds, String socket_url, Integer video_version) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("scene/init.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("appid", appid);
        requestMap.put("scene_id", scene_id + "");
        requestMap.put("expire_seconds", expire_seconds + "");
        requestMap.put("socket_url", socket_url);
        requestMap.put("video_version", video_version + "");
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return httpResponse.entityString;
    }

    public JSONObject createBind(String appid, Integer scene_id, String openid) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("scene/bind.json");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("appid", appid);
        requestMap.put("scene_id", scene_id + "");
        requestMap.put("openid", openid);
        HiveHttpResponse httpResponse = HiveHttpPost.postMap(httpUrl.toString(), requestMap, HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString);
    }

    public JSONObject queryScene(String appid, String code) {
        StringBuffer httpUrl = new StringBuffer(ApiConstants.COUPON_API).append("scene/query.json");
        httpUrl.append("?appid=").append(appid).append("&code=").append(code);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (StringUtils.isEmpty(httpResponse.entityString) || "error".equals(httpResponse.entityString)) {
            return null;
        }
        return JSONObject.parseObject(httpResponse.entityString);
    }
}
