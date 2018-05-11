package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;

import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/
@SqlMapper
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
