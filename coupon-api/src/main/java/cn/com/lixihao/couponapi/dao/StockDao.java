package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.StatisticsCondition;
import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import cn.com.lixihao.couponapi.entity.result.StatisticsResponse;
import cn.com.lixihao.couponapi.entity.result.StockResponse;
import cn.com.lixihao.couponapi.mapper.StockMapper;
import com.alibaba.druid.support.spring.stat.annotation.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("stockDao")
public class StockDao extends BaseDao {

    @Autowired
    StockMapper stockMapper;

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
