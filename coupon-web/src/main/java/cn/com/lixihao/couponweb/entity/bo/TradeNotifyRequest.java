package cn.com.lixihao.couponweb.entity.bo;

import cn.com.lixihao.couponweb.constant.SysConstants;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/22.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class TradeNotifyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public String user_id;
    public String user_phone;
    public String trade_no;
    public Integer trade_status;//0-初始化;2-支付完成;1-交易取消
    public Integer total_amount;
    public Integer deduction_amount;
    public Integer payment_amount;
    public String coupon_id;
    public String coupon_stock_id;
    public Integer coupon_status;
    public String goods_category;
    public String grade_1st_category_sn;
    public String grade_2nd_category_sn;
    public String grade_3rd_category_sn;
    public String good_sku_sn;


    public String toString() {
        return JSON.toJSONString(this);
    }

    public static boolean isInvalid(TradeNotifyRequest tradeNotifyRequest) {
        if (StringUtils.isEmpty(tradeNotifyRequest.user_id)
                || StringUtils.isEmpty(tradeNotifyRequest.user_phone)
                || StringUtils.isEmpty(tradeNotifyRequest.trade_no)
                || tradeNotifyRequest.total_amount == null
                || tradeNotifyRequest.deduction_amount == null
                || tradeNotifyRequest.payment_amount == null
                || tradeNotifyRequest.trade_status == null
                || tradeNotifyRequest.coupon_status == null
                || StringUtils.isEmpty(tradeNotifyRequest.coupon_id)
                || StringUtils.isEmpty(tradeNotifyRequest.coupon_stock_id)) {
            return true;
        }
        if (tradeNotifyRequest.trade_status.equals(SysConstants.TRADE_STATUS_INIT)) {
            if (StringUtils.isEmpty(tradeNotifyRequest.grade_1st_category_sn)
                    || StringUtils.isEmpty(tradeNotifyRequest.grade_2nd_category_sn)
                    || StringUtils.isEmpty(tradeNotifyRequest.grade_3rd_category_sn)
                    || StringUtils.isEmpty(tradeNotifyRequest.goods_category)
                    || StringUtils.isEmpty(tradeNotifyRequest.good_sku_sn)) {
                return true;
            }
        }
        return false;
    }
}
