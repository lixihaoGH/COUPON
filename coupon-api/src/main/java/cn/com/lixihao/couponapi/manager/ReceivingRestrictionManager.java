package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;
import cn.com.lixihao.couponapi.mapper.ReceivingRestrictionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivingRestrictionManager extends BaseManager {

    @Autowired
    private ReceivingRestrictionMapper receivingRestrictionMapper;

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
