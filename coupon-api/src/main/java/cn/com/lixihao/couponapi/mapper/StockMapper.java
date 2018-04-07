package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import cn.com.lixihao.couponapi.entity.result.StockResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMapper {
    StockResponse get(StockCondition stockCondition);

    List<StockResponse> getList(StockCondition stockCondition);

    Integer insert(StockCondition stockCondition);

    Integer delete(StockCondition stockCondition);

    Integer update(StockCondition stockCondition);

    Integer getCount(StockCondition stockCondition);
}
