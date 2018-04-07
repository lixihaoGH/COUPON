package cn.com.lixihao.couponweb.service;

import cn.com.lixihao.couponweb.constant.SysConstants;
import cn.com.lixihao.couponweb.entity.CouponReceiving;
import cn.com.lixihao.couponweb.entity.UnifiedMessageEnum;
import cn.com.lixihao.couponweb.entity.UnifiedResponse;
import cn.com.lixihao.couponweb.entity.bo.*;
import cn.com.lixihao.couponweb.entity.vo.*;
import cn.com.lixihao.couponweb.service.api.BindingApi;
import cn.com.lixihao.couponweb.service.api.ReceivingApi;
import cn.com.lixihao.couponweb.service.api.TradeApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lixihao on 2017/12/12.
 **/
@Service
public class CouponClientService {


    @Autowired
    ReceivingApi receivingApi;
    @Autowired
    BindingApi bindingApi;
    @Autowired
    TradeApi tradeApi;

    public QueryCouponResponse querylist(QueryListRequest queryListRequest) {
        if (QueryListRequest.isInvalid(queryListRequest)) {
            return new QueryCouponResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        String user_id = queryListRequest.user_id;
        String user_phone = queryListRequest.user_phone;
        String updateResult = this.updateBinding(user_id, user_phone);
        if (updateResult.equals(SysConstants.RETURN_FAIL)) {
            return new QueryCouponResponse(UnifiedMessageEnum.SYSTEMERROR.getCode(), UnifiedMessageEnum.SYSTEMERROR.getName());
        }
        receivingApi.updateReceivingUserInfo(user_id, user_phone);
        List<CouponReceiving> couponList = receivingApi.querylist(user_id, user_phone, queryListRequest.coupon_status, queryListRequest.page_index, queryListRequest.page_size);
        if (couponList == null) {
            return new QueryCouponResponse(UnifiedMessageEnum.POST_DATA_EMPTY.getCode(), UnifiedMessageEnum.POST_DATA_EMPTY.getName() + "2");
        }
        QueryCouponDetail queryCouponDetail = new QueryCouponDetail(couponList.size(), couponList);
        return new QueryCouponResponse(UnifiedMessageEnum.SUCCESS.getCode(), UnifiedMessageEnum.SUCCESS.getName(), queryListRequest.page_index, queryListRequest.page_size, queryCouponDetail);
    }


    public QueryCouponResponse queryCoupon(String user_id, String user_phone, Integer bussiness_type, String request_json) {
        if (StringUtils.isEmpty(user_id) || StringUtils.isEmpty(user_phone) || bussiness_type == null || StringUtils.isEmpty(request_json)) {
            return new QueryCouponResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        String updateResult = this.updateBinding(user_id, user_phone);
        if (updateResult.equals(SysConstants.RETURN_FAIL)) {
            return new QueryCouponResponse(UnifiedMessageEnum.SYSTEMERROR.getCode(), UnifiedMessageEnum.SYSTEMERROR.getName());
        }
        receivingApi.updateReceivingUserInfo(user_id, user_phone);
        switch (bussiness_type) {
            case 1:
                return this.queryYougouCoupon(user_id, user_phone, request_json);
        }
        return new QueryCouponResponse(UnifiedMessageEnum.SYSTEMERROR.getCode(), UnifiedMessageEnum.SYSTEMERROR.getName());
    }


    private QueryCouponResponse queryYougouCoupon(String user_id, String phone_number, String request_json) {
        JSONObject requestJSON = JSONObject.parseObject(request_json);
        if (requestJSON == null || requestJSON.isEmpty()) {
            return new QueryCouponResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        Integer price = requestJSON.getInteger("price");
        String goods_category = requestJSON.getString("goods_category");
        String grade_1st_category_sn = requestJSON.getString("grade_1st_category_sn");
        String grade_2nd_category_sn = requestJSON.getString("grade_2nd_category_sn");
        String grade_3rd_category_sn = requestJSON.getString("grade_3rd_category_sn");
        String good_sku_sn = requestJSON.getString("good_sku_sn");
        if (price == null || goods_category == null || StringUtils.isEmpty(grade_1st_category_sn)
                || StringUtils.isEmpty(grade_2nd_category_sn) || StringUtils.isEmpty(grade_3rd_category_sn) || StringUtils.isEmpty(good_sku_sn)) {
            return new QueryCouponResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        List<CouponReceiving> couponList = receivingApi.queryYougouCoupon(user_id, phone_number, price, grade_1st_category_sn, grade_2nd_category_sn, grade_3rd_category_sn, good_sku_sn, goods_category);
        if (couponList == null) {
            return new QueryCouponResponse(UnifiedMessageEnum.SUCCESS.getCode(), UnifiedMessageEnum.SUCCESS.getName(), new QueryCouponDetail(0));
        }
        return new QueryCouponResponse(UnifiedMessageEnum.SUCCESS.getCode(), UnifiedMessageEnum.SUCCESS.getName(), new QueryCouponDetail(couponList.size(), couponList));
    }


    public UnifiedResponse tradeNotify(TradeNotifyRequest tradeNotifyRequest) {
        if (TradeNotifyRequest.isInvalid(tradeNotifyRequest)) {
            return new UnifiedResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        Integer price = tradeNotifyRequest.total_amount;
        String coupon_stock_id = tradeNotifyRequest.coupon_stock_id;
        if (tradeNotifyRequest.trade_status.equals(SysConstants.TRADE_STATUS_INIT)) {
            String goods_category = tradeNotifyRequest.goods_category;
            String grade_1st_category_sn = tradeNotifyRequest.grade_1st_category_sn;
            String grade_2nd_category_sn = tradeNotifyRequest.grade_2nd_category_sn;
            String grade_3rd_category_sn = tradeNotifyRequest.grade_3rd_category_sn;
            String good_sku_sn = tradeNotifyRequest.good_sku_sn;
            JSONObject result = receivingApi.verifyYougouCoupon(coupon_stock_id, price, grade_1st_category_sn, grade_2nd_category_sn, grade_3rd_category_sn, good_sku_sn, goods_category);
            if (result == null || result.isEmpty()) {
                return new UnifiedResponse(UnifiedMessageEnum.VERIFY_ERROR.getCode(), UnifiedMessageEnum.VERIFY_ERROR.getName());
            }
        }
        CouponReceiving coupon = receivingApi.getReceiving(tradeNotifyRequest.coupon_id, tradeNotifyRequest.user_id, tradeNotifyRequest.user_phone, coupon_stock_id, tradeNotifyRequest.coupon_status);
        if (coupon == null) {
            return new UnifiedResponse(UnifiedMessageEnum.POST_DATA_EMPTY.getCode(), UnifiedMessageEnum.POST_DATA_EMPTY.getName());
        }
        boolean verifyResult = false;
        if (coupon.preferential_type.equals(SysConstants.PREFERENTIAL_TYPE_REDUCE)) {
            if (tradeNotifyRequest.deduction_amount.equals(coupon.preferential_amount) && tradeNotifyRequest.payment_amount.equals(price - coupon.preferential_amount))
                verifyResult = true;
        } else if (coupon.preferential_type.equals(SysConstants.PREFERENTIAL_TYPE_DISCOUNT)) {
            if (tradeNotifyRequest.payment_amount.equals(price * coupon.discount / 100) && tradeNotifyRequest.deduction_amount.equals(price * (100 - coupon.discount) / 100))
                verifyResult = true;
        }
        if (!verifyResult) {
            return new UnifiedResponse(UnifiedMessageEnum.VERIFY_ERROR.getCode(), UnifiedMessageEnum.VERIFY_ERROR.getName());
        }
        String updateReceivingResult = this.updateTrade(tradeNotifyRequest.coupon_id, coupon_stock_id, tradeNotifyRequest.trade_status, tradeNotifyRequest.user_id, tradeNotifyRequest.trade_no, price, tradeNotifyRequest.deduction_amount, tradeNotifyRequest.payment_amount);
        if (updateReceivingResult.equals(SysConstants.RETURN_FAIL)) {
            return new UnifiedResponse(UnifiedMessageEnum.FAIL.getCode(), UnifiedMessageEnum.FAIL.getName());
        }
        return new UnifiedResponse(UnifiedMessageEnum.SUCCESS.getCode(), UnifiedMessageEnum.SUCCESS.getName());
    }


    public PayVerifyResponse payVerify(PayVerifyRequest payVerifyRequest) {
        if (PayVerifyRequest.isInvalid(payVerifyRequest)) {
            return new PayVerifyResponse(UnifiedMessageEnum.LACK_PARAMS.getCode(), UnifiedMessageEnum.LACK_PARAMS.getName());
        }
        JSONArray getTradeListResult = tradeApi.getTradeList(payVerifyRequest.trade_no, payVerifyRequest.trade_status);
        if (getTradeListResult == null || getTradeListResult.isEmpty()) {
            return new PayVerifyResponse(UnifiedMessageEnum.POST_DATA_EMPTY.getCode(), UnifiedMessageEnum.POST_DATA_EMPTY.getName());
        }
        List<CouponItem> systemTradeList = new ArrayList<CouponItem>();
        for (Object object : getTradeListResult) {
            JSONObject tradeJSON = (JSONObject) object;
            CouponItem couponItem = new CouponItem();
            couponItem.coupon_id = tradeJSON.getString("coupon_id");
            couponItem.coupon_stock_id = tradeJSON.getString("coupon_stock_id");
            couponItem.coupon_amount = tradeJSON.getInteger("deduction_amount");
            systemTradeList.add(couponItem);
        }
        List<CouponItem> verifyTradeList = new ArrayList<CouponItem>();
        CouponDetail couponDetail = JSON.parseObject(payVerifyRequest.coupon_detail, CouponDetail.class);
        if (couponDetail.coupon_item != null) {
            verifyTradeList = couponDetail.coupon_item;
        }
        verifyTradeList.removeAll(systemTradeList);
        if (verifyTradeList.isEmpty()) {
            return new PayVerifyResponse(UnifiedMessageEnum.SUCCESS.getCode(), UnifiedMessageEnum.SUCCESS.getName());
        }
        VerifyFailDetail verifyFailDetail = new VerifyFailDetail();
        List<VerifyFailItem> verifyFailItemList = new ArrayList<VerifyFailItem>();
        for (CouponItem couponItem : verifyTradeList) {
            CouponReceiving couponReceiving = receivingApi.getReceiving(couponItem.coupon_id, couponItem.coupon_stock_id);
            if (couponReceiving == null) {
                return new PayVerifyResponse(UnifiedMessageEnum.POST_DATA_EMPTY.getCode(), UnifiedMessageEnum.POST_DATA_EMPTY.getName());
            }
            VerifyFailItem verifyFailItem = new VerifyFailItem();
            verifyFailItem.coupon_id = couponItem.coupon_id;
            if (!couponItem.coupon_amount.equals(couponReceiving.preferential_amount)) {
                verifyFailItem.amount_error = 1;
            }
            if (!couponReceiving.coupon_status.equals(SysConstants.COUPON_STATUS_LOCK)) {
                verifyFailItem.status_error = 1;
            }
            verifyFailItemList.add(verifyFailItem);
        }
        verifyFailDetail.fail_item = verifyFailItemList;
        return new PayVerifyResponse(UnifiedMessageEnum.VERIFY_ERROR.getCode(), UnifiedMessageEnum.VERIFY_ERROR.getName(), verifyFailDetail);
    }

    public UnifiedResponse restore() {
        String create_time = new DateTime().minusMinutes(30).toString(SysConstants.DATE_FORMAT);
        Integer trade_status = SysConstants.TRADE_STATUS_INIT;
        String restoreResult = tradeApi.cancelExpiredTrade(trade_status, create_time);
        if (StringUtils.isEmpty(restoreResult)) {
            return new UnifiedResponse(UnifiedMessageEnum.FAIL.getCode(), UnifiedMessageEnum.FAIL.getName());
        }
        return new UnifiedResponse(UnifiedMessageEnum.SUCCESS.getCode(), UnifiedMessageEnum.SUCCESS.getName());
    }

    private String updateTrade(String coupon_id, String coupon_stock_id, Integer trade_status, String user_id, String trade_no, Integer total_amount, Integer deduction_amount, Integer payment_amount) {
        String updateResult = SysConstants.RETURN_SUCCESS;
        if (trade_status.equals(SysConstants.TRADE_STATUS_INIT)) {
            updateResult = tradeApi.addTrade(trade_no, coupon_id, coupon_stock_id, user_id, total_amount, deduction_amount, payment_amount, trade_status);
        } else if (trade_status.equals(SysConstants.TRADE_STATUS_PAID) || trade_status.equals(SysConstants.TRADE_STATUS_CANCEL)) {
            updateResult = tradeApi.updateTrade(coupon_id, trade_no, trade_status);
        }
        if (StringUtils.isEmpty(updateResult)) {
            updateResult = SysConstants.RETURN_FAIL;
        }
        return updateResult;
    }

    private String updateBinding(String user_id, String phone_number) {
        JSONObject bindingJSON = bindingApi.getBinding(SysConstants.GET_BINDING_PARAM_TYPE_PHONE, phone_number);
        String updateResult = SysConstants.RETURN_SUCCESS;
        if (bindingJSON == null || bindingJSON.isEmpty()) {
            updateResult = bindingApi.addBinding(phone_number, SysConstants.ADD_BINDING_PARAM_USERID, user_id);
        } else {
            String userIdJson = bindingJSON.getString("user_id");
            if (StringUtils.isEmpty(userIdJson) || !userIdJson.equals(user_id)) {
                updateResult = bindingApi.updateBinding(phone_number, SysConstants.ADD_BINDING_PARAM_USERID, user_id);
            }
        }
        if (StringUtils.isEmpty(updateResult)) {
            updateResult = SysConstants.RETURN_FAIL;
        }
        return updateResult;
    }

}
