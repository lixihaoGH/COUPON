package cn.com.lixihao.couponapi.test.dao;

import cn.com.lixihao.couponapi.dao.ReceivingRestrictionDao;
import cn.com.lixihao.couponapi.entity.condition.ReceivingRestrictionCondition;
import cn.com.lixihao.couponapi.test.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class ReceivingRestrictionDaoTest extends BaseTest {

    @Autowired
    ReceivingRestrictionDao receivingRestrictionDao;

    @Test
    public void insert() {
        ReceivingRestrictionCondition condition = new ReceivingRestrictionCondition();
        condition.setBox_total_max(7000);
        condition.setPhone_total_max(52000);
        System.out.println(receivingRestrictionDao.insert(condition));
    }

    @Test
    public void select() {
        ReceivingRestrictionCondition condition = new ReceivingRestrictionCondition();
        condition.setRelease_id("123");
        ReceivingRestrictionCondition result = receivingRestrictionDao.get(condition);
        System.out.println(result);
    }
}
