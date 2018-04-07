package cn.com.lixihao.couponapi.api;

import com.hiveview.commons.http.HiveHttpEntityType;
import com.hiveview.commons.http.HiveHttpPost;
import com.hiveview.commons.http.HiveHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * create by lixihao on 2018/2/26.
 **/
@Repository
public class SmsApi {

    private Logger DATA = LoggerFactory.getLogger(SmsApi.class);

    private static final String SEND_URL = "http://best.sms.domybox.local/sms/http/send.json";

    public String send(String phones, String content) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phones", phones);
        map.put("content", content);
        HiveHttpResponse response = HiveHttpPost.postMap(SEND_URL, map, HiveHttpEntityType.STRING);
        DATA.info("[sendSms]phones->{},content->{},result->{},elapse->{}", new Object[]{phones, content, response.entityString, response.elapseMs});
        return response.entityString;
    }
}
