package cn.com.lixihao.couponweb.entity.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/26.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class ReceivingRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public String phone_number;
    public String user_id;
    public String openid;
    public String release_id;
    public Integer device_type;

    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static boolean isInvalid(ReceivingRequest receivingRequest) {
        if (StringUtils.isEmpty(receivingRequest.phone_number) || StringUtils.isEmpty(receivingRequest.release_id)) {
            return true;
        }
        return false;
    }

}
