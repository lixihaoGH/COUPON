package cn.com.lixihao.couponweb.entity.vo;

import cn.com.lixihao.couponweb.entity.CouponReceiving;

import java.io.Serializable;
import java.util.List;

/**
 * create by lixihao on 2018/1/5.
 **/

public class QueryCouponDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer coupon_count;
    public List<CouponReceiving> coupon_list;

    public QueryCouponDetail(Integer coupon_count, List<CouponReceiving> coupon_list) {
        this.coupon_count = coupon_count;
        this.coupon_list = coupon_list;
    }

    public QueryCouponDetail(Integer coupon_count) {
        this.coupon_count = coupon_count;

    }
}
