package cn.com.lixihao.couponweb.entity.bo;

import cn.com.lixihao.couponweb.entity.UnifiedResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2018/2/26.
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CaptchaResponse extends UnifiedResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer intervalSecond;//秒级单位\
    private Integer reapplySecond;

    public CaptchaResponse(String return_code, String return_message) {
        super(return_code, return_message);
    }

    public CaptchaResponse() {
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
