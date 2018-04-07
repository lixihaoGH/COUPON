package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
public class StatCondition implements Serializable{
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String coupon_stock_id;
    public String release_id;
    public Integer remaining_count;
}
