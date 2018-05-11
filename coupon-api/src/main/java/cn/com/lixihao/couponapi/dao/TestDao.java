package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * create by lixihao on 2018/3/13.
 **/
@Repository("testDao")
public class TestDao extends BaseDao {

    @Autowired
    TestDao testDao;

    public User get(User user) {
        return testDao.get(user);
    }
}
