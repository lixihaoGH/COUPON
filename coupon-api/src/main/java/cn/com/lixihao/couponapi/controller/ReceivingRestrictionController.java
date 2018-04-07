package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.service.ReceivingRestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by lixihao on 2018/1/3.
 **/
@RestController
@RequestMapping(value = "receivingrestriction")
public class ReceivingRestrictionController {

    @Autowired
    ReceivingRestrictionService receivingRestrictionService;

    @RequestMapping(value = "/get")
    public String get(String release_id) {
        return receivingRestrictionService.get(release_id);
    }
}
