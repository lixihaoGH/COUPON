package cn.com.lixihao.couponweb.helper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * create by lixihao on 2018/1/23.
 **/

public class ServletHelper {

    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }

    /**
     * 获取客户端请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parameter = new HashMap<String, String>();
        Map<String, String[]> map = request.getParameterMap();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] value = map.get(key);
            if (value == null) {
                parameter.put(key, "");
            } else if (value.length == 1) {
                parameter.put(key, value[0]);
            } else {
                parameter.put(key, StringUtils.join(value, ","));
            }
        }
        return parameter;
    }

    /**
     * 写入服务端响应结果
     *
     * @param response
     * @param result
     */
    public static void writeResultString(HttpServletResponse response, String result) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.write(result);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
