package cn.com.lixihao.couponweb.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * create by lixihao on 2018/1/5.
 **/

public class CouponReceiving implements Serializable{

    private static final long serialVersionUID = 1L;

    public Integer id;
    public String coupon_id;
    public String coupon_stock_id;
    public String coupon_stock_name;
    public String release_id;
    public String user_id;
    public String phone_number;
    public String openid;
    public String receiving_time;
    public Integer coupon_status;
    public Integer preferential_type;
    public Integer reach_amount;
    public Integer preferential_amount;
    public Integer discount;
    public String effective_time;
    public String expired_time;
    public Integer device_type;
    public String restriction_description;

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
