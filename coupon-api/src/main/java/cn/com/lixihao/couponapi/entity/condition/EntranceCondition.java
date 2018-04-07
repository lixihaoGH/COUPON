package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EntranceCondition extends BaseCondition{

    public Integer id;
    public String entrance_name;
    public String release_id_list;
    public String update_time;
    public String create_time;
}
