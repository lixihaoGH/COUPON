package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import cn.com.lixihao.couponapi.entity.result.EntranceResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntranceMapper {
    EntranceResponse get(EntranceCondition conditon);

    List<EntranceResponse> getList(EntranceCondition conditon);

    Integer insert(EntranceCondition conditon);

    Integer delete(EntranceCondition conditon);

    Integer update(EntranceCondition conditon);

    Integer getCount(EntranceCondition conditon);
}
