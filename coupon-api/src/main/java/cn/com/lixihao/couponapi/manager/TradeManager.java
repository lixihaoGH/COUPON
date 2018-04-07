package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.TradeCondition;
import cn.com.lixihao.couponapi.mapper.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/
@Service
public class TradeManager extends BaseManager {

    @Autowired
    TradeMapper tradeMapper;

    public Integer add(TradeCondition tradeCondition) {
        Integer result = tradeMapper.add(tradeCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer update(TradeCondition tradeCondition) {
        Integer result = tradeMapper.update(tradeCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public TradeCondition get(TradeCondition tradeCondition) {
        return tradeMapper.get(tradeCondition);
    }

    public List<TradeCondition> getList(TradeCondition tradeCondition) {
        List<TradeCondition> result = tradeMapper.getList(tradeCondition);
        if (result == null) {
            return new ArrayList<TradeCondition>();
        }
        return result;
    }

    public Integer getCount(TradeCondition tradeCondition) {
        Integer result = tradeMapper.getCount(tradeCondition);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer getTotalPayment(TradeCondition tradeCondition) {
        Integer totalpayment = tradeMapper.getTotalPayment(tradeCondition);
        if (totalpayment == null) {
            return 0;
        }
        return totalpayment;
    }

}
