package cn.com.lixihao.couponweb.controller;

import cn.com.lixihao.couponweb.constant.WeixinConstants;
import cn.com.lixihao.couponweb.entity.UnifiedResponse;
import cn.com.lixihao.couponweb.entity.bo.*;
import cn.com.lixihao.couponweb.helper.ServletHelper;
import cn.com.lixihao.couponweb.service.CouponClientService;
import cn.com.lixihao.couponweb.service.CouponReceivingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * create by lixihao on 2017/12/12.
 **/
@CrossOrigin
@Controller
@RequestMapping(value = "/coupon")
public class CouponController {

    private Logger DATA = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    CouponReceivingService couponReceivingService;
    @Autowired
    CouponClientService couponClientService;


    /**
     * 授权接口
     *
     * @param httpServletResponse
     * @param release_id
     */
    @RequestMapping(value = "/authorize")
    public void authorize(HttpServletResponse httpServletResponse, String release_id) {
        String redirect_url = couponReceivingService.getOauth2Url(release_id, WeixinConstants.TESTCASE_APP_ID);
        DATA.info("[authorize]redirect_url->{}", redirect_url);
        try {
            httpServletResponse.sendRedirect(redirect_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 红包系统绑定
     *
     * @param code
     * @param state
     * @param release_id
     * @param request
     * @return
     */
    @RequestMapping(value = "/binding/query")
    public String queryBinding(String code, String state, String release_id, HttpServletRequest request) {
        Object[] args = new Object[]{code, state, release_id};
        DATA.info("[couponBindRequest]code->{},state->{},release_id->{}", args);
        Map<String, String> template = couponReceivingService.queryTemplate(release_id);
        Map<String, String> jssdkReceivingRequest = couponReceivingService.queryBinding(code, state, release_id);
        StringBuffer requestURL = request.getRequestURL();
        requestURL.delete(requestURL.length() - request.getRequestURI().length(), requestURL.length());
        String sourceurl = requestURL.append("coupon/binding/query.json?").append(request.getQueryString()).toString();
        Map<String, String> jssdkConfigRequest = couponReceivingService.jssdkConfig(WeixinConstants.TESTCASE_APP_ID, sourceurl);
        request.setAttribute("template", template);
        request.setAttribute("jssdkReceivingRequest", jssdkReceivingRequest);
        request.setAttribute("jssdkConfigRequest", jssdkConfigRequest);
        DATA.info("[couponBindResponse]jssdkReceivingRequest->{},jssdkConfigRequest->{},template->{}", jssdkReceivingRequest, jssdkConfigRequest, template);
        return "index";
    }

    /**
     * 验证码
     *
     * @param captchaResquest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/captcha")
    public CaptchaResponse handleCaptcha(CaptchaResquest captchaResquest) {
        DATA.info("[captchaResquest]request->{}", captchaResquest);
        CaptchaResponse captchaResponse = couponReceivingService.handleCaptcha(captchaResquest);
        Object[] args = new Object[]{captchaResquest.handle_type, captchaResquest.phone_number, captchaResponse.toString()};
        DATA.info("[captchaResponse]handle_type->{},phone_number->{},response->{}", args);
        return captchaResponse;
    }

    /**
     * 领取红包接口
     *
     * @param receivingRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/receiving", "/client/receiving"})
    public ReceivingResponse receiving(ReceivingRequest receivingRequest) {
        DATA.info("[receivingRequest]request->{}", receivingRequest);
        ReceivingResponse receivingResponse = couponReceivingService.receiving(receivingRequest);
        Object[] args = new Object[]{receivingRequest.release_id, receivingRequest.phone_number, receivingResponse};
        DATA.info("[receivingResponse]release_id->{},phone->{},response->{}", args);
        return receivingResponse;
    }

    /**
     * 优购端红包列表接口
     *
     * @param queryListRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/client/list/query")
    public QueryCouponResponse queryList(QueryListRequest queryListRequest) {
        DATA.info("[queryListRequest]request->{}", queryListRequest);
        QueryCouponResponse queryCouponResponse = couponClientService.querylist(queryListRequest);
        DATA.info("[queryListResponse]user_phone->{},response->{}", new Object[]{queryListRequest.user_phone, queryCouponResponse});
        return queryCouponResponse;
    }

    /**
     * 客户端可使用红包查询
     *
     * @param user_id
     * @param user_phone
     * @param bussiness_type
     * @param request_json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/client/query")
    public QueryCouponResponse queryCoupon(String user_id, String user_phone, Integer bussiness_type, String request_json) {
        Object[] requestArgs = new Object[]{user_id, user_phone, bussiness_type, request_json};
        DATA.info("[queryCouponRequest]user_id->{},user_phone->{},bussiness_type->{},request->{}", requestArgs);
        QueryCouponResponse queryCouponResponse = couponClientService.queryCoupon(user_id, user_phone, bussiness_type, request_json);
        Object[] responseArgs = new Object[]{user_id, user_phone, bussiness_type, queryCouponResponse};
        DATA.info("[queryCouponResponse]user_id->{},user_phone->{},bussiness_type->{},response->{}", responseArgs);
        return queryCouponResponse;
    }

    /**
     * 接收客户端订单状态通知，更新红包状态
     *
     * @param tradeNotifyRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/trade/notify")
    public UnifiedResponse tradeNotify(TradeNotifyRequest tradeNotifyRequest) {
        DATA.info("[tradeNotifyRequest]request->{}", tradeNotifyRequest);
        UnifiedResponse unifiedResponse = couponClientService.tradeNotify(tradeNotifyRequest);
        Object[] args = new Object[]{tradeNotifyRequest.trade_no, tradeNotifyRequest.trade_status, unifiedResponse};
        DATA.info("[tradeNotifyResponse]trade_no->{},trade_status->{},response->{}", args);
        return unifiedResponse;
    }

    /**
     * 支付端红包校验
     */
    @ResponseBody
    @RequestMapping(value = "/pay/verify")
    public PayVerifyResponse payVerify(PayVerifyRequest payVerifyRequest) {
        DATA.info("[payVerifyRequest]request->{}", payVerifyRequest);
        PayVerifyResponse payVerifyResponse = couponClientService.payVerify(payVerifyRequest);
        DATA.info("[payVerifyResponse]response->{}", payVerifyResponse);
        return payVerifyResponse;
    }

    /**
     * 红包回退
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/restore")
    public UnifiedResponse restore(HttpServletRequest httpServletRequest) {
        DATA.info("[restoreCouponRequest]request_ip->{}", ServletHelper.getRemoteAddr(httpServletRequest));
        UnifiedResponse unifiedResponse = couponClientService.restore();
        DATA.info("[restoreCouponResponse]response->{}", unifiedResponse);
        return unifiedResponse;
    }

}
