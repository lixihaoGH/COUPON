package cn.com.lixihao.couponapi.helper;

import cn.com.lixihao.couponapi.constants.SysConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

public class GenerateIdentifier {

    /**
     * 1---------------优购商品
     * 2
     * 4
     * 9
     * 17
     */

    private static final String STOCK_ID_PREFIX = "530";    //S的ASCII码补0
    private static final String RELEASE_ID_PREFIX = "7200";  //r的ASCII码补0

    public static String generateCouponStockId(String selectCategory) {
        String dateTime = DateTime.now().toString("yyyyMMddhhmm");
        String category = sumFromString(selectCategory);
        String result = STOCK_ID_PREFIX + category + System.currentTimeMillis() + dateTime;
        return result;
    }

    public static String generateReleaseId() {
        String dateTime = DateTime.now().toString("yyyyMMddhhmmss");
        String result = RELEASE_ID_PREFIX + System.currentTimeMillis() + 0 + dateTime;
        return result;
    }

    public static String generateCouponId(String phone_number) {
        String coupon_id_str = phone_number.substring(phone_number.length() - 11, phone_number.length()) + DateTime.now().toString(SysConstants.DATE_FORMAT_ID) + StringUtils.rightPad((int) (Math.random() * 1000000) + "", 6, "0");
        return DigestUtils.md5Hex(coupon_id_str).toUpperCase();
    }

    private static String sumFromString(String str) {
        String[] strs = str.split(",");
        int sum = 0;
        for (String num : strs) {
            int n = Integer.parseInt(num);
            sum += n;
        }
        if (sum < 10) {
            return "00" + sum + "0";
        }
        return "0" + sum + "0";
    }

}
