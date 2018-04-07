package cn.com.lixihao.couponapi.entity.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class StatisticsResponse {

    public String coupon_stock_id;
    public String coupon_stock_name;
    public String release_id;
    public Integer release_count;
    public Integer receiving_count;
    public Integer used_count;
    public Double usage_rate;
    public String total_payment_amount;
    public Integer remaining_count;
}
