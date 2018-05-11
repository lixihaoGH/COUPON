package cn.com.lixihao.couponapi.test.dao;

import cn.com.lixihao.couponapi.dao.StatDao;
import cn.com.lixihao.couponapi.entity.condition.StatisticsCondition;
import cn.com.lixihao.couponapi.test.base.BaseTest;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class StatDaoTest extends BaseTest {

    @Autowired
    private StatDao dao;

    @Test
    public void getStatsList() {
        StatisticsCondition condition = new StatisticsCondition();
        condition.coupon_stock_name = "e";
        //condition.release_id = "";
        System.out.println(JSON.toJSONString(dao.getStatisticsList(condition)));
    }
}
