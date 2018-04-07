package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.entity.condition.*;
import cn.com.lixihao.couponapi.entity.result.*;
import cn.com.lixihao.couponapi.manager.ReleaseManager;
import cn.com.lixihao.couponapi.manager.StatManager;
import cn.com.lixihao.couponapi.manager.StockManager;
import cn.com.lixihao.couponapi.manager.TradeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private StatManager statManager;
    @Autowired
    private TradeManager tradeManager;
    @Autowired
    private ReleaseManager releaseManager;
    @Autowired
    private StockManager stockManager;

    public PageResponse getStats(StatisticsCondition condition) {
        List<StatisticsResponse> statsList = statManager.getStatisticsList(condition);
        PageResponse pageResponse = new PageResponse(UnifiedResponse.SUCCESS, "ok");
        if (statsList.size() < 1) {
            return new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        }
        StatCondition statCondition = new StatCondition();
        statCondition.release_id = condition.release_id;
        statCondition.coupon_stock_id = condition.coupon_stock_id;
        Integer totalCount = statManager.getCount(statCondition);
        for (StatisticsResponse statisticsResponse : statsList) {
            //获取红包单包相关信息
            StockCondition stockCondition = new StockCondition();
            stockCondition.coupon_stock_id = statisticsResponse.coupon_stock_id;
            StockResponse stockResponse = stockManager.get(stockCondition);
            statisticsResponse.coupon_stock_name = stockResponse.coupon_stock_name;
            //获取策略相关信息
            ReleaseConditon releaseConditon = new ReleaseConditon();
            releaseConditon.release_id = statisticsResponse.release_id;
            ReleaseResponse releaseResponse = releaseManager.get(releaseConditon);
            statisticsResponse.release_count = releaseResponse.release_count;
            statisticsResponse.receiving_count = statisticsResponse.release_count - statisticsResponse.remaining_count;
            //获取使用订单记录相关信息
            TradeCondition tradeCondition = new TradeCondition();
            tradeCondition.setRelease_id(statisticsResponse.release_id);
            tradeCondition.setCoupon_stock_id(statisticsResponse.coupon_stock_id);
            Integer count = tradeManager.getCount(tradeCondition);
            Integer totalPayment = tradeManager.getTotalPayment(tradeCondition);
            statisticsResponse.used_count = count;
            statisticsResponse.total_payment_amount = totalPayment / 100 + "";
            String usage_rate = String.format("%.2f", statisticsResponse.used_count * 1.0 / statisticsResponse.release_count);
            statisticsResponse.usage_rate = Double.parseDouble(usage_rate);
        }
        pageResponse.setRows(statsList);
        pageResponse.setTotal(totalCount);
        return pageResponse;
    }
}
