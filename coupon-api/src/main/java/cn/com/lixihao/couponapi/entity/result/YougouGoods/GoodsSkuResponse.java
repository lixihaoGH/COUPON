package cn.com.lixihao.couponapi.entity.result.YougouGoods;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsSkuResponse{

    private String goodskuSn;
    private String goodsName;
    private String specItemValues;
    private Double price;
    private Double marketPrice;
}
