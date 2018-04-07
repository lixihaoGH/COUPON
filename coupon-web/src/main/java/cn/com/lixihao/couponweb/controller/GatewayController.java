package cn.com.lixihao.couponweb.controller;

import cn.com.lixihao.couponweb.service.GatewayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by lixihao on 2018/3/22.
 **/
@Controller
@RequestMapping(value = "/gateway")
public class GatewayController {


    @Autowired
    GatewayService gatewayService;

    @ResponseBody
    @RequestMapping(value = "/wx/token")
    public String verifyToken(String signature, String timestamp, String nonce, String echostr) {
        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(echostr)) {
            return "";
        }
        return gatewayService.verifyWXToken(signature, timestamp, nonce, echostr);
    }
}
