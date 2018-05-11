package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.entity.condition.ReceivingCondition;
import cn.com.lixihao.couponapi.entity.condition.StatCondition;
import cn.com.lixihao.couponapi.entity.condition.StockCondition;
import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import cn.com.lixihao.couponapi.entity.result.StockResponse;
import cn.com.lixihao.couponapi.helper.GenerateIdentifier;
import cn.com.lixihao.couponapi.dao.ReceivingDao;
import cn.com.lixihao.couponapi.dao.StatDao;
import cn.com.lixihao.couponapi.dao.StockDao;
import cn.com.lixihao.couponapi.dao.YougouRestrictionDao;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by lixihao on 2017/12/28.
 **/
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ReceivingService {

    @Autowired
    ShardedJedisPool shardedJedisPool;
    @Autowired
    ReleaseService releaseService;
    @Autowired
    WeixinService weixinService;
    @Autowired
    ReceivingDao receivingDao;
    @Autowired
    StockDao stockDao;
    @Autowired
    YougouRestrictionDao yougouRestrictionDao;
    @Autowired
    StatDao statDao;

    public String get(ReceivingCondition receivingCondition) {
        ReceivingCondition result = receivingDao.get(receivingCondition);
        if (result == null) {
            return "error";
        }
        return JSONObject.toJSONString(result);
    }


    public String getList(ReceivingCondition receivingCondition, Integer page_index, Integer page_size) {
        List<ReceivingCondition> listResult;
        receivingCondition.setPage_index(page_index);
        receivingCondition.setPage_size(page_size);
        Integer coupon_status = receivingCondition.getCoupon_status();
        if (coupon_status.equals(SysConstants.COUPON_STATUS_EXPIRED)) {
            receivingCondition.setCoupon_status(SysConstants.COUPON_STATUS_INIT);
            listResult = receivingDao.getExpiredList(receivingCondition);
        } else {
            listResult = receivingDao.getEffectiveList(receivingCondition);
        }
        if (listResult == null) {
            return "error";
        }
        //System.out.println(listResult.toString());
        return JSONObject.toJSONString(listResult);
    }


    public String getYougouStock(String stock_id_list) {
        if (StringUtils.isEmpty(stock_id_list)) {
            return "error";
        }
        String key = "coupon_receiving_yougoustock_" + stock_id_list;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                return shardedJedis.get(key);
            }
            String[] StockIdList = stock_id_list.split(",");
            JSONArray resultArray = new JSONArray();
            StockCondition stockCondition = new StockCondition();
            YougouRestrictionCondition yougouRestrictionCondition = new YougouRestrictionCondition();
            for (String coupon_stock_id : StockIdList) {
                JSONObject jsonObject = new JSONObject();
                stockCondition.setCoupon_stock_id(coupon_stock_id);
                StockResponse stock = stockDao.get(stockCondition);
                if (stock == null) {
                    return "error";
                }
                jsonObject.put("coupon_stock_id", stock.getCoupon_stock_id());
                jsonObject.put("coupon_stock_name", stock.getCoupon_stock_name());
                jsonObject.put("preferential_type", stock.getPreferential_type() + "");
                jsonObject.put("preferential_amount", stock.getPreferential_amount() + "");
                jsonObject.put("discount", stock.getDiscount() + "");
                yougouRestrictionCondition.setCoupon_stock_id(coupon_stock_id);
                YougouRestrictionCondition yougouRestriction = yougouRestrictionDao.get(yougouRestrictionCondition);
                if (yougouRestriction == null) {
                    return "error";
                }
                jsonObject.put("restriction_description", yougouRestriction.getRestriction_description());
                jsonObject.put("goods_range", yougouRestriction.getGoods_range() + "");
                jsonObject.put("reach_amount", yougouRestriction.getReach_amount() + "");
                jsonObject.put("effective_duration", yougouRestriction.getEffective_duration() + "");
                Integer effective_duration = yougouRestriction.getEffective_duration();
                if (effective_duration != null) {
                    jsonObject.put("expired_time", DateTime.now().millisOfDay().withMaximumValue().plusDays(effective_duration).toString(SysConstants.DATE_FORMAT));
                } else {
                    jsonObject.put("effective_time", yougouRestriction.getEffective_time());
                    jsonObject.put("expired_time", yougouRestriction.getExpired_time());
                }
                resultArray.add(jsonObject);
            }
            String result = JSONObject.toJSONString(resultArray);
            shardedJedis.set(key, result);
            shardedJedis.expire(key, 1800);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }


    public String getYougouCoupon(YougouRestrictionCondition yougouRestrictionCondition, String user_id, String phone_number) {
        ReceivingCondition receivingCondition = new ReceivingCondition();
        receivingCondition.setUser_id(user_id);
        receivingCondition.setPhone_number(phone_number);
        receivingCondition.setCoupon_status(0);
        List<ReceivingCondition> receivingConditionList = receivingDao.queryList(receivingCondition);
        if (receivingConditionList.isEmpty()) {
            return "error";
        }
        List<ReceivingCondition> result = new ArrayList<ReceivingCondition>();
        for (ReceivingCondition receiving : receivingConditionList) {
            yougouRestrictionCondition.setCoupon_stock_id(receiving.getCoupon_stock_id());
            YougouRestrictionCondition yougouRestriction = yougouRestrictionDao.query(yougouRestrictionCondition);
            if (yougouRestriction != null) {
                result.add(receiving);
            }
        }
        return JSONObject.toJSONString(result);
    }

    public void add(ReceivingCondition receivingCondition, String stock_id_list) {
        try {
            stock_id_list = URLDecoder.decode(stock_id_list,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String yougouStockJsonStr = this.getYougouStock(stock_id_list);
        if (StringUtils.isEmpty(yougouStockJsonStr) || yougouStockJsonStr.equals("error")) {
            throw new RuntimeException("获取请求红包批次失败");
        }
        receivingCondition.setUser_id(StringUtils.isEmpty(receivingCondition.getUser_id()) ? null : receivingCondition.getUser_id());
        JSONArray yougouStockArray = JSON.parseArray(yougouStockJsonStr);
        receivingCondition.setCoupon_status(0);
        for (Object object : yougouStockArray) {
            JSONObject yougouStock = (JSONObject) object;
            String coupon_stock_id = yougouStock.getString("coupon_stock_id");
            receivingCondition.setCoupon_id(GenerateIdentifier.generateCouponId(receivingCondition.getPhone_number()));
            receivingCondition.setCoupon_stock_id(coupon_stock_id);
            receivingCondition.setCoupon_stock_name(yougouStock.getString("coupon_stock_name"));
            receivingCondition.setPreferential_type(yougouStock.getInteger("preferential_type"));
            receivingCondition.setPreferential_amount(yougouStock.getInteger("preferential_amount"));
            receivingCondition.setDiscount(yougouStock.getInteger("discount"));
            receivingCondition.setRestriction_description(yougouStock.getString("restriction_description"));
            receivingCondition.setReach_amount(yougouStock.getInteger("reach_amount"));
            Integer effective_duration = yougouStock.getInteger("effective_duration");
            if (effective_duration == null) {
                receivingCondition.setEffective_time(yougouStock.getString("effective_time"));
                receivingCondition.setExpired_time(yougouStock.getString("expired_time"));
            } else {
                DateTimeFormatter format = DateTimeFormat.forPattern(SysConstants.DATE_FORMAT);
                DateTime expired_time = DateTime.parse(receivingCondition.getReceiving_time(), format).millisOfDay().withMaximumValue().plusDays(effective_duration);
                receivingCondition.setEffective_time(receivingCondition.getReceiving_time());
                receivingCondition.setExpired_time(expired_time.toString(SysConstants.DATE_FORMAT));
            }
            synchronized (this) {
                Integer addResult = receivingDao.add(receivingCondition);
                if (addResult != 1) throw new RuntimeException("添加领取记录失败");
            }
        }
        synchronized (this) {
            StatCondition statCondition = new StatCondition();
            statCondition.setRelease_id(receivingCondition.getRelease_id());
            Integer updateResult = statDao.updateRemaining(statCondition);
            Integer stock_count = stock_id_list.split(",").length;
            if (!updateResult.equals(stock_count)) throw new RuntimeException("更新卡券池失败");
        }
    }


    public String update(ReceivingCondition receivingCondition) {
        Integer updateResult = receivingDao.update(receivingCondition);
        if (updateResult != 0) {
            return "ok";
        }
        return "error";
    }


    public String updateUserInfo(ReceivingCondition receivingCondition) {
        Integer result = receivingDao.updateUserInfo(receivingCondition);
        if (result == 0) {
            return "error";
        }
        return "ok";
    }


    public String countUserReceiving(ReceivingCondition receivingCondition, String stock_id_list) {
        if (StringUtils.isEmpty(stock_id_list)) {
            return "error";
        }
        Integer stock_count = stock_id_list.split(",").length;
        Integer dayReleaseCount = receivingDao.dayCountByReceiving(receivingCondition);
        Integer totaReleaselCount = receivingDao.totalCountByReceiving(receivingCondition);
        Integer dayCount = dayReleaseCount % stock_count == 0 ? dayReleaseCount / stock_count : dayReleaseCount / stock_count + 1;
        Integer totalCount = totaReleaselCount % stock_count == 0 ? totaReleaselCount / stock_count : totaReleaselCount / stock_count + 1;
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        resultMap.put("day_count_result", dayCount);
        resultMap.put("total_count_result", totalCount);
        return JSONObject.toJSONString(resultMap);
    }


    public String countRemainingRelease(String release_id) {
        StatCondition statCondition = new StatCondition();
        statCondition.setRelease_id(release_id);
        return statDao.countRemaining(statCondition) + "";
    }


    public String verifyYougouCoupon(YougouRestrictionCondition yougouRestrictionCondition) {
        YougouRestrictionCondition result = yougouRestrictionDao.query(yougouRestrictionCondition);
        if (result == null) {
            return "error";
        }
        return JSONObject.toJSONString(result);
    }


    public String queryOpenid(String appid, String code) {
        String key = "coupon_receiving_openid_" + appid + "_" + code;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                return shardedJedis.get(key);
            }
            WeixinService.AppConfig appConfig = weixinService.getAppConfig(appid);
            if (appConfig == null) {
                return "error";
            }
            String openid = weixinService.getOpenid(code, appid, appConfig.secret);
            if (StringUtils.isEmpty(openid)) {
                return "error";
            }
            shardedJedis.set(key, openid);
            shardedJedis.expire(key, 3600);
            return openid;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

}
