package cn.com.lixihao.couponapi.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.commons.http.HiveHttpEntityType;
import com.hiveview.commons.http.HiveHttpGet;
import com.hiveview.commons.http.HiveHttpPost;
import com.hiveview.commons.http.HiveHttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoweige on 2017/8/29.
 */
@Service
public class WeixinApi {

    private Logger log = LoggerFactory.getLogger(WeixinApi.class);

    public WeixinOauth2 getOauth2(String code, String appid, String secret) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token");
        httpUrl.append("?appid=").append(appid);
        httpUrl.append("&secret=").append(secret);
        httpUrl.append("&code=").append(code);
        httpUrl.append("&grant_type=authorization_code");
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return new WeixinOauth2();
        }
        return JSON.parseObject(httpResponse.entityString, WeixinOauth2.class);
    }

    public WeixinQrcodeTicket createQrcode(Integer scene_id, Integer expire_seconds, String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/qrcode/create");
        httpUrl.append("?access_token=").append(access_token);
        StringBuffer requestJson = new StringBuffer();
        requestJson.append("{\"expire_seconds\":").append(expire_seconds);
        requestJson.append(",\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":");
        requestJson.append(scene_id).append("}}}");
        HiveHttpResponse httpResponse = HiveHttpPost.postString(httpUrl.toString(), requestJson.toString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return new WeixinQrcodeTicket();
        }
        return JSON.parseObject(httpResponse.entityString, WeixinQrcodeTicket.class);
    }

    public String showQrcode(String encode_ticket) {
        StringBuffer httpUrl = new StringBuffer("https://mp.weixin.qq.com/cgi-bin/showqrcode");
        httpUrl.append("?ticket=").append(encode_ticket);
        return httpUrl.toString();
    }

    public String getShortUrl(String long_url, String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/shorturl");
        httpUrl.append("?access_token=").append(access_token);
        JSONObject requestJson = new JSONObject();
        requestJson.put("action", "long2short");
        requestJson.put("long_url", long_url);
        HiveHttpResponse httpResponse = HiveHttpPost.postString(httpUrl.toString(), requestJson.toJSONString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return "";
        }
        return JSON.parseObject(httpResponse.entityString).getString("short_url");
    }

    public String getShortUrl(String long_url) {
        String httpUrl = "http://suo.im/api.php?format=json&url=" + long_url;
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl, HiveHttpEntityType.STRING);
        log.info("[ShortUrl]request->{},response->{}", new Object[]{httpUrl, httpResponse.entityString});
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return null;
        }
        return JSON.parseObject(httpResponse.entityString).getString("url");
    }

    public JSONObject countMaterial(String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/material/get_materialcount");
        httpUrl.append("?access_token=").append(access_token);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return new JSONObject();
        }
        return JSON.parseObject(httpResponse.entityString);
    }

    public List<WeixinMaterialItem> getMaterial(String type, Integer offset, Integer count, String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/material/batchget_material");
        httpUrl.append("?access_token=").append(access_token);
        JSONObject requestJson = new JSONObject();
        requestJson.put("type", type);
        requestJson.put("offset", offset);
        requestJson.put("count", count);
        HiveHttpResponse httpResponse = HiveHttpPost.postString(httpUrl.toString(), requestJson.toJSONString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return new ArrayList<WeixinMaterialItem>();
        }
        JSONObject responseJson = JSON.parseObject(httpResponse.entityString);
        String itemArray = responseJson.getString("item");
        if (StringUtils.isEmpty(itemArray)) {
            return new ArrayList<WeixinMaterialItem>();
        }
        return JSON.parseArray(itemArray, WeixinMaterialItem.class);
    }

    public String getNewsMaterial(String media_id, String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/material/get_material");
        httpUrl.append("?access_token=").append(access_token);
        JSONObject requestJson = new JSONObject();
        requestJson.put("media_id", media_id);
        HiveHttpResponse httpResponse = HiveHttpPost.postString(httpUrl.toString(), requestJson.toJSONString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return "";
        }
        return httpResponse.entityString;
    }

    public String createMenu(String menu_content, String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/menu/create");
        httpUrl.append("?access_token=").append(access_token);
        HiveHttpResponse httpResponse = HiveHttpPost.postString(httpUrl.toString(), menu_content, HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return "";
        }
        return httpResponse.entityString;
    }

    public WeixinUserInfo getUserInfo(String openid, String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/user/info");
        httpUrl.append("?access_token=").append(access_token);
        httpUrl.append("&openid=").append(openid);
        httpUrl.append("&lang=zh_CN");
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return new WeixinUserInfo();
        }
        return JSON.parseObject(httpResponse.entityString, WeixinUserInfo.class);
    }

    public WeixinTicket getTicket(String access_token) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/ticket/getticket");
        httpUrl.append("?access_token=").append(access_token);
        httpUrl.append("&type=jsapi");
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return new WeixinTicket();
        }
        return JSON.parseObject(httpResponse.entityString, WeixinTicket.class);
    }

    public WeixinAccessToken getAccessToken(String appid, String secret) {
        StringBuffer httpUrl = new StringBuffer("https://api.weixin.qq.com/cgi-bin/token");
        httpUrl.append("?grant_type=client_credential");
        httpUrl.append("&appid=").append(appid);
        httpUrl.append("&secret=").append(secret);
        HiveHttpResponse httpResponse = HiveHttpGet.getEntity(httpUrl.toString(), HiveHttpEntityType.STRING);
        if (httpResponse.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(httpResponse.entityString)) {
            return new WeixinAccessToken();
        }
        return JSON.parseObject(httpResponse.entityString, WeixinAccessToken.class);
    }

    public static class WeixinAccessToken implements Serializable {

        private static final long serialVersionUID = 1L;
        public String access_token;
        public Integer expires_in;
    }

    public static class WeixinTicket implements Serializable {

        private static final long serialVersionUID = 1L;
        public String errcode;
        public String errmsg;
        public String ticket;
        public Integer expires_in;
    }

    public static class WeixinUserInfo implements Serializable {

        private static final long serialVersionUID = 1L;
        public String subscribe;
        public String openid;
        public String nickname;
        public String sex;
        public String language;
        public String city;
        public String province;
        public String country;
        public String headimgurl;
        public String subscribe_time;
        public String unionid;
        public String remark;
        public String groupid;
        public String tagid_list;
    }

    public static class WeixinMaterialItem implements Serializable {

        private static final long serialVersionUID = 1L;
        public String media_id;
        public String content;
        public String name;
        public String update_time;
        public String url;
    }

    public static class WeixinQrcodeTicket implements Serializable {

        private static final long serialVersionUID = 1L;
        public String ticket;
        public Integer expire_seconds;
        public String url;
    }

    public static class WeixinOauth2 implements Serializable {

        private static final long serialVersionUID = 1L;
        public String access_token;
        public Integer expires_in;
        public String refresh_token;
        public String openid;
        public String scope;

        public WeixinOauth2() {
            super();
        }

        public WeixinOauth2(String access_token, String refresh_token, String openid) {
            this.access_token = access_token;
            this.refresh_token = refresh_token;
            this.openid = openid;
        }

    }
}
