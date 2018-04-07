package cn.com.lixihao.couponweb.entity;

/**
 * create by lixihao on 2017/12/21.
 **/

public enum UnifiedMessageEnum {
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),//
    RECEIVING_SUCCESS("SUCCESS", "领取成功"),
    RECEIVING_FAIL_TOTAL("FAIL_TOTAL", "领取次数已达限制次"),
    RECEIVING_FAIL_RELEASE_EXPIRED("FAIL_RELEASE_EXPIRED", "非红包领取时间，领取失败"),
    RECEIVING_FAIL_RELEASE_EMPTY("FAIL_RELEASE_EMPTY", "领取失败,红包已被别人领光了"),
    RECEIVING_FAIL_POST_DATA_EMPTY("FAIL_POST_DATA_EMPTY", "领取红包走丢了"),
    SYSTEMERROR("SYSTEMERROR", "系统错误"), //
    POST_DATA_EMPTY("POST_DATA_EMPTY", "请求数据为空"), //
    LACK_PARAMS("LACK_PARAMS", "缺少参数"), //
    PARAM_ERROR("PARAM_ERROR", "参数错误"),
    VERIFY_ERROR("VERIFY_ERROR", "验证失败"),
    SIGN_ERROR("SIGN_ERROR", "签名错误"); //


    private String code;
    private String name;

    private UnifiedMessageEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
