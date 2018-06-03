package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import cn.com.lixihao.couponapi.mapper.YougouRestrictionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("yougouRestrictionDao")
public class YougouRestrictionDao extends BaseDao {

    @Autowired
    YougouRestrictionMapper yougouRestrictionMapper;

    public YougouRestrictionCondition get(YougouRestrictionCondition condition) {
        return yougouRestrictionMapper.get(condition);
    }

    public Integer insert(YougouRestrictionCondition condition) {
        return yougouRestrictionMapper.insert(condition);
    }

    public Integer delete(YougouRestrictionCondition condition) {
        return yougouRestrictionMapper.delete(condition);
    }

    public Integer update(YougouRestrictionCondition condition) {
        return yougouRestrictionMapper.update(condition);
    }

    public YougouRestrictionCondition query(YougouRestrictionCondition condition) {
        return yougouRestrictionMapper.query(condition);
    }

}
