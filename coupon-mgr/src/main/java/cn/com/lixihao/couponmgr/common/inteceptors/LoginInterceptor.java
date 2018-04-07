package cn.com.lixihao.couponmgr.common.inteceptors;

import cn.com.lixihao.couponmgr.common.utils.MD5Util;
import cn.com.lixihao.couponmgr.common.utils.RequestHelper;
import cn.com.lixihao.couponmgr.common.utils.ResponseHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String tokenParameter = RequestHelper.getString(request, "token");
            if (StringUtils.isBlank(tokenParameter)) {
                tokenParameter = request.getParameter("token");
                if (StringUtils.isBlank(tokenParameter)) {
                    return false;
                }
                ResponseHelper.addCookie(response, "token", "/", tokenParameter, 3600);
            }
            String dateMd5 = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//设置日期格式,如20170922
            String dateStr = sdf.format(new Date());
            dateMd5 = MD5Util.getMd5(dateStr);
            if (dateMd5.equalsIgnoreCase(tokenParameter)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
