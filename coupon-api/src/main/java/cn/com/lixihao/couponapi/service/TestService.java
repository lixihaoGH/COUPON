package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.dao.TestDao;
import cn.com.lixihao.couponapi.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by lixihao on 2018/3/13.
 **/
@Service
public class TestService {

    @Autowired
    TestDao testDao;

    public String get(User user) {
        return JSONObject.toJSONString(testDao.get(user));
    }
}
