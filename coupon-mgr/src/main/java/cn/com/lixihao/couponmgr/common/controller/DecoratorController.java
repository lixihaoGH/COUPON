package cn.com.lixihao.couponmgr.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chimeilong on 2017/6/29.
 */
@Controller
public class DecoratorController {
    @RequestMapping(value = "decorator")
    public String decorator() {
        return "derorator/decorator";
    }

}
