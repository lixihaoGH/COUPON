package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import org.springframework.stereotype.Repository;

@Repository
public interface YougouRestrictionMapper {
    YougouRestrictionCondition get(YougouRestrictionCondition condition);

    Integer insert(YougouRestrictionCondition condition);

    Integer delete(YougouRestrictionCondition condition);

    Integer update(YougouRestrictionCondition condition);

    YougouRestrictionCondition query(YougouRestrictionCondition condition);

}
