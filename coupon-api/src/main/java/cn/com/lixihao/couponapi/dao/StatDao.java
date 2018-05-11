package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.StatCondition;
import cn.com.lixihao.couponapi.entity.condition.StatisticsCondition;
import cn.com.lixihao.couponapi.entity.result.StatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("statDao")
public class StatDao extends BaseDao {

    @Autowired
    StatDao statDao;

    public StatCondition get(StatCondition statCondition) {
        return statDao.get(statCondition);
    }

    public List<StatCondition> getList(StatCondition statCondition) {
        return statDao.getList(statCondition);
    }

    public Integer insert(StatCondition statCondition) {
        return statDao.insert(statCondition);
    }

    public Integer delete(StatCondition statCondition) {
        return statDao.delete(statCondition);
    }

    public Integer updateRemaining(StatCondition statCondition) {
        return statDao.updateRemaining(statCondition);
    }

    public Integer getCount(StatCondition statCondition) {
        Integer result = statDao.getCount(statCondition);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    public Integer countRemaining(StatCondition statCondition) {
        Integer result = statDao.countRemaining(statCondition);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    public List<StatisticsResponse> getStatisticsList(StatisticsCondition statisticsCondition) {
        return statDao.getStatisticsList(statisticsCondition);
    }

}
