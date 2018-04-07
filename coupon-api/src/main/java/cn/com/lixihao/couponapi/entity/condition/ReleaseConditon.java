package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
public class ReleaseConditon extends BaseCondition implements Serializable{
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String release_id;
    public String release_name;
    public String stock_id_list;
    public Integer release_count;
    public String release_start_time;
    public String release_end_time;
    public Integer release_status;
}
