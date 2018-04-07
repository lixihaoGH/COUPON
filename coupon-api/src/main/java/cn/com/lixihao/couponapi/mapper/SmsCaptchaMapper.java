package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.condition.SmsCaptchaCondition;
import org.springframework.stereotype.Repository;

/**
 * create by lixihao on 2018/2/27.
 **/
@Repository
public interface SmsCaptchaMapper {

    SmsCaptchaCondition get(SmsCaptchaCondition smsCaptchaCondition);

    Integer add(SmsCaptchaCondition smsCaptchaCondition);

    Integer update(SmsCaptchaCondition smsCaptchaCondition);

    Integer delete(SmsCaptchaCondition smsCaptchaCondition);
}
