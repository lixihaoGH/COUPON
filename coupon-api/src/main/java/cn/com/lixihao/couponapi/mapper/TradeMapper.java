package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.TradeCondition;

import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/
@SqlMapper
public interface TradeMapper {

    Integer add(TradeCondition tradeCondition);

    Integer update(TradeCondition tradeCondition);

    TradeCondition get(TradeCondition tradeCondition);

    List<TradeCondition> getList(TradeCondition tradeCondition);

    Integer getCount(TradeCondition tradeCondition);

    Integer getTotalPayment(TradeCondition tradeCondition);
}
