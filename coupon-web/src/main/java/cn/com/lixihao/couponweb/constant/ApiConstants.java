package cn.com.lixihao.couponweb.constant;

import java.util.ResourceBundle;


public class ApiConstants {

    private static final ResourceBundle R = ResourceBundle.getBundle("conf/api");

    public static final String COUPON_API = R.getString("coupon.api");


    public static void main(String[] args) {
        System.out.println("COUPON_API=>" + COUPON_API);
    }
}
