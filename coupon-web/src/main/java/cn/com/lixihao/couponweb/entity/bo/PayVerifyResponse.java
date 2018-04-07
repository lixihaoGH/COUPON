package cn.com.lixihao.couponweb.entity.bo;

import cn.com.lixihao.couponweb.entity.UnifiedResponse;
import cn.com.lixihao.couponweb.entity.vo.VerifyFailDetail;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2018/1/22.
 **/
@EqualsAndHashCode(callSuper = true)
public class PayVerifyResponse extends UnifiedResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public VerifyFailDetail fail_detail;

    public PayVerifyResponse(String return_code, String return_message) {
        super(return_code, return_message);
    }

    public PayVerifyResponse(String return_code, String return_message, VerifyFailDetail fail_detail) {
        super(return_code, return_message);
        this.fail_detail = fail_detail;
    }
}
