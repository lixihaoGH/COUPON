package cn.com.lixihao.couponweb.interceptor;

import cn.com.lixihao.couponweb.constant.SysConstants;
import cn.com.lixihao.couponweb.entity.UnifiedMessageEnum;
import cn.com.lixihao.couponweb.entity.UnifiedResponse;
import cn.com.lixihao.couponweb.helper.CouponHelper;
import cn.com.lixihao.couponweb.helper.ServletHelper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * create by lixihao on 2018/2/6.
 **/

public class SignVerifyInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String requestURI = request.getRequestURI();
        Map<String, String> parameterMap = ServletHelper.getParameterMap(request);
        if (requestURI.contains("/client") || requestURI.contains("/pay") || requestURI.contains("/trade")) {
            if (!CouponHelper.verifyMD5Sign(parameterMap, SysConstants.WEIXIN_WEB_COUPON_KEY)) {
                return this.writeResponse(response, UnifiedMessageEnum.SIGN_ERROR);
            }
        }
        return super.preHandle(request, response, object);
    }

    public boolean writeResponse(HttpServletResponse response, UnifiedMessageEnum message) {
        ServletHelper.writeResultString(response, new UnifiedResponse(message.getCode(), message.getName()).toString());
        return false;
    }
}
