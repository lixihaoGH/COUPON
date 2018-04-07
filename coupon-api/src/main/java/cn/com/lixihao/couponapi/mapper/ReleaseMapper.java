package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.condition.ReleaseConditon;
import cn.com.lixihao.couponapi.entity.result.ReleaseResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseMapper {
    ReleaseResponse get(ReleaseConditon conditon);
    List<ReleaseResponse> getList(ReleaseConditon conditon);
    Integer insert(ReleaseConditon conditon);
    Integer delete(ReleaseConditon conditon);
    Integer update(ReleaseConditon conditon);
    Integer getCount(ReleaseConditon conditon);
}
