package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.condition.TradeCondition;
import cn.com.lixihao.couponapi.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by lixihao on 2018/1/19.
 **/
@RestController
@RequestMapping(value = "/trade")
public class TradeController {

    private Logger log = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    TradeService tradeService;

    @RequestMapping(value = "/add")
    public String add(TradeCondition tradeCondition) {
        log.info("[TradeAdd]request->{}", tradeCondition);
        String trade_no = tradeCondition.getTrade_no();
        try {
            tradeService.add(tradeCondition);
            log.info("[TradeAddResult]trade_no->{},result->{}", new Object[]{trade_no, "ok"});
            return "ok";
        } catch (Exception e) {
            log.error("[TradeAddResult]trade_no->{},error->{}", new Object[]{trade_no, e.getMessage()});
            return "error";
        }
    }

    @RequestMapping(value = "/update")
    public String update(TradeCondition tradeCondition) {
        log.info("[TradeUpdate]request->{}", tradeCondition);
        String trade_no = tradeCondition.getTrade_no();
        try {
            tradeService.update(tradeCondition);
            log.info("[TradeUpdateResult]trade_no->{},result->{}", new Object[]{trade_no, "ok"});
            return "ok";
        } catch (Exception e) {
            log.error("[TradeUpdateResult]trade_no->{},error->{}", new Object[]{trade_no, e.getMessage()});
            return "error";
        }
    }


    @RequestMapping(value = "/cancel")
    public String cancel(TradeCondition tradeCondition) {
        log.info("[TradeCancel]expired_time->{}", tradeCondition.getCreate_time());
        String create_time = tradeCondition.getCreate_time();
        try {
            tradeService.updateExpiredTrade(tradeCondition);
            log.info("[TradeCancelResult]expired_time->{},result->{}", new Object[]{create_time, "ok"});
            return "ok";
        } catch (Exception e) {
            log.error("[TradeCancelResult]expired_time->{},error->{}", new Object[]{create_time, e.getMessage()});
            return "error";
        }
    }

    @RequestMapping(value = "/getlist")
    public String getList(TradeCondition tradeCondition) {
        return tradeService.getList(tradeCondition);
    }
}
