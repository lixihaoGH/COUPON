package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class StatisticsCondition extends BaseCondition{
    public String coupon_stock_id;
    public String coupon_stock_name;
    public String release_id;
    public Integer release_count;
}
