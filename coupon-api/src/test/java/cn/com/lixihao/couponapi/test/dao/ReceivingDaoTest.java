package cn.com.lixihao.couponapi.test.dao;

import cn.com.lixihao.couponapi.dao.ReceivingDao;
import cn.com.lixihao.couponapi.dao.StatDao;
import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;
import cn.com.lixihao.couponapi.entity.condition.StatCondition;
import cn.com.lixihao.couponapi.test.base.BaseTest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * create by lixihao on 2017/12/25.
 **/

public class ReceivingDaoTest extends BaseTest {

    @Autowired
    ReceivingDao receivingDao;
    @Autowired
    StatDao statDao;

    @Test
    public void add() {
        ReceivingCondition receivingCondition = new ReceivingCondition();
        receivingCondition.setCoupon_stock_id("dsadad");
        receivingCondition.setCoupon_stock_name("kaquan");
        receivingCondition.setPhone_number("+86 1515212121");
        receivingCondition.setReceiving_time(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        receivingCondition.setCoupon_status(2);
        receivingCondition.setPreferential_type(3);
        receivingCondition.setEffective_time(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        receivingCondition.setExpired_time(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        receivingCondition.setRelease_id("sdadad");
        receivingCondition.setUser_id("123456");
        receivingCondition.setOpenid("sdadasd");
        receivingCondition.setDevice_type(0);
        for (int i = 0; i <= 5; i++) {
            receivingCondition.setCoupon_id("sdadadasdas" + i);
            receivingDao.add(receivingCondition);
        }
    }

    @Test
    public void get() {
        ReceivingCondition receivingCondition = new ReceivingCondition();
        receivingCondition.setCoupon_id("sdadadasdas0");
        ReceivingCondition result = receivingDao.get(receivingCondition);
        System.out.println(result.toString());
    }


    @Test
    public void update() {
        ReceivingCondition receivingCondition = new ReceivingCondition();
        receivingCondition.setCoupon_id("sdadadasdas0");
        receivingCondition.setCoupon_status(2);
        receivingDao.update(receivingCondition);
    }


    @Test
    public void dayCountByReleaseAndDevice() {
        ReceivingCondition receivingCondition = new ReceivingCondition();
        receivingCondition.setRelease_id("sdadad");
        receivingCondition.setCoupon_stock_id("dsadad");
        receivingCondition.setReceiving_time(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        receivingCondition.setUser_id("123456");
        receivingCondition.setDevice_type(0);
    }

    @Test
    public void getRemaining() {
        StatCondition statCondition = new StatCondition();
        statCondition.setRelease_id("106136");
        Integer num = statDao.countRemaining(statCondition);
        System.out.println(num);
    }
}
