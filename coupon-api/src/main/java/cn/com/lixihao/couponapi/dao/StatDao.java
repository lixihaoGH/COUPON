package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.StatCondition;
import cn.com.lixihao.couponapi.entity.condition.StatisticsCondition;
import cn.com.lixihao.couponapi.entity.result.StatisticsResponse;
import cn.com.lixihao.couponapi.mapper.StatMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("statDao")
public class StatDao extends BaseDao {

    @Autowired
    StatMapper statMapper;

    public StatCondition get(StatCondition statCondition) {
        return statMapper.get(statCondition);
    }

    public List<StatCondition> getList(StatCondition statCondition) {
        return statMapper.getList(statCondition);
    }

    public Integer insert(StatCondition statCondition) {
        return statMapper.insert(statCondition);
    }

    public Integer delete(StatCondition statCondition) {
        return statMapper.delete(statCondition);
    }

    public Integer updateRemaining(StatCondition statCondition) {
        return statMapper.updateRemaining(statCondition);
    }

    public Integer getCount(StatCondition statCondition) {
        Integer result = statMapper.getCount(statCondition);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    public Integer countRemaining(StatCondition statCondition) {
        Integer result = statMapper.countRemaining(statCondition);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    public List<StatisticsResponse> getStatisticsList(StatisticsCondition statisticsCondition){
        return statMapper.getStatisticsList(statisticsCondition);
    }


}
