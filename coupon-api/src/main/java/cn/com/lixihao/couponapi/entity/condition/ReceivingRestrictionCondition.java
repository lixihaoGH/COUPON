package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ReceivingRestrictionCondition {
    public Integer id;
    public String release_id;
    public Integer box_day_max;
    public Integer box_total_max;
    public Integer phone_day_max;
    public Integer phone_total_max;
}
