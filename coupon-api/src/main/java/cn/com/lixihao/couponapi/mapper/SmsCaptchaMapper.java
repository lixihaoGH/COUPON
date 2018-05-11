package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.SmsCaptchaCondition;

/**
 * create by lixihao on 2018/2/27.
 **/
@SqlMapper
public interface SmsCaptchaMapper {

    SmsCaptchaCondition get(SmsCaptchaCondition smsCaptchaCondition);

    Integer add(SmsCaptchaCondition smsCaptchaCondition);

    Integer update(SmsCaptchaCondition smsCaptchaCondition);

    Integer delete(SmsCaptchaCondition smsCaptchaCondition);
}
