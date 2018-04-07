package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/25.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class ReceivingCondition extends BaseCondition implements Serializable {

    private Integer id;
    private String coupon_id;
    private String coupon_stock_id;
    private String coupon_stock_name;
    private String release_id;
    private String user_id;
    private String phone_number;
    private String openid;
    private String receiving_time;
    private Integer coupon_status;
    private Integer preferential_type;
    private Integer reach_amount;
    private Integer preferential_amount;
    private Integer discount;
    private String effective_time;
    private String expired_time;
    private Integer device_type;
    private String restriction_description;
}
