package cn.com.lixihao.couponweb.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * create by lixihao on 2017/12/26.
 **/
public class CouponDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer coupon_amount;
    public Integer coupon_count;
    public List<CouponItem> coupon_item;
}
