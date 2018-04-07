package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.BindingCondition;
import cn.com.lixihao.couponapi.mapper.BindingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * create by lixihao on 2017/12/25.
 **/
@Repository("bindingDao")
public class BindingManager extends BaseManager {

    @Autowired
    BindingMapper bindingMapper;

    public Integer add(BindingCondition bindingCondition) {
        Integer result = bindingMapper.add(bindingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public BindingCondition get(BindingCondition bindingCondition) {
        return bindingMapper.get(bindingCondition);
    }

    public Integer update(BindingCondition bindingCondition) {
        Integer result = bindingMapper.update(bindingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }
}
