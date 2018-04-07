package cn.com.lixihao.couponweb.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/21.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class UnifiedResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public String return_code;
    public String return_message;

    public UnifiedResponse(String return_code, String return_message) {
        this.return_code = return_code;
        this.return_message = return_message;
    }

    public UnifiedResponse() {
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
