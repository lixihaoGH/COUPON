package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;
import cn.com.lixihao.couponapi.manager.ReceivingRestrictionManager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by lixihao on 2018/1/3.
 **/
@Service
public class ReceivingRestrictionService {

    @Autowired
    ReceivingRestrictionManager receivingRestrictionManager;

    public String get(String release_id) {
        ReceivingRestrictionCondition receivingRestrictionCondition = new ReceivingRestrictionCondition();
        receivingRestrictionCondition.setRelease_id(release_id);
        ReceivingRestrictionCondition result = receivingRestrictionManager.get(receivingRestrictionCondition);
        if (result == null) {
            return "error";
        }
        return JSONObject.toJSONString(result);
    }
}
