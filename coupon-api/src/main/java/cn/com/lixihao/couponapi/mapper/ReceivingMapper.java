package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/
@Repository
public interface ReceivingMapper {

    Integer add(ReceivingCondition receivingCondition);

    List<ReceivingCondition> getEffectiveList(ReceivingCondition receivingCondition);

    List<ReceivingCondition> getExpiredList(ReceivingCondition receivingCondition);

    ReceivingCondition get(ReceivingCondition receivingCondition);

    Integer update(ReceivingCondition receivingCondition);

    Integer updateUserInfo(ReceivingCondition receivingCondition);

    Integer dayCountByReceiving(ReceivingCondition receivingCondition);

    Integer totalCountByReceiving(ReceivingCondition receivingCondition);

    List<ReceivingCondition> queryList(ReceivingCondition receivingCondition);


}
