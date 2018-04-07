package cn.com.lixihao.couponmgr.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class ResponseHelper {
    public static void addCookie(HttpServletResponse response, String name, String path, String value, int maxAge) {
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            value = URLEncoder.encode(value, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
    }
}
