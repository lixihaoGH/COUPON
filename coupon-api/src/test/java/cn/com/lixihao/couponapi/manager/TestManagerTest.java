package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.base.BaseTest;
import cn.com.lixihao.couponapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * create by lixihao on 2018/3/13.
 **/

public class TestManagerTest extends BaseTest {

    @Autowired
    TestManager testManager;

    @Test
    public void get() {
        User user = new User();
        user.setName("lixihao");
        User result = testManager.get(user);
        System.out.println(result.getName() + " " + result.getPassword());
    }
}
