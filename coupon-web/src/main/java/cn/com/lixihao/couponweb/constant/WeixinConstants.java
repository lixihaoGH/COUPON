package cn.com.lixihao.couponweb.constant;

import java.util.ResourceBundle;


public class WeixinConstants {

    private static final ResourceBundle R = ResourceBundle.getBundle("conf/weixin");

    public static final String HIVEVIEW_APP_ID = R.getString("hiveview.appid");
    public static final String TESTCASE_APP_ID = R.getString("testcase.appid");

    public static void main(String[] args) {
        System.out.println("HIVEVIEW_APP_ID=>" + HIVEVIEW_APP_ID);
        System.out.println("TESTCASE_APP_ID=>" + TESTCASE_APP_ID);

    }
}
