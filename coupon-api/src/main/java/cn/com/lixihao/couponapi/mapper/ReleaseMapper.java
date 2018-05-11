package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.ReleaseConditon;
import cn.com.lixihao.couponapi.entity.result.ReleaseResponse;

import java.util.List;

@SqlMapper
public interface ReleaseMapper {
    ReleaseResponse get(ReleaseConditon conditon);
    List<ReleaseResponse> getList(ReleaseConditon conditon);
    Integer insert(ReleaseConditon conditon);
    Integer delete(ReleaseConditon conditon);
    Integer update(ReleaseConditon conditon);
    Integer getCount(ReleaseConditon conditon);
}
