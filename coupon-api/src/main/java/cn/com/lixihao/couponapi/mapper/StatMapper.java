package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.StatCondition;
import cn.com.lixihao.couponapi.entity.condition.StatisticsCondition;
import cn.com.lixihao.couponapi.entity.result.StatisticsResponse;

import java.util.List;

@SqlMapper
public interface StatMapper {
    StatCondition get(StatCondition statCondition);

    List<StatCondition> getList(StatCondition statCondition);

    List<StatisticsResponse> getStatisticsList(StatisticsCondition statisticsCondition);

    Integer insert(StatCondition statCondition);

    Integer delete(StatCondition statCondition);

    Integer updateRemaining(StatCondition statCondition);

    Integer getCount(StatCondition statCondition);

    Integer countRemaining(StatCondition statCondition);
}
