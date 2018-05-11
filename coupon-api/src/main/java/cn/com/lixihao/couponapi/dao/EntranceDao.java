package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import cn.com.lixihao.couponapi.entity.result.EntranceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("entranceDao")
public class EntranceDao extends BaseDao{

    @Autowired
    EntranceDao entranceDao;

    public EntranceResponse get(EntranceCondition conditon) {
        return entranceDao.get(conditon);
    }

    public List<EntranceResponse> getList(EntranceCondition conditon) {
        return entranceDao.getList(conditon);
    }

    public Integer insert(EntranceCondition conditon) {
        return entranceDao.insert(conditon);
    }

    public Integer update(EntranceCondition conditon) {
        return entranceDao.update(conditon);
    }

    public Integer delete(EntranceCondition conditon) {
        return entranceDao.delete(conditon);
    }

    public Integer getCount(EntranceCondition conditon) {
        Integer result = entranceDao.getCount(conditon);
        if (result == null) {
            result = 0;
        }
        return result;
    }
}
