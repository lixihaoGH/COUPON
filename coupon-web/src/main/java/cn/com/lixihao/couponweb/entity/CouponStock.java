package cn.com.lixihao.couponweb.entity;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/25.
 **/

public class CouponStock implements Serializable{

    private static final long serialVersionUID = 1L;

    public String coupon_stock_id;
    public String coupon_stock_name;
    public Integer preferential_type;
    public Integer preferential_amount;
    public Integer discount;
    public String restriction_description;//限制描述
    public Integer goods_range;//范围限制：1-全部；2-部分
    public Integer reach_amount;
    public Integer effective_duration;
    public String effective_time;
    public String expired_time;


}
