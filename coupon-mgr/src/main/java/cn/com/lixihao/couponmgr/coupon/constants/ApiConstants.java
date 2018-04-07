package cn.com.lixihao.couponmgr.coupon.constants;

import java.util.ResourceBundle;

public class ApiConstants {

    private static final ResourceBundle R = ResourceBundle.getBundle("conf/api");

    public static final String COUPON_UPLOAD_PATH = R.getString("coupon.upload.path");
    public static final String COUPON_WEB_ACCESS_PATH = R.getString("coupon.webaccess.path");

    public static void main(String[] args) {
        System.out.println("COUPON_UPLOAD_PATH=>" + COUPON_UPLOAD_PATH);
        System.out.println("COUPON_WEB_ACCESS_PATH=>" + COUPON_WEB_ACCESS_PATH);
    }
}