package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;
import cn.com.lixihao.couponapi.mapper.ReceivingRestrictionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("receivingRestrictionDao")
public class ReceivingRestrictionDao extends BaseDao {

    @Autowired
    ReceivingRestrictionMapper receivingRestrictionMapper;

    public ReceivingRestrictionCondition get(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionMapper.get(conditon);
    }

/*    public List<ReceivingRestrictionCondition> getList(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionMapper.getList(conditon);
    }*/

    public Integer insert(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionMapper.insert(conditon);
    }

    public Integer update(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionMapper.update(conditon);
    }

    public Integer delete(ReceivingRestrictionCondition conditon) {
        return receivingRestrictionMapper.delete(conditon);
    }

}
