package cn.com.lixihao.couponapi.entity.result;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2018/2/26.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class SmsCaptchaResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean return_code;
    private String return_msg;
    private Integer intervalSecond;//秒级单位
    private Long reapplySecond;


    public enum SmsCaptchaMessageEnum {

        SUCCESS(true, "成功"),
        FAIL(false, "短信验证码发送失败"),
        LACK_PARAMS(false, "缺少参数"),
        FREQUENCY_ERROR(false, "验证码发送频繁,请稍后重试"),
        EMPTY_ERROR(false, "验证失败，请先获取验证码"),
        EXPIRY_ERROR(false, "当前验证码已失效，请重新获取"),
        VERIFY_ERROR(false, "验证码错误,请重新输入");

        public boolean code;
        public String name;

        private SmsCaptchaMessageEnum(boolean code, String name) {
            this.code = code;
            this.name = name;
        }

        public boolean getCode() {
            return this.code;
        }

        public void setCode(boolean code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public SmsCaptchaResponse(SmsCaptchaMessageEnum smsMessageEnum) {
        this.return_code = smsMessageEnum.getCode();
        this.return_msg = smsMessageEnum.getName();
    }

    public SmsCaptchaResponse() {
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
