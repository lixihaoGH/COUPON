package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.mapper.TestMapper;
import cn.com.lixihao.couponapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by lixihao on 2018/3/13.
 **/
@Service
public class TestManager {

    @Autowired
    TestMapper testMapper;

    public User get(User user) {
        return testMapper.get(user);
    }
}
