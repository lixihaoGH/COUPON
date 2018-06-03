package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.SmsCaptchaCondition;
import cn.com.lixihao.couponapi.mapper.SmsCaptchaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * create by lixihao on 2018/2/27.
 **/
@Repository("smsCaptchaDao")
public class SmsCaptchaDao extends BaseDao {

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
