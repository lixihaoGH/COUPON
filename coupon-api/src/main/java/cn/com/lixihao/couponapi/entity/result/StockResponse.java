package cn.com.lixihao.couponapi.entity.result;

import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = false)
@Data
public class StockResponse implements Comparable{
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String coupon_stock_id;
    public String coupon_stock_name;
    public String create_time;
    public Integer preferential_type; //1-满减、2-折扣
    public Integer preferential_amount;
    public Integer discount;
    public Integer effective_duration;
    public String effective_time;
    public String expired_time;

    //@Override
    public int compareTo(Object o) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(SysConstants.DATE_FORMAT);
        DateTime odate = DateTime.parse(((StockCondition) o).getCreate_time(), dateTimeFormatter);
        DateTime date = DateTime.parse(this.getCreate_time(), dateTimeFormatter);
        return date.compareTo(odate);
    }
}
