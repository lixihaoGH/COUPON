package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("yougouRestrictionDao")
public class YougouRestrictionDao extends BaseDao {

    @Autowired
    YougouRestrictionDao yougouRestrictionDao;

    public YougouRestrictionCondition get(YougouRestrictionCondition condition) {
        return yougouRestrictionDao.get(condition);
    }

    public Integer insert(YougouRestrictionCondition condition) {
        return yougouRestrictionDao.insert(condition);
    }

    public Integer delete(YougouRestrictionCondition condition) {
        return yougouRestrictionDao.delete(condition);
    }

    public Integer update(YougouRestrictionCondition condition) {
        return yougouRestrictionDao.update(condition);
    }

    public YougouRestrictionCondition query(YougouRestrictionCondition condition) {
        return yougouRestrictionDao.query(condition);
    }

}
