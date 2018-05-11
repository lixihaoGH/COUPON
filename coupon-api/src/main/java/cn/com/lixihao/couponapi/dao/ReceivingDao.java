package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/
@Repository("receivingDao")
public class ReceivingDao extends BaseDao {

    @Autowired
    ReceivingDao receivingDao;

    public Integer add(ReceivingCondition receivingCondition) {
        Integer result = receivingDao.add(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public List<ReceivingCondition> getExpiredList(ReceivingCondition receivingCondition) {
        List<ReceivingCondition> result = receivingDao.getExpiredList(receivingCondition);
        if (result == null) {
            return new ArrayList<ReceivingCondition>();
        }
        return result;
    }

    public List<ReceivingCondition> getEffectiveList(ReceivingCondition receivingCondition) {
        List<ReceivingCondition> result = receivingDao.getEffectiveList(receivingCondition);
        if (result == null) {
            return new ArrayList<ReceivingCondition>();
        }
        return result;
    }

    public List<ReceivingCondition> queryList(ReceivingCondition receivingCondition) {
        List<ReceivingCondition> result = receivingDao.queryList(receivingCondition);
        if (result == null) {
            return new ArrayList<ReceivingCondition>();
        }
        return result;
    }

    public ReceivingCondition get(ReceivingCondition receivingCondition) {
        return receivingDao.get(receivingCondition);
    }

    public Integer update(ReceivingCondition receivingCondition) {
        Integer result = receivingDao.update(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer updateUserInfo(ReceivingCondition receivingCondition) {
        Integer result = receivingDao.updateUserInfo(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer dayCountByReceiving(ReceivingCondition receivingCondition) {
        Integer result = receivingDao.dayCountByReceiving(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer totalCountByReceiving(ReceivingCondition receivingCondition) {
        Integer result = receivingDao.totalCountByReceiving(receivingCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

}
