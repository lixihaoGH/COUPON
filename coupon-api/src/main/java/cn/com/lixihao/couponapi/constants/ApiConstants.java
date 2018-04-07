package cn.com.lixihao.couponapi.constants;

import java.util.ResourceBundle;

public class ApiConstants {

    private static final ResourceBundle R = ResourceBundle.getBundle("conf/api");

    public static final String YOUGOU_URL = R.getString("yougou.host");
    public static final String COUPON_API_URL = R.getString("coupon.api.host");
    public static final String COUPON_WEB_URL = R.getString("coupon.web.host");
    public static final String COUPON_UPLOAD_PATH = R.getString("coupon.upload.path");
    public static final String COUPON_WEBACCESS_PATH = R.getString("coupon.webaccess.path");


    public static void main(String[] args) {
        System.out.println("COUPON_API_URL=>" + COUPON_API_URL);
        System.out.println("COUPON_WEB_URL=>" + COUPON_WEB_URL);
        System.out.println("YOUGOU_URL=>" + YOUGOU_URL);
        System.out.println("COUPON_UPLOAD_PATH=>" + COUPON_UPLOAD_PATH);
        System.out.println("COUPON_WEBACCESS_PATH=>" + COUPON_WEBACCESS_PATH);

    }
}
