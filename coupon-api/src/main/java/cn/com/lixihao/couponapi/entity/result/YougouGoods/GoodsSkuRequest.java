package cn.com.lixihao.couponapi.entity.result.YougouGoods;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsSkuRequest extends BaseYougou {

    private String categorySn;
    private String goodsName;
    private Integer marketStatus;
    private Double minPrice;
    private Double maxPrice;
}
