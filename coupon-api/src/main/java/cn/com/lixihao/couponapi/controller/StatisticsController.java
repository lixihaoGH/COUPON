package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.condition.StatisticsCondition;
import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.service.StatisticsService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@RequestMapping(value = "/statistics")
@Controller
public class StatisticsController {

    private Logger DATA = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public PageResponse getList(StatisticsCondition condition) {
        DATA.info("[statistics]getList: coupon_stock_name->{},release_id->{}", condition.getCoupon_stock_name(), condition.getRelease_id());
        PageResponse pageResponse;
        try {
            pageResponse = statisticsService.getStats(condition);
        } catch (Exception e) {
            DATA.error("[statistics]getList: exception->{}", e.getMessage());
            pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        }
        DATA.info("[statistics]getList: response->{}", JSON.toJSONString(pageResponse));
        return pageResponse;
    }
}
