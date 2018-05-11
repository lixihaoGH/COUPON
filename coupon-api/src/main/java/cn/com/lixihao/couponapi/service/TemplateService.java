package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.constants.ApiConstants;
import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;
import cn.com.lixihao.couponapi.dao.TemplateDao;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class TemplateService {

    @Autowired
    private TemplateDao templateDao;
    @Autowired
    ShardedJedisPool shardedJedisPool;

    public String get(TemplateCondition condition) {
        String key = "coupon_template_get_" + condition.release_id;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                return shardedJedis.get(key);
            }
            TemplateCondition response = templateDao.get(condition);
            if (response == null) {
                response = new TemplateCondition(0);
            } else {
                response.share_img_url = ApiConstants.COUPON_WEBACCESS_PATH + response.share_img_url;
                response.page_img_url = ApiConstants.COUPON_WEBACCESS_PATH + response.page_img_url;
            }
            String responseJson = JSONObject.toJSONString(response);
            shardedJedis.set(key, responseJson);
            shardedJedis.expire(key, 360);
            return responseJson;
        } catch (Exception e) {
            throw new RuntimeException("NOT FOUND");
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }
}
