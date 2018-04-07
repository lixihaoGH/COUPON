package cn.com.lixihao.couponweb.entity.vo;

import java.io.Serializable;

/**
 * create by lixihao on 2018/1/22.
 **/

public class VerifyFailItem implements Serializable {

    private static final long serialVersionUID = 1L;

    public String coupon_id;
    public Integer amount_error;//1-error;null-ok
    public Integer status_error;
}
