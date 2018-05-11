package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import cn.com.lixihao.couponapi.entity.result.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("stockDao")
public class StockDao extends BaseDao {

    @Autowired
    StockDao stockDao;

    public StockResponse get(StockCondition stockcondition) {
        return stockDao.get(stockcondition);
    }

    public List<StockResponse> getList(StockCondition stockCondition) {
        return stockDao.getList(stockCondition);
    }

    public Integer insert(StockCondition stockCondition) {
        return stockDao.insert(stockCondition);
    }

    public Integer delete(StockCondition stockCondition) {
        return stockDao.delete(stockCondition);
    }

    public Integer update(StockCondition stockCondition) {
        return stockDao.update(stockCondition);
    }

    public Integer getCount(StockCondition stockCondition) {
        Integer result = stockDao.getCount(stockCondition);
        if (result == null) {
            result = 0;
        }
        return result;
    }
}
