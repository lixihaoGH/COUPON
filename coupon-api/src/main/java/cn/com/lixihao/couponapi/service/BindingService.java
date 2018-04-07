package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.entity.condition.BindingCondition;
import cn.com.lixihao.couponapi.manager.BindingManager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * create by lixihao on 2017/12/27.
 **/
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BindingService {

    @Autowired
    BindingManager bindingDao;

    public String get(BindingCondition bindingCondition) {
        return JSONObject.toJSONString(bindingDao.get(bindingCondition));
    }


    public String add(BindingCondition bindingCondition) {
        Integer result = bindingDao.add(bindingCondition);
        if (result != 1) {
            return "error";
        }
        return "ok";
    }

    public String update(BindingCondition bindingCondition) {
        Integer result = bindingDao.update(bindingCondition);
        if (result == 0) {
            return "error";
        }
        return "ok";
    }

}
