package cn.com.lixihao.couponapi.test.dao;

import cn.com.lixihao.couponapi.dao.SmsCaptchaDao;
import cn.com.lixihao.couponapi.entity.condition.SmsCaptchaCondition;
import cn.com.lixihao.couponapi.test.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * create by lixihao on 2018/2/27.
 **/

public class SmsCaptchaDaoTest extends BaseTest {

    @Autowired
    SmsCaptchaDao smsCaptchaDao;

    @Test
    public void add() {
        SmsCaptchaCondition smsCaptchaCondition = new SmsCaptchaCondition();
        smsCaptchaCondition.setPhone("17862808091");
        smsCaptchaCondition.setSms_captcha("151262");
        smsCaptchaCondition.setExpiry_time(System.currentTimeMillis());
        System.out.println(smsCaptchaDao.add(smsCaptchaCondition));
    }

    @Test
    public void get() {
        SmsCaptchaCondition smsCaptchaCondition = new SmsCaptchaCondition();
        smsCaptchaCondition.setPhone("17862808091");
        System.out.println(smsCaptchaDao.get(smsCaptchaCondition).toString());
    }

    @Test
    public void update() {
        SmsCaptchaCondition smsCaptchaCondition = new SmsCaptchaCondition();
        smsCaptchaCondition.setPhone("17862808091");
        smsCaptchaCondition.setExpiry_time(System.currentTimeMillis());
        smsCaptchaCondition.setSms_captcha("1521211");
        System.out.println(smsCaptchaDao.update(smsCaptchaCondition).toString());

    }
}
