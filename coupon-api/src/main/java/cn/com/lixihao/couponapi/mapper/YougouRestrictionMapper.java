package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;

@SqlMapper
public interface YougouRestrictionMapper {
    YougouRestrictionCondition get(YougouRestrictionCondition condition);

    Integer insert(YougouRestrictionCondition condition);

    Integer delete(YougouRestrictionCondition condition);

    Integer update(YougouRestrictionCondition condition);

    YougouRestrictionCondition query(YougouRestrictionCondition condition);

}
