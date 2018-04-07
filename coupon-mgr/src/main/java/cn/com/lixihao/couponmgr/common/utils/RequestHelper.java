package cn.com.lixihao.couponmgr.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestHelper {
    public static String getString(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        String string = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                string = cookie.getValue();
                break;
            }
        }
        return string;
    }

    public static Map<String, Cookie> getMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
