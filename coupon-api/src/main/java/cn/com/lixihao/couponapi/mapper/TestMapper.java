package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.User;

/**
 * create by lixihao on 2018/3/13.
 **/
@SqlMapper
public interface TestMapper {

    User get(User user);
}
