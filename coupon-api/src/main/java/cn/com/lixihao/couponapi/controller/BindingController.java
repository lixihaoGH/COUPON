package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.condition.BindingCondition;
import cn.com.lixihao.couponapi.service.BindingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by lixihao on 2017/12/27.
 **/
@RestController
@RequestMapping(value = "/binding")
public class BindingController {

    private Logger log = LoggerFactory.getLogger(BindingController.class);

    @Autowired
    BindingService bindingService;

    @RequestMapping(value = "/get")
    public String get(BindingCondition bindingCondition) {
        return bindingService.get(bindingCondition);
    }


    @RequestMapping(value = "/add")
    public String add(BindingCondition bindingCondition) {
        log.info("[BindingAdd]phone_number->{}", bindingCondition.getPhone_number());
        try {
            String result = bindingService.add(bindingCondition);
            log.info("[BindingAddResult]phone_number->{},result->{}", new Object[]{bindingCondition.getPhone_number(), result});
            return result;
        } catch (Exception e) {
            log.error("[BindingAddResult]phone_number->{},error->{}", new Object[]{bindingCondition.getPhone_number(), e.getMessage()});
            return "error";
        }
    }

    @RequestMapping(value = "/update")
    public String update(BindingCondition bindingCondition) {
        log.info("[BindingUpdate]phone_number->{}", bindingCondition.getPhone_number());
        try {
            String result = bindingService.update(bindingCondition);
            log.info("[BindingUpdateResult]phone_number->{},result->{}", new Object[]{bindingCondition.getPhone_number(), result});
            return result;
        } catch (Exception e) {
            log.error("[BindingUpdateResult]phone_number->{},error->{}", new Object[]{bindingCondition.getPhone_number(), e.getMessage()});
            return "error";
        }
    }

}
