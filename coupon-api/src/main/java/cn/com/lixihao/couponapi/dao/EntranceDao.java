package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import cn.com.lixihao.couponapi.entity.result.EntranceResponse;
import cn.com.lixihao.couponapi.mapper.EntranceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("entranceDao")
public class EntranceDao extends BaseDao{

    @Autowired
    EntranceMapper entranceMapper;

    public EntranceResponse get(EntranceCondition conditon) {
        return entranceMapper.get(conditon);
    }

    public List<EntranceResponse> getList(EntranceCondition conditon) {
        return entranceMapper.getList(conditon);
    }

    public Integer insert(EntranceCondition conditon) {
        return entranceMapper.insert(conditon);
    }

    public Integer update(EntranceCondition conditon) {
        return entranceMapper.update(conditon);
    }

    public Integer delete(EntranceCondition conditon) {
        return entranceMapper.delete(conditon);
    }

    public Integer getCount(EntranceCondition conditon) {
        Integer result = entranceMapper.getCount(conditon);
        if (result == null) {
            result = 0;
        }
        return result;
    }
}
