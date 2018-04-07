package cn.com.lixihao.couponapi.entity.result;

import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = false)
@Data
public class EntranceResponse implements Comparable{
    public Integer id;
    public String entrance_name;
    public String goto_url;
    public String release_id_list;
    public String update_time;
    public String create_time;

    //@Override
    public int compareTo(Object o) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(SysConstants.DATE_FORMAT);
        DateTime odate = DateTime.parse(((EntranceCondition) o).getCreate_time(), dateTimeFormatter);
        DateTime date = DateTime.parse(this.create_time, dateTimeFormatter);
        return date.compareTo(odate);
    }
}
