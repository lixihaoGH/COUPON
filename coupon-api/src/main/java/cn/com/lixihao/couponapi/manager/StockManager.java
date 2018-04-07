package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import cn.com.lixihao.couponapi.entity.result.StockResponse;
import cn.com.lixihao.couponapi.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockManager extends BaseManager {

    @Autowired
    private StockMapper stockMapper;

    public StockResponse get(StockCondition stockcondition) {
        return stockMapper.get(stockcondition);
    }

    public List<StockResponse> getList(StockCondition stockCondition) {
        return stockMapper.getList(stockCondition);
    }

    public Integer insert(StockCondition stockCondition) {
        return stockMapper.insert(stockCondition);
    }

    public Integer delete(StockCondition stockCondition) {
        return stockMapper.delete(stockCondition);
    }

    public Integer update(StockCondition stockCondition) {
        return stockMapper.update(stockCondition);
    }

    public Integer getCount(StockCondition stockCondition) {
        Integer result = stockMapper.getCount(stockCondition);
        if (result == null) {
            result = 0;
        }
        return result;
    }
}
