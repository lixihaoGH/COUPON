package cn.com.lixihao.couponweb.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * create by lixihao on 2018/1/22.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class PayVerifyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public String trade_no;
    public Integer trade_status;
    public String coupon_detail;

    public static boolean isInvalid(PayVerifyRequest payVerifyRequest) {
        if (StringUtils.isEmpty(payVerifyRequest.trade_no) || payVerifyRequest.trade_status == null || StringUtils.isEmpty(payVerifyRequest.coupon_detail)) {
            return true;
        }
        return false;
    }
}
