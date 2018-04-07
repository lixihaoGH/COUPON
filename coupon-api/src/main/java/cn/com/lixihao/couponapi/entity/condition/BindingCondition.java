package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/25.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class BindingCondition extends BaseCondition implements Serializable {

    private Integer id;
    private String device_id;
    private String phone_number;
    private String user_id;
    private String openid;
}
