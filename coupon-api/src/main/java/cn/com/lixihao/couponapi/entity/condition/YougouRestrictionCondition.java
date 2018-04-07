package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
public class YougouRestrictionCondition implements Serializable{
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String coupon_stock_id;
    public Integer goods_range;
    public Integer reach_amount;
    public Integer effective_duration;
    public String effective_time;
    public String expired_time;
    public String selected_goods_category;
    public String selected_first_level_list;
    public String selected_second_level_list;
    public String selected_third_level_list;
    public String selected_goods_list;
    public String excluded_goods_category;
    public String excluded_first_level_list;
    public String excluded_second_level_list;
    public String excluded_third_level_list;
    public String excluded_goods_list;
    public String restriction_description;
}
