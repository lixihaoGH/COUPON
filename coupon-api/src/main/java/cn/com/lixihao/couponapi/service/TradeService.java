package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;
import cn.com.lixihao.couponapi.entity.condition.TradeCondition;
import cn.com.lixihao.couponapi.manager.ReceivingManager;
import cn.com.lixihao.couponapi.manager.TradeManager;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by lixihao on 2018/1/19.
 **/
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TradeService {

    private Logger log = LoggerFactory.getLogger(TradeService.class);

    @Autowired
    ReceivingManager receivingManager;
    @Autowired
    TradeManager tradeManager;

    private final Integer TRADE_STATUS_LOCK = 1;
    private final Integer TRADE_STATUS_USED = 2;
    private final Integer TRADE_STATUS_PAID = 2;
    private final Integer TRADE_STATUS_CANCEL = 1;


    public void add(TradeCondition tradeCondition) {
        String create_time = DateTime.now().toString(SysConstants.DATE_FORMAT);
        tradeCondition.setCreate_time(create_time);
        Integer addResult = tradeManager.add(tradeCondition);
        if (addResult != 1) {
            throw new RuntimeException("添加交易失败");
        }
        ReceivingCondition receivingCondition = new ReceivingCondition();
        receivingCondition.setCoupon_id(tradeCondition.getCoupon_id());
        receivingCondition.setCoupon_status(this.TRADE_STATUS_LOCK);
        Integer updateResult = receivingManager.update(receivingCondition);
        if (updateResult == 0) {
            throw new RuntimeException("变更卡券状态失败");
        }
    }

    public void update(TradeCondition tradeCondition) {
        Integer updateResult = tradeManager.update(tradeCondition);
        if (updateResult == 0) {
            throw new RuntimeException("更新交易失败");
        }
        ReceivingCondition receivingCondition = new ReceivingCondition();
        receivingCondition.setCoupon_id(tradeCondition.getCoupon_id());
        if (tradeCondition.getTrade_status().equals(this.TRADE_STATUS_CANCEL)) {
            receivingCondition.setCoupon_status(SysConstants.COUPON_STATUS_INIT);
            Integer updateCouponResult = receivingManager.update(receivingCondition);
            if (updateCouponResult == 0) {
                throw new RuntimeException("变更卡券状态失败");
            }
        } else if (tradeCondition.getTrade_status().equals(this.TRADE_STATUS_PAID)) {
            receivingCondition.setCoupon_status(this.TRADE_STATUS_USED);
            Integer updateCouponResult = receivingManager.update(receivingCondition);
            if (updateCouponResult == 0) {
                throw new RuntimeException("变更卡券状态失败");
            }
        }
    }

    public String getList(TradeCondition tradeCondition) {
        List<TradeCondition> result = tradeManager.getList(tradeCondition);
        if (result.isEmpty()) {
            return "error";
        }
        return JSONObject.toJSONString(result);
    }

    public void updateExpiredTrade(TradeCondition tradeCondition) {
        List<TradeCondition> tradeConditionList = tradeManager.getList(tradeCondition);
        log.info("[UpdateExpiredTrade]expired_trade->{}", tradeConditionList);
        if (tradeConditionList.isEmpty()) {
            return;
        }
        for (TradeCondition trade : tradeConditionList) {
            trade.setTrade_status(this.TRADE_STATUS_CANCEL);
            this.update(trade);
        }
    }

}
