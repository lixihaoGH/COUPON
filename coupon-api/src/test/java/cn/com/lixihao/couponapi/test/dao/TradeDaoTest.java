package cn.com.lixihao.couponapi.test.dao;

import cn.com.lixihao.couponapi.dao.TradeDao;
import cn.com.lixihao.couponapi.entity.condition.TradeCondition;
import cn.com.lixihao.couponapi.test.base.BaseTest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/

public class TradeDaoTest extends BaseTest {

    @Autowired
    TradeDao tradeDao;

    @Test
    public void addTrade() {
        TradeCondition tradeCondition = new TradeCondition();
        tradeCondition.setCoupon_id("15121121221212sdad");
        tradeCondition.setCreate_time(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        tradeCondition.setDeduction_amount(100);
        tradeCondition.setPayment_amount(20);
        tradeCondition.setTrade_status(2);
        tradeCondition.setTotal_amount(30);
        tradeCondition.setTrade_no("sdasdasdasdas");
        tradeCondition.setUser_id("cascaadsda");
        tradeCondition.setRelease_id("nasdhbcasvuyacasjkh");
        tradeCondition.setCoupon_stock_id("dadasdasdasd");
        tradeDao.add(tradeCondition);
    }

    @Test
    public void updateTrade() {
        TradeCondition tradeCondition = new TradeCondition();
        tradeCondition.setTrade_status(2);
        tradeCondition.setTrade_no("sdasdasdasdas");
        tradeDao.update(tradeCondition);
    }

    @Test
    public void getTrade() {
        TradeCondition tradeCondition = new TradeCondition();
        tradeCondition.setTrade_no("sdasdasdasdas");
        TradeCondition result = tradeDao.get(tradeCondition);
        System.out.println(result.toString());
    }

    @Test
    public void getTradeList() {
        TradeCondition tradeCondition = new TradeCondition();
        tradeCondition.setRelease_id("nasdhbcasvuyacasjkh");
        List<TradeCondition> result = tradeDao.getList(tradeCondition);
        System.out.println(JSONObject.toJSONString(result));
    }


    @Test
    public void getCount() {
        TradeCondition tradeCondition = new TradeCondition();
        tradeCondition.setCoupon_stock_id("dadasdasdasd");
        tradeCondition.setRelease_id("nasdhbcasvuyacasjkh");
        Integer result = tradeDao.getCount(tradeCondition);
        System.out.println(result);
    }

    @Test
    public void getList() {
        TradeCondition tradeCondition = new TradeCondition();
        tradeCondition.setRelease_id("nasdhbcasvuyacasjkh");
        tradeCondition.setCoupon_stock_id("dadasdasdasd");
        List<TradeCondition> list = tradeDao.getList(tradeCondition);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testSum() {
        TradeCondition condition = new TradeCondition();
        condition.setCoupon_stock_id("53000101517540638538201802021103");
        condition.setRelease_id("72001517556799276020180202033319");
        Integer result = tradeDao.getTotalPayment(condition);
        System.out.println(result);

    }

    @Test
    public void testGetCount() {
        TradeCondition condition = new TradeCondition();
        condition.setCoupon_stock_id("53000101517540638538201802021103");
        condition.setRelease_id("72001517556799276020180202033319");
        Integer result = tradeDao.getCount(condition);
        System.out.println(result);

    }
}
