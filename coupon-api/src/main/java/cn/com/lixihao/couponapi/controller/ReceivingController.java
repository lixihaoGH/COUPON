package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;
import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import cn.com.lixihao.couponapi.service.ReceivingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * create by lixihao on 2017/12/28.
 **/
@RestController
@RequestMapping(value = "/receiving")
public class ReceivingController {

    private Logger log = LoggerFactory.getLogger(ReceivingController.class);

    @Autowired
    ReceivingService receivingService;


    @RequestMapping(value = "/get")
    public String get(ReceivingCondition receivingCondition) {
        return receivingService.get(receivingCondition);
    }


    @RequestMapping(value = "/getlist")
    public String getList(ReceivingCondition receivingCondition, Integer page_index, Integer page_size) {
        return receivingService.getList(receivingCondition, page_index, page_size);
    }

    @RequestMapping(value = "/count_user_receiving")
    public String countUserReceiving(ReceivingCondition receivingCondition, String stock_id_list) {
        return receivingService.countUserReceiving(receivingCondition, stock_id_list);
    }

    @RequestMapping(value = "/count_remaining_release")
    public String countRemainingRelease(String release_id) {
        return receivingService.countRemainingRelease(release_id);
    }

    @RequestMapping(value = "/add")
    public String add(ReceivingCondition receivingCondition, String stock_id_list) {
        log.info("[ReceivingAdd]request->{}", receivingCondition);
        try {
            receivingService.add(receivingCondition, stock_id_list);
            log.info("[ReceivingAddResult]release_id->{},phone->{},result->{}", new Object[]{receivingCondition.getRelease_id(), receivingCondition.getPhone_number(), "ok"});
            return "ok";
        } catch (Exception e) {
            log.error("[ReceivingAddResult]release_id->{},phone->{},error->{}", new Object[]{receivingCondition.getRelease_id(), receivingCondition.getPhone_number(), e.getMessage()});
            return "error";
        }
    }

    @RequestMapping(value = "/update")
    public String update(ReceivingCondition receivingCondition) {
        log.info("[ReceivingUpdate]request->{}", receivingCondition);
        try {
            String result = receivingService.update(receivingCondition);
            log.info("[ReceivingUpdateResult]request->{},result->{}", new Object[]{receivingCondition, result});
            return result;
        } catch (Exception e) {
            log.error("[ReceivingUpdateResult]request->{},error->{}", new Object[]{receivingCondition, e.getMessage()});
            return "error";
        }
    }

    @RequestMapping(value = "/yougou_stock")
    public String getStock(String stock_id_list) {
        try {
            return receivingService.getYougouStock(stock_id_list);
        } catch (Exception e) {
            log.error("[YougouStockGet]error->{}", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/yougou_coupon")
    public String yougouCoupon(YougouRestrictionCondition yougouRestrictionCondition, String user_id, String phone_number) {
        return receivingService.getYougouCoupon(yougouRestrictionCondition, user_id, phone_number);
    }

    @RequestMapping(value = "/verify_yougou_coupon")
    public String verifyYougouCoupon(YougouRestrictionCondition yougouRestrictionCondition) {
        return receivingService.verifyYougouCoupon(yougouRestrictionCondition);
    }

    @RequestMapping(value = "/update_user_info")
    public String updateUserInfo(ReceivingCondition receivingCondition) {
        log.info("[UserInfoUpdate]request->{}", receivingCondition);
        try {
            String result = receivingService.updateUserInfo(receivingCondition);
            log.info("[UserInfoUpdateResult]request->{},result->{}", new Object[]{receivingCondition.getUser_id(), result});
            return result;
        } catch (Exception e) {
            log.error("[UserInfoUpdateResult]request->{},error->{}", new Object[]{receivingCondition.getUser_id(), e.getMessage()});
            return "error";
        }
    }

    @RequestMapping(value = "/openid")
    public String queryOpenid(String appid, String code) {
        log.info("[OpenidQuery]appid->{},code->{}", new Object[]{appid, code});
        try {
            String openid = receivingService.queryOpenid(appid, code);
            log.info("[OpenidQueryResult]appid->{},openid->{}", new Object[]{appid, openid});
            return openid;
        } catch (Exception e) {
            log.error("[OpenidQueryResult]appid->{},error->{}", new Object[]{appid, e.getMessage()});
            return "error";
        }
    }
}