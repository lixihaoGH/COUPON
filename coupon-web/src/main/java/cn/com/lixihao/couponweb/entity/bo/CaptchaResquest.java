package cn.com.lixihao.couponweb.entity.bo;

import cn.com.lixihao.couponweb.constant.SysConstants;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/22.
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class CaptchaResquest implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer handle_type;
    public String phone_number;
    public String captcha;
    public String openid;

    public static boolean isInvalid(CaptchaResquest captchaResquest) {
        Integer handle_type = captchaResquest.handle_type;
        if (handle_type == null) {
            return true;
        }
        if (!handle_type.equals(SysConstants.CAPTCHA_HANDLE_SEND) && !handle_type.equals(SysConstants.CAPTCHA_HANDLE_VERIFY)) {
            return true;
        }
        if (handle_type.equals(SysConstants.CAPTCHA_HANDLE_SEND) && StringUtils.isEmpty(captchaResquest.phone_number)) {
            return true;
        }
        if (handle_type.equals(SysConstants.CAPTCHA_HANDLE_VERIFY)
                && StringUtils.isEmpty(captchaResquest.captcha)
                && StringUtils.isEmpty(captchaResquest.phone_number)
                && StringUtils.isEmpty(captchaResquest.openid)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
