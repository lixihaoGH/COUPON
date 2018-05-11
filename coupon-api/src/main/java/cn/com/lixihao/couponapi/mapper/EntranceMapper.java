package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import cn.com.lixihao.couponapi.entity.result.EntranceResponse;

import java.util.List;

@SqlMapper
public interface EntranceMapper {
    EntranceResponse get(EntranceCondition conditon);

    List<EntranceResponse> getList(EntranceCondition conditon);

    Integer insert(EntranceCondition conditon);

    Integer delete(EntranceCondition conditon);

    Integer update(EntranceCondition conditon);

    Integer getCount(EntranceCondition conditon);
}
