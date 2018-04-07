package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;
import cn.com.lixihao.couponapi.mapper.ReceivingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/
@Service
public class ReceivingManager extends BaseManager {

    @Autowired
    ReceivingMapper receivingMapper;

    public Integer add(ReceivingCondition receivingCondition) {
        Integer result = receivingMapper.add(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public List<ReceivingCondition> getExpiredList(ReceivingCondition receivingCondition) {
        List<ReceivingCondition> result = receivingMapper.getExpiredList(receivingCondition);
        if (result == null) {
            return new ArrayList<ReceivingCondition>();
        }
        return result;
    }

    public List<ReceivingCondition> getEffectiveList(ReceivingCondition receivingCondition) {
        List<ReceivingCondition> result = receivingMapper.getEffectiveList(receivingCondition);
        if (result == null) {
            return new ArrayList<ReceivingCondition>();
        }
        return result;
    }

    public List<ReceivingCondition> queryList(ReceivingCondition receivingCondition) {
        List<ReceivingCondition> result = receivingMapper.queryList(receivingCondition);
        if (result == null) {
            return new ArrayList<ReceivingCondition>();
        }
        return result;
    }

    public ReceivingCondition get(ReceivingCondition receivingCondition) {
        return receivingMapper.get(receivingCondition);
    }

    public Integer update(ReceivingCondition receivingCondition) {
        Integer result = receivingMapper.update(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer updateUserInfo(ReceivingCondition receivingCondition) {
        Integer result = receivingMapper.updateUserInfo(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer dayCountByReceiving(ReceivingCondition receivingCondition) {
        Integer result = receivingMapper.dayCountByReceiving(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer totalCountByReceiving(ReceivingCondition receivingCondition) {
        Integer result = receivingMapper.totalCountByReceiving(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

}
