package cn.com.lixihao.couponweb.entity.vo;

import cn.com.lixihao.couponweb.entity.CouponStock;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * create by lixihao on 2017/12/25.
 **/

public class ReceivingDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer remaining_count;
    public Integer day_restriction_num;
    public Integer total_restriction_num;
    public Integer release_restriction_status;
    public String phone_number;
    public List<CouponStock> stock_list;

    public ReceivingDetail(Integer remaining_count, String phone_number, List<CouponStock> stock_list) {
        this.remaining_count = remaining_count;
        this.stock_list = stock_list;
        this.phone_number = phone_number;
    }

    public ReceivingDetail(Integer remaining_count, List<CouponStock> stock_list) {
        this.remaining_count = remaining_count;
        this.stock_list = stock_list;
    }


    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
