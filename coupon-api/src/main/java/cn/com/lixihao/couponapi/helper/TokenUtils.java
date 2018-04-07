package cn.com.lixihao.couponapi.helper;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TokenUtils {
    /**
     * 1、keyASCII字典序排序
     * 2、拼接为k=v&k=v&key=密钥
     * 3、DigestUtils.md5Hex(参数).toUpperCase();
     *
     * @return token
     */
    public static String youGouToken(Map<String, Object> map) {
        List<String> list = new ArrayList<String>();
        list.addAll(map.keySet());

        Collections.sort(list);

        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                tmp.append(list.get(i)).append("=").append(map.get(list.get(i)));
            } else {
                tmp.append("&").append(list.get(i)).append("=").append(map.get(list.get(i)));
            }
        }
        tmp = tmp.append("&key=7a773cb65e51632e23082cd9fe8e4219");

        return DigestUtils.md5Hex(tmp.toString()).toUpperCase();
    }
}
