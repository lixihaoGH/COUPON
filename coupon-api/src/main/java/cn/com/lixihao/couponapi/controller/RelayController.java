package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.service.WeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haoweige on 2017/8/30.
 */
@RestController
@RequestMapping(value = "/relay")
public class RelayController {

    private Logger log = LoggerFactory.getLogger(RelayController.class);

    @Autowired
    WeixinService weixinService;

    @RequestMapping(value = "/token")
    public String token(String appid, String timestamp, String sign) {
        log.info("[tokenGet]appid->{}", new Object[]{appid});
        WeixinService.AppConfig appConfig = weixinService.getAppConfig(appid);
        if (appConfig == null) {
            return "error";
        }
        String token = weixinService.getAccessToken(appid, appConfig.secret);
        log.info("[tokenGet]appid->{},token->{}", new Object[]{appid, token});
        return token;
    }

    @RequestMapping(value = "/ticket")
    public String ticket(String appid, String timestamp, String sign) {
        log.info("[ticketGet]appid->{}", new Object[]{appid});
        WeixinService.AppConfig appConfig = weixinService.getAppConfig(appid);
        if (appConfig == null) {
            return "error";
        }
        String ticket = weixinService.getTicket(appid, appConfig.secret);
        log.info("[ticketGet]appid->{},ticket->{}", new Object[]{appid, ticket});
        return ticket;
    }

    @RequestMapping(value = "/openid")
    public String openid(String code, String appid, String timestamp, String sign) {
        log.info("[openidGet]code->{},appid->{}", new Object[]{code, appid});
        WeixinService.AppConfig appConfig = weixinService.getAppConfig(appid);
        if (appConfig == null) {
            return "error";
        }
        String openid = weixinService.getOpenid(code, appid, appConfig.secret);
        log.info("[openidGet]code->{},openid->{}", new Object[]{code, appid});
        return openid;
    }

    @RequestMapping(value = "/refresh")
    public String refresh(String key, String timestamp, String sign) {
        String result = weixinService.refreshLocalValue(key);
        log.info("[LocalRefresh]key->{},result->{}", new Object[]{key, result});
        return result;
    }
}
