package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;

import java.util.List;

@SqlMapper
public interface ReceivingRestrictionMapper {
    ReceivingRestrictionCondition get(ReceivingRestrictionCondition condition);

    List<ReceivingRestrictionCondition> getList(ReceivingRestrictionCondition condition);

    Integer insert(ReceivingRestrictionCondition condition);

    Integer delete(ReceivingRestrictionCondition condition);

    Integer update(ReceivingRestrictionCondition condition);
}
