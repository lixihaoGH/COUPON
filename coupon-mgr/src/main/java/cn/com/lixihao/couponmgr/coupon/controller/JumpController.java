package cn.com.lixihao.couponmgr.coupon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/coupon")
@Controller
public class JumpController {

    @RequestMapping(value = "/get")
    public String browseCoupon(HttpServletRequest request, String coupon_stock_id) {
        request.setAttribute("coupon_stock_id", coupon_stock_id);
        return "coupon/couponInnerPage";
    }

    @RequestMapping(value = "/create")
    public String createCoupon() {
        return "coupon/createNewCoupon";
    }

    @RequestMapping(value = "/getList")
    public String couponList() {
        return "coupon/couponList";
    }

    @RequestMapping(value = "/releaseList")
    public String releaseList() {
        return "coupon/couponRelease";
    }

    @RequestMapping(value = "/entrance")
    public String entrance() {
        return "coupon/couponEntrance";
    }

    @RequestMapping(value = "/statistics")
    public String statistics() {
        return "coupon/couponStatistics";
    }
}
