package cn.com.lixihao.couponweb.entity.bo;

import cn.com.lixihao.couponweb.entity.UnifiedResponse;
import cn.com.lixihao.couponweb.entity.vo.QueryCouponDetail;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2018/1/5.
 **/
@EqualsAndHashCode(callSuper = true)
public class QueryCouponResponse extends UnifiedResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public QueryCouponDetail coupon_detail;
    public Integer page_index;
    public Integer page_size;

    public QueryCouponResponse(String return_code, String return_message, Integer page_index, Integer page_size, QueryCouponDetail coupon_detail) {
        super(return_code, return_message);
        this.coupon_detail = coupon_detail;
        this.page_index = page_index;
        this.page_size = page_size;
    }

    public QueryCouponResponse(String return_code, String return_message) {
        super(return_code, return_message);
    }

    public QueryCouponResponse(String return_code, String return_message, QueryCouponDetail coupon_detail) {
        super(return_code, return_message);
        this.coupon_detail = coupon_detail;
    }

}
