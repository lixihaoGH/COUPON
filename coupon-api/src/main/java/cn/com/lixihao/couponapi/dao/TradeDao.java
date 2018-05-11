package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.TradeCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/
@Repository("tradeDao")
public class TradeDao extends BaseDao {

    @Autowired
    TradeDao tradeDao;

    public Integer add(TradeCondition tradeCondition) {
        Integer result = tradeDao.add(tradeCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer update(TradeCondition tradeCondition) {
        Integer result = tradeDao.update(tradeCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public TradeCondition get(TradeCondition tradeCondition) {
        return tradeDao.get(tradeCondition);
    }

    public List<TradeCondition> getList(TradeCondition tradeCondition) {
        List<TradeCondition> result = tradeDao.getList(tradeCondition);
        if (result == null) {
            return new ArrayList<TradeCondition>();
        }
        return result;
    }

    public Integer getCount(TradeCondition tradeCondition) {
        Integer result = tradeDao.getCount(tradeCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer getTotalPayment(TradeCondition tradeCondition) {
        Integer totalpayment = tradeDao.getTotalPayment(tradeCondition);
        if (totalpayment == null) {
            return 0;
        }
        return totalpayment;
    }

}
