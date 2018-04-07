package cn.com.lixihao.couponweb.entity.bo;

import cn.com.lixihao.couponweb.entity.UnifiedResponse;
import cn.com.lixihao.couponweb.entity.vo.ReceivingDetail;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/26.
 **/
@EqualsAndHashCode(callSuper = true)
public class ReceivingResponse extends UnifiedResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public ReceivingDetail coupon_detail;

    public ReceivingResponse(String return_code, String return_message, ReceivingDetail coupon_detail) {
        super(return_code, return_message);
        this.coupon_detail = coupon_detail;
    }

    public ReceivingResponse(String return_code, String return_message) {
        super(return_code, return_message);
    }

}
