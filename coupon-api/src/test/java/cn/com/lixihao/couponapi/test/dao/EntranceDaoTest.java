package cn.com.lixihao.couponapi.test.dao;

import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.dao.EntranceDao;
import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import cn.com.lixihao.couponapi.test.base.BaseTest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.testng.annotations.Test;

public class EntranceDaoTest extends BaseTest {

    @Autowired
    private EntranceDao dao;

    @Test
    public void insert() {
        EntranceCondition condition = new EntranceCondition();
        condition.setCreate_time(DateTime.now().toString(SysConstants.DATE_FORMAT));
        condition.setUpdate_time(DateTime.now().toString(SysConstants.DATE_FORMAT));
        condition.setEntrance_name("lipeng");
        condition.setRelease_id_list("7200151660736215002122256547321");
        try {
            System.out.println(dao.insert(condition));
        } catch (DuplicateKeyException e) {
            System.out.println(e.getCause());
        }
    }

    @Test
    public void getList() {
        EntranceCondition condition = new EntranceCondition();
        System.out.println(dao.getList(condition));
    }

    @Test
    public void select() {
        EntranceCondition condition = new EntranceCondition();
        condition.setId(1);
        System.out.println(dao.get(condition));
    }


    @Test
    public void delete() {
        EntranceCondition condition = new EntranceCondition();
        condition.setId(2);
        System.out.println(dao.delete(condition));
    }

    @Test
    public void update() {
        EntranceCondition condition = new EntranceCondition();
        condition.setId(1);
        condition.setEntrance_name("lipeng");
        System.out.println(dao.update(condition));
    }
}
