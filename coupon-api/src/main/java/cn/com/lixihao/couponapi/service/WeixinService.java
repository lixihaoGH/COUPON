package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.api.WeixinApi;
import cn.com.lixihao.couponapi.constants.WeixinConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.HashMap;
import java.util.Map;


@Service
public class WeixinService {

    @Autowired
    WeixinApi weixinApi;
    @Autowired
    ShardedJedisPool shardedJedisPool;

    private static final Map<String, AppConfig> WEIXIN_APP_CONFIG = new HashMap<String, AppConfig>();

    static {
        AppConfig testcaseConfig = new AppConfig();
        testcaseConfig.appid = WeixinConstants.TESTCASE_APP_ID;
        testcaseConfig.secret = WeixinConstants.TESTCASE_SECRET;
        WEIXIN_APP_CONFIG.put(testcaseConfig.appid, testcaseConfig);
        AppConfig hiveviewConfig = new AppConfig();
        hiveviewConfig.appid = WeixinConstants.HIVEVIEW_APP_ID;
        hiveviewConfig.secret = WeixinConstants.HIVEVIEW_SECRET;
        WEIXIN_APP_CONFIG.put(hiveviewConfig.appid, hiveviewConfig);
    }

    public AppConfig getAppConfig(String appid) {
        return WEIXIN_APP_CONFIG.get(appid);
    }


    public static class AppConfig {
        public String appid;
        public String secret;
    }

    public String refreshLocalValue(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.del(key);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    public String getOpenid(String code, String appid, String secret) {
        WeixinApi.WeixinOauth2 weixinOauth2 = this.getOauth2(code, appid, secret);
        if (weixinOauth2 != null) {
            return weixinOauth2.openid;
        }
        return null;
    }

    public WeixinApi.WeixinOauth2 getOauth2(String code, String appid, String secret) {
        //code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
        String key = "weixin_user_oauth2_" + appid + "_" + code;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                Map<String, String> hash = shardedJedis.hgetAll(key);
                return new WeixinApi.WeixinOauth2(hash.get("access_token"), hash.get("refresh_token"), hash.get("openid"));
            }
            synchronized (key) {
                if (shardedJedis.exists(key)) {
                    Map<String, String> hash = shardedJedis.hgetAll(key);
                    return new WeixinApi.WeixinOauth2(hash.get("access_token"), hash.get("refresh_token"), hash.get("openid"));
                }
                WeixinApi.WeixinOauth2 weixinOauth2 = weixinApi.getOauth2(code, appid, secret);
                if (weixinOauth2 != null && StringUtils.isNotEmpty(weixinOauth2.access_token)) {
                    Map<String, String> hash = new HashMap<String, String>();
                    hash.put("access_token", weixinOauth2.access_token);
                    hash.put("refresh_token", weixinOauth2.refresh_token);
                    hash.put("openid", weixinOauth2.openid);
                    shardedJedis.hmset(key, hash);
                    shardedJedis.expire(key, 60);
                    return weixinOauth2;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    public String getShortUrl(String long_url, String appid, String secret) {
        return weixinApi.getShortUrl(long_url);
    }

    public WeixinApi.WeixinUserInfo getUserInfo(String openid, String appid, String secret) {
        String access_token = this.getAccessToken(appid, secret);
        return weixinApi.getUserInfo(openid, access_token);
    }

    public String getTicket(String appid, String secret) {
        String key = "weixin_public_ticket_" + appid;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                return shardedJedis.get(key);
            }
            synchronized (key) {
                if (shardedJedis.exists(key)) {
                    return shardedJedis.get(key);
                }
                String access_token = this.getAccessToken(appid, secret);
                WeixinApi.WeixinTicket weixinTicket = weixinApi.getTicket(access_token);
                if (weixinTicket != null && StringUtils.isNotEmpty(weixinTicket.ticket)) {
                    shardedJedis.set(key, weixinTicket.ticket);
                    shardedJedis.expire(key, weixinTicket.expires_in - 200);
                    return weixinTicket.ticket;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    public String getAccessToken(String appid, String secret) {
        String key = "weixin_public_token_" + appid;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                return shardedJedis.get(key);
            }
            synchronized (key) {
                if (shardedJedis.exists(key)) {
                    return shardedJedis.get(key);
                }
                WeixinApi.WeixinAccessToken weixinAccessToken = weixinApi.getAccessToken(appid, secret);
                if (weixinAccessToken != null && StringUtils.isNotEmpty(weixinAccessToken.access_token)) {
                    shardedJedis.set(key, weixinAccessToken.access_token);
                    shardedJedis.expire(key, weixinAccessToken.expires_in - 200);
                    return weixinAccessToken.access_token;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

}
