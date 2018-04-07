package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
public class StockCondition extends BaseCondition implements Serializable {
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String coupon_stock_id;
    public String coupon_stock_name;
    public String create_time;
    public Integer preferential_type; //1-满减、2-折扣
    public Integer preferential_amount;
    public Integer discount;

}
