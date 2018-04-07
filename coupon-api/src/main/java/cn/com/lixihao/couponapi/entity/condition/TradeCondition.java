package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * create by lixihao on 2017/12/25.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class TradeCondition extends BaseCondition {

    private Integer id;
    private String coupon_id;
    private String coupon_stock_id;
    private String create_time;
    private String user_id;
    private String trade_no;
    private Integer total_amount;
    private Integer deduction_amount;
    private Integer payment_amount;
    private Integer trade_status;
    private String release_id;
}
