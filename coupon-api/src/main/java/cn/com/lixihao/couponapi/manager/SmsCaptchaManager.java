package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.SmsCaptchaCondition;
import cn.com.lixihao.couponapi.mapper.SmsCaptchaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by lixihao on 2018/2/27.
 **/
@Service
public class SmsCaptchaManager {

    @Autowired
    SmsCaptchaMapper smsCaptchaMapper;

    public SmsCaptchaCondition get(SmsCaptchaCondition smsCaptchaCondition) {
        return smsCaptchaMapper.get(smsCaptchaCondition);
    }

    public Integer add(SmsCaptchaCondition smsCaptchaCondition) {
        Integer result = smsCaptchaMapper.add(smsCaptchaCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer update(SmsCaptchaCondition smsCaptchaCondition) {
        Integer result = smsCaptchaMapper.update(smsCaptchaCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer delete(SmsCaptchaCondition smsCaptchaCondition) {
        Integer result = smsCaptchaMapper.delete(smsCaptchaCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }
}
