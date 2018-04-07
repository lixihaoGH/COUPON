package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2018/2/27.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class SmsCaptchaCondition implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String phone;
    private String sms_captcha;
    private Long expiry_time;
}
