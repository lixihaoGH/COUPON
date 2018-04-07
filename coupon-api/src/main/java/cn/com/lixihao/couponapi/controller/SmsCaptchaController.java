package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.result.SmsCaptchaResponse;
import cn.com.lixihao.couponapi.service.SmsCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * create by lixihao on 2018/2/26.
 **/
@RestController
@RequestMapping(value = "/captcha/sms")
public class SmsCaptchaController {

    private Logger log = LoggerFactory.getLogger(SmsCaptchaController.class);

    @Autowired
    SmsCaptchaService smsCaptchaService;


    @RequestMapping(value = "/send")
    public SmsCaptchaResponse send(String phone, Integer captchaSize, Integer intervalSecond, String contentTitle, Integer effectiveMinutes) {
        log.info("[SmsCaptchaSend]phone->{}", phone);
        SmsCaptchaResponse smsCaptchaResponse = smsCaptchaService.addCaptcha(phone, captchaSize, intervalSecond, contentTitle, effectiveMinutes);
        log.info("[SmsCaptchaSendResult]phone->{},response->{}", new Object[]{phone, smsCaptchaResponse});
        return smsCaptchaResponse;
    }

    @RequestMapping(value = "/verify")
    public SmsCaptchaResponse verify(String phone, String smsCaptcha) {
        log.info("[SmsCaptchaVerify]phone->{},smsCaptcha->{}", new Object[]{phone, smsCaptcha});
        SmsCaptchaResponse smsCaptchaResponse = smsCaptchaService.verify(phone, smsCaptcha);
        log.info("[SmsCaptchaVerifyResult]phone->{},response->{}", new Object[]{phone, smsCaptchaResponse});
        return smsCaptchaResponse;
    }

    @RequestMapping(value = "/delete")
    public SmsCaptchaResponse delete(HttpServletRequest request) {
        Long expiryTime = System.currentTimeMillis();
        log.info("[SmsCaptchaDelete]request_ip->{},expiryTime->{}", new Object[]{request.getRemoteAddr(), expiryTime});
        try {
            Integer deletedRows = smsCaptchaService.delete(expiryTime);
            log.info("[SmsCaptchaDeleteResult]expiryTime->{},deletedRows->{}", new Object[]{expiryTime, deletedRows});
            return new SmsCaptchaResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.SUCCESS);
        } catch (Exception e) {
            log.error("[SmsCaptchaDeleteResult]error->{}", e.getMessage());
            return null;
        }
    }
}
