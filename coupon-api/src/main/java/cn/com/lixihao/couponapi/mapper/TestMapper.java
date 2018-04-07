package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * create by lixihao on 2018/3/13.
 **/
@Repository
public interface TestMapper {

    User get(User user);
}
