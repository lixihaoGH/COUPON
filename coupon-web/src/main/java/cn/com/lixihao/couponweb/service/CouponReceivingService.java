package cn.com.lixihao.couponweb.service;


import cn.com.lixihao.couponweb.constant.SysConstants;
import cn.com.lixihao.couponweb.constant.WeixinConstants;
import cn.com.lixihao.couponweb.entity.CouponStock;
import cn.com.lixihao.couponweb.entity.UnifiedMessageEnum;
import cn.com.lixihao.couponweb.entity.bo.CaptchaResponse;
import cn.com.lixihao.couponweb.entity.bo.CaptchaResquest;
import cn.com.lixihao.couponweb.entity.bo.ReceivingRequest;
import cn.com.lixihao.couponweb.entity.bo.ReceivingResponse;
import cn.com.lixihao.couponweb.entity.vo.ReceivingDetail;
import cn.com.lixihao.couponweb.service.api.BindingApi;
import cn.com.lixihao.couponweb.service.api.ReceivingApi;
import cn.com.lixihao.couponweb.service.api.RelayApi;
import cn.com.lixihao.couponweb.service.api.SmsCaptchaApi;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.commons.http.HiveHttpQueryString;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * create by lixihao on 2017/12/12.
 **/
@Service
public class CouponReceivingService {

    private Logger DATA = LoggerFactory.getLogger(CouponReceivingService.class);

    @Autowired
    ReceivingApi receivingApi;
    @Autowired
    BindingApi bindingApi;
    @Autowired
    SmsCaptchaApi smsCaptchaApi;
    @Autowired
    RelayApi relayApi;

    public String getOauth2Url(String release_id, String appid) {
        StringBuffer resultUrl = new StringBuffer(SysConstants.WEIXIN_WEB + "coupon/binding/query.json");
        resultUrl.append("?release_id=").append(release_id);
        String redirect_uri = resultUrl.toString();
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, SysConstants.CHARACTER_SET_ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String oauth2Url = relayApi.getOauth2Url(appid, redirect_uri, SysConstants.OAUTH2_COUPON_STATE);
        if (StringUtils.isEmpty(oauth2Url)) {
            oauth2Url = "http://www.hiveview.com/";
        }
        return oauth2Url;
    }


    public Map<String, String> queryBinding(String code, String state, String release_id) {
        Map<String, String> jssdkReceivingRequest = new HashMap<String, String>();
        jssdkReceivingRequest.put(SysConstants.RETURN_CODE, SysConstants.RETURN_SUCCESS);
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state) || StringUtils.isEmpty(release_id) || !state.equals(SysConstants.OAUTH2_COUPON_STATE)) {
            jssdkReceivingRequest.put(SysConstants.RETURN_CODE, SysConstants.RETURN_FAIL);
            jssdkReceivingRequest.put(SysConstants.ISBINDED_STATUS, SysConstants.ISBINDED_FALSE);
            return jssdkReceivingRequest;
        }
        jssdkReceivingRequest.put("release_id", release_id);
        jssdkReceivingRequest.put("device_type", SysConstants.DEVICE_MOBILE + "");
        String openid = receivingApi.queryOpenid(WeixinConstants.HIVEVIEW_APP_ID, code);
        if (StringUtils.isEmpty(openid)) {
            jssdkReceivingRequest.put(SysConstants.RETURN_CODE, SysConstants.RETURN_FAIL);
            jssdkReceivingRequest.put(SysConstants.ISBINDED_STATUS, SysConstants.ISBINDED_FALSE);
            return jssdkReceivingRequest;
        }
        jssdkReceivingRequest.put("openid", openid);
        JSONObject bindingResult = bindingApi.getBinding(SysConstants.GET_BINDING_PARAM_TYPE_OPENID, openid);
        if (bindingResult == null || bindingResult.isEmpty()) {
            jssdkReceivingRequest.put(SysConstants.ISBINDED_STATUS, SysConstants.ISBINDED_FALSE);
            return jssdkReceivingRequest;
        }
        jssdkReceivingRequest.put(SysConstants.ISBINDED_STATUS, SysConstants.ISBINDED_TRUE);
        jssdkReceivingRequest.put("user_id", bindingResult.getString("user_id"));
        jssdkReceivingRequest.put("phone_number", bindingResult.getString("phone_number"));
        return jssdkReceivingRequest;
    }


    public CaptchaResponse handleCaptcha(CaptchaResquest captchaResquest) {
        if (CaptchaResquest.isInvalid(captchaResquest)) {
            return new CaptchaResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        String phone_number = captchaResquest.phone_number;
        String openid = captchaResquest.openid;
        if (SysConstants.CAPTCHA_HANDLE_SEND.equals(captchaResquest.handle_type)) {
            return this.sendSmsCaptcha(phone_number);
        }
        JSONObject verifyResult = smsCaptchaApi.verifySmsCaptcha(phone_number, captchaResquest.captcha);
        if (verifyResult == null || verifyResult.isEmpty()) {
            return new CaptchaResponse(UnifiedMessageEnum.FAIL.getCode(), "系统出错，验证失败");
        }
        if (!verifyResult.getBoolean("return_code")) {
            return new CaptchaResponse(UnifiedMessageEnum.FAIL.getCode(), verifyResult.getString("return_msg"));
        }
        JSONObject binding = bindingApi.getBinding(SysConstants.GET_BINDING_PARAM_TYPE_PHONE, phone_number);
        if (binding == null || binding.isEmpty()) {
            bindingApi.addBinding(phone_number, SysConstants.ADD_BINDING_PARAM_OPENID, openid);
        } else {
            bindingApi.updateBinding(phone_number, SysConstants.ADD_BINDING_PARAM_OPENID, openid);
        }
        return new CaptchaResponse(UnifiedMessageEnum.SUCCESS.getCode(), UnifiedMessageEnum.SUCCESS.getName());
    }


    public ReceivingResponse receiving(ReceivingRequest receivingRequest) {
        if (ReceivingRequest.isInvalid(receivingRequest)) {
            return new ReceivingResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        if (receivingRequest.release_id.equals(SysConstants.ENTRANCE_ERROR)) {
            return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_RELEASE_EXPIRED.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_POST_DATA_EMPTY.getName());
        }
        if (receivingRequest.device_type == null || !receivingRequest.device_type.equals(SysConstants.DEVICE_MOBILE)) {
            receivingRequest.device_type = SysConstants.DEVICE_BOX;
        }
        JSONObject releaseDetail = receivingApi.getRelease(receivingRequest.release_id);
        if (releaseDetail == null || releaseDetail.isEmpty()) {
            return new ReceivingResponse(UnifiedMessageEnum.SYSTEMERROR.getCode(), UnifiedMessageEnum.SYSTEMERROR.getName());
        }
        JSONObject release = releaseDetail.getJSONObject("release");
        String stockIdList = release.getString("stock_id_list");
        List<CouponStock> couponStockList = receivingApi.getCouponStock(stockIdList);
        if (couponStockList == null || couponStockList.isEmpty()) {
            return new ReceivingResponse(UnifiedMessageEnum.SYSTEMERROR.getCode(), UnifiedMessageEnum.SYSTEMERROR.getName());
        }
        ReceivingDetail unifiedDetail = new ReceivingDetail(SysConstants.RECEIVING_REMAINING, couponStockList);
        String releaseStatus = release.getString("release_status");
        if (!releaseStatus.equals(SysConstants.RELEASE_STATUS_EFFECTIVE)) {
            return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_RELEASE_EXPIRED.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_RELEASE_EXPIRED.getName(), unifiedDetail);
        }
        DateTimeFormatter format = DateTimeFormat.forPattern(SysConstants.DATE_FORMAT);
        DateTime releaseStartTime = DateTime.parse(release.getString("release_start_time"), format);
        DateTime releaseEndTime = DateTime.parse(release.getString("release_end_time"), format);
        if (releaseStartTime.isAfterNow() || releaseEndTime.isBeforeNow()) {
            return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_RELEASE_EXPIRED.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_RELEASE_EXPIRED.getName(), unifiedDetail);
        }
        Integer remainingReleaseNum = receivingApi.countRemainingRelease(receivingRequest.release_id);
        if (remainingReleaseNum == null || remainingReleaseNum <= 0) {
            return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_RELEASE_EMPTY.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_RELEASE_EMPTY.getName(), unifiedDetail);
        }
        Integer remaining_count = 0;
        String receivingResult;
        synchronized (remaining_count) {
            JSONObject receivingCount = receivingApi.countUserReceiving(receivingRequest.release_id, receivingRequest.device_type, receivingRequest.phone_number, stockIdList);
            if (receivingCount == null || receivingCount.isEmpty()) {
                return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_POST_DATA_EMPTY.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_POST_DATA_EMPTY.getName());
            }
            Integer dayCount = receivingCount.getInteger("day_count_result");
            Integer totalCount = receivingCount.getInteger("total_count_result");
            JSONObject restriction = releaseDetail.getJSONObject("receiving_restriction");
            Map<String, Integer> remainingMap = this.getRemainingCount(receivingRequest.device_type, dayCount, totalCount, restriction);
            if (remainingMap == null || remainingMap.isEmpty()) {
                return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_POST_DATA_EMPTY.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_POST_DATA_EMPTY.getName());
            }
            remaining_count = remainingMap.get(this.REMAINING_COUNT);
            if (remaining_count <= 0) {
                unifiedDetail.day_restriction_num = remainingMap.get(this.DAY_RESTRICTION);
                unifiedDetail.total_restriction_num = remainingMap.get(this.TOTAL_RESTRICTION);
                return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_TOTAL.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_TOTAL.getName(), unifiedDetail);
            }
            receivingResult = receivingApi.receivingCoupon(receivingRequest.user_id, receivingRequest.phone_number, receivingRequest.openid, receivingRequest.release_id, stockIdList, receivingRequest.device_type);
        }
        if (StringUtils.isEmpty(receivingResult)) {
            return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_FAIL_POST_DATA_EMPTY.getCode(), UnifiedMessageEnum.RECEIVING_FAIL_POST_DATA_EMPTY.getName(), unifiedDetail);
        }
        ReceivingDetail receivingDetail = new ReceivingDetail(remaining_count - 1, receivingRequest.phone_number, couponStockList);
        return new ReceivingResponse(UnifiedMessageEnum.RECEIVING_SUCCESS.getCode(), UnifiedMessageEnum.RECEIVING_SUCCESS.getName(), receivingDetail);
    }


    public CaptchaResponse sendSmsCaptcha(String userPhone) {
        CaptchaResponse captchaResponse = new CaptchaResponse();
        captchaResponse.setReturn_code(UnifiedMessageEnum.SUCCESS.getCode());
        Integer intervalSencond = 60;
        JSONObject sendResult = smsCaptchaApi.sendSmsCaptcha(userPhone, 4, 60, "大麦盒子", 15);
        if (sendResult == null || sendResult.isEmpty()) {
            captchaResponse.setReturn_code(UnifiedMessageEnum.FAIL.getCode());
            captchaResponse.setReturn_message("短信验证码发送失败");
            return captchaResponse;
        }
        captchaResponse.setReturn_message(sendResult.getString("return_msg"));
        if (!sendResult.getBoolean("return_code")) {
            captchaResponse.setReturn_code(UnifiedMessageEnum.FAIL.getCode());
            if (sendResult.getInteger("reapplySecond") != null) {
                captchaResponse.setReapplySecond(sendResult.getInteger("reapplySecond"));
            }
            return captchaResponse;
        }
        captchaResponse.setIntervalSecond(intervalSencond);
        return captchaResponse;
    }


    private Map<String, Integer> getRemainingCount(Integer device_type, Integer dayCount, Integer totalCount, JSONObject restriction) {
        Map<String, Integer> remainingMap = new HashMap<String, Integer>();
        remainingMap.put(this.REMAINING_COUNT, 0);
        Integer dayRrestriction = restriction.getInteger("box_day_max");
        Integer totalRestriction = restriction.getInteger("box_total_max");
        if (device_type.equals(SysConstants.DEVICE_MOBILE)) {
            dayRrestriction = restriction.getInteger("phone_day_max");
            totalRestriction = restriction.getInteger("phone_total_max");
        }
        if (dayRrestriction == null && totalRestriction == null) {
            return new HashMap<String, Integer>();
        }
        if (dayRrestriction != null && totalRestriction == null) {
            if (dayRrestriction - dayCount <= 0) {
                remainingMap.put(this.DAY_RESTRICTION, dayRrestriction);
                return remainingMap;
            }
            remainingMap.put(this.REMAINING_COUNT, dayRrestriction - dayCount);
            return remainingMap;
        } else if (dayRrestriction == null) {
            if (totalRestriction - totalCount <= 0) {
                remainingMap.put(this.TOTAL_RESTRICTION, totalRestriction);
                return remainingMap;
            }
            remainingMap.put(this.REMAINING_COUNT, totalRestriction - totalCount);
            return remainingMap;
        }
        Integer totalRemaining = totalRestriction - totalCount;
        Integer dayRemaining = dayRrestriction - dayCount;
        if (dayRemaining <= 0) {
            remainingMap.put(this.DAY_RESTRICTION, dayRrestriction);
            return remainingMap;
        }
        if (totalRemaining <= 0) {
            remainingMap.put(this.TOTAL_RESTRICTION, totalRestriction);
            return remainingMap;
        }
        if (totalRemaining <= dayRrestriction && totalRemaining <= dayRemaining) {
            remainingMap.put(this.REMAINING_COUNT, totalRemaining);
            return remainingMap;
        }
        remainingMap.put(this.REMAINING_COUNT, dayRemaining);
        return remainingMap;
    }


    public Map<String, String> queryTemplate(String release_id) {
        Map<String, String> templateMap = new HashMap<String, String>();
        JSONObject template = receivingApi.queryTemplate(release_id);
        if (template == null) {
            return new HashMap<String, String>();
        }
        StringBuffer share_link = new StringBuffer(SysConstants.WEIXIN_WEB + "coupon/authorize.json");
        share_link.append("?release_id=").append(release_id);
        templateMap.put("share_link", share_link.toString());
        templateMap.put("share_title", template.getString("share_title"));
        templateMap.put("share_desc", template.getString("share_desc"));
        templateMap.put("share_img_url", template.getString("share_img_url"));
        templateMap.put("page_title", template.getString("page_title"));
        templateMap.put("page_bgcolor", template.getString("page_bgcolor"));
        templateMap.put("page_img_url", template.getString("page_img_url"));
        templateMap.put("goto_page_url", template.getString("goto_page_url"));
        return templateMap;
    }


    public Map<String, String> jssdkConfig(String appid, String sourceurl) {
        String ticket = relayApi.getTicket(appid);
        if (StringUtils.isEmpty(ticket)) {
            return new HashMap<String, String>();
        }
        Map<String, String> jssdkConfigRequest = new HashMap<String, String>();
        jssdkConfigRequest.put("noncestr", RandomStringUtils.randomAlphanumeric(16));
        jssdkConfigRequest.put("timestamp", System.currentTimeMillis() / 1000 + "");
        jssdkConfigRequest.put("jsapi_ticket", ticket);
        jssdkConfigRequest.put("url", sourceurl);
        jssdkConfigRequest.put("signature", DigestUtils.sha1Hex(HiveHttpQueryString.buildQuery(jssdkConfigRequest)));
        jssdkConfigRequest.put("appid", appid);
        jssdkConfigRequest.remove("jsapi_ticket");
        return jssdkConfigRequest;
    }


    private final String REMAINING_COUNT = "remaining_count";
    private final String DAY_RESTRICTION = "day_restriction_num";
    private final String TOTAL_RESTRICTION = "total_restriction_num";

}
