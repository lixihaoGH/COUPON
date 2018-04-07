package cn.com.lixihao.couponweb.entity.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * create by lixihao on 2018/1/24.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class QueryListRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public String user_id;
    public String user_phone;
    public Integer coupon_status; //0-可用；1-锁定中；2-已使用；3-已过期
    public Integer page_index;
    public Integer page_size;

    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static boolean isInvalid(QueryListRequest queryListRequest) {
        if (StringUtils.isEmpty(queryListRequest.user_id)
                || StringUtils.isEmpty(queryListRequest.user_phone)
                || queryListRequest.coupon_status == null
                || queryListRequest.page_index == null
                || queryListRequest.page_size == null) {
            return true;
        }
        return false;
    }
}

