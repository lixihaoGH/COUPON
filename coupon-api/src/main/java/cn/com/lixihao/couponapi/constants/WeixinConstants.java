package cn.com.lixihao.couponapi.constants;

import java.util.ResourceBundle;

/**
 * Created by haoweige on 2017/9/8.
 */
public class WeixinConstants {

    private static final ResourceBundle R = ResourceBundle.getBundle("conf/weixin");

    public static final String TESTCASE_APP_ID = R.getString("testcase.appid");
    public static final String TESTCASE_SECRET = R.getString("testcase.secret");
    public static final String HIVEVIEW_APP_ID = R.getString("hiveview.appid");
    public static final String HIVEVIEW_SECRET = R.getString("hiveview.secret");

    public static void main(String[] args) {

        System.out.println("TESTCASE_APP_ID=>" + TESTCASE_APP_ID);
        System.out.println("TESTCASE_SECRET=>" + TESTCASE_SECRET);
        System.out.println("HIVEVIEW_APP_ID=>" + HIVEVIEW_APP_ID);
        System.out.println("HIVEVIEW_SECRET=>" + HIVEVIEW_SECRET);

    }
}
