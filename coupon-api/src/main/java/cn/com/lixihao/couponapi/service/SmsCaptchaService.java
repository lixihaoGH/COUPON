package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.api.SmsApi;
import cn.com.lixihao.couponapi.entity.condition.SmsCaptchaCondition;
import cn.com.lixihao.couponapi.entity.result.SmsCaptchaResponse;
import cn.com.lixihao.couponapi.dao.SmsCaptchaDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * create by lixihao on 2018/2/26.
 **/
@Service
public class SmsCaptchaService {

    private Logger log = LoggerFactory.getLogger(SmsCaptchaService.class);

    @Autowired
    SmsApi smsApi;
    @Autowired
    ShardedJedisPool shardedJedisPool;
    @Autowired
    SmsCaptchaDao smsCaptchaDao;


    private final String captchaKeyPrefix = "coupon_sms_captcha_";

    public SmsCaptchaResponse addCaptcha(String phone, Integer smsCaptchaSize, Integer intervalSecond, String contentTitle, Integer effectiveMinutes) {
        if (StringUtils.isEmpty(phone) || smsCaptchaSize == null || intervalSecond == null || StringUtils.isEmpty(contentTitle) || effectiveMinutes == null) {
            return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.LACK_PARAMS);
        }
        String key = "coupon_sms_captcha_send_" + phone + smsCaptchaSize;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                SmsCaptchaResponse smsCaptchaResponse = new SmsCaptchaResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.FREQUENCY_ERROR);
                smsCaptchaResponse.setReapplySecond(shardedJedis.pttl(key) / 1000);
                return smsCaptchaResponse;
            } else {
                shardedJedis.set(key, "1");
                shardedJedis.expire(key, intervalSecond);
            }
            String captcha = RandomStringUtils.randomNumeric(smsCaptchaSize);
            String content = "【" + contentTitle + "】验证码:" + captcha + "，验证码有效时间" + effectiveMinutes + "分钟。请勿告知他人，如非您本人操作请忽略";
            log.info("[SmsSendContent]phone->{},captcha->{}", new Object[]{phone, captcha});
            smsApi.send(phone, content);
            this.updateSmsCaptcha(phone, captcha, effectiveMinutes);
            String captchaKey = this.captchaKeyPrefix + phone;
            shardedJedis.set(captchaKey, captcha);
            shardedJedis.expire(captchaKey, 60 * effectiveMinutes);
            return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.FAIL);
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    public SmsCaptchaResponse verify(String phone, String smsCaptcha) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsCaptcha)) {
            return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.LACK_PARAMS);
        }
        String captchaKey = this.captchaKeyPrefix + phone;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(captchaKey)) {
                if (shardedJedis.get(captchaKey).equals(smsCaptcha)) {
                    shardedJedis.del(captchaKey);
                    return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.SUCCESS);
                }
                return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.VERIFY_ERROR);
            }
            SmsCaptchaCondition smsCaptchaCondition = new SmsCaptchaCondition();
            smsCaptchaCondition.setPhone(phone);
            SmsCaptchaCondition getResult = smsCaptchaDao.get(smsCaptchaCondition);
            if (getResult == null) {
                return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.EMPTY_ERROR);
            }
            if (getResult.getExpiry_time() < System.currentTimeMillis()) {
                return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.EXPIRY_ERROR);
            }
            if (!getResult.getSms_captcha().equals(smsCaptcha)) {
                return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.VERIFY_ERROR);
            }
            return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return this.returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum.VERIFY_ERROR);
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }

    }

    public Integer updateSmsCaptcha(String phone, String smsCaptcha, Integer effectiveMinutes) {
        SmsCaptchaCondition smsCaptchaCondition = new SmsCaptchaCondition();
        smsCaptchaCondition.setSms_captcha(smsCaptcha);
        smsCaptchaCondition.setPhone(phone);
        smsCaptchaCondition.setExpiry_time(System.currentTimeMillis() + effectiveMinutes * 60 * 1000);
        SmsCaptchaCondition getResult = smsCaptchaDao.get(smsCaptchaCondition);
        if (getResult == null) {
            return smsCaptchaDao.add(smsCaptchaCondition);
        }
        return smsCaptchaDao.update(smsCaptchaCondition);
    }

    public Integer delete(Long expiryTime) {
        SmsCaptchaCondition smsCaptchaCondition = new SmsCaptchaCondition();
        smsCaptchaCondition.setExpiry_time(expiryTime);
        return smsCaptchaDao.delete(smsCaptchaCondition);
    }

    private SmsCaptchaResponse returnResponse(SmsCaptchaResponse.SmsCaptchaMessageEnum messageEnum) {
        return new SmsCaptchaResponse(messageEnum);
    }
}
