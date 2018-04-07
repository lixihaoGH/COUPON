package cn.com.lixihao.couponapi.entity.result;

import cn.com.lixihao.couponapi.entity.condition.ReleaseConditon;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class ReleaseResponse implements Serializable,Comparable{
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String release_id;
    public String release_name;
    public String stock_id_list;
    public Integer release_count;
    public String release_start_time;
    public String release_end_time;
    public Integer release_status;

    //@Override
    public int compareTo(Object o) {
        String omills = ((ReleaseConditon)o).getRelease_id().substring(4, 17);
        String mills = this.getRelease_id().substring(4, 17);
        long otimestamp = Long.parseLong(omills);
        long timestamp = Long.parseLong(mills);
        DateTime odate = new DateTime(new Date(otimestamp));
        DateTime date = new DateTime(new Date(timestamp));
        return date.compareTo(odate);
    }
}
