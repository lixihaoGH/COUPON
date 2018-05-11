package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("receivingRestrictionDao")
public class ReceivingRestrictionDao extends BaseDao {

    @Autowired
    ReceivingRestrictionDao receivingRestrictionDao;

    public ReceivingRestrictionCondition get(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionDao.get(conditon);
    }

/*    public List<ReceivingRestrictionCondition> getList(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionMapper.getList(conditon);
    }*/

    public Integer insert(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionDao.insert(conditon);
    }

    public Integer update(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionDao.update(conditon);
    }

    public Integer delete(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionDao.delete(conditon);
    }

}
