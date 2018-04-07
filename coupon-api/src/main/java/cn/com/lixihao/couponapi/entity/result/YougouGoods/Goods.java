package cn.com.lixihao.couponapi.entity.result.YougouGoods;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Goods {
    private String categoryTreePath;
    private String categoryTreeName;
    private String goodsSn;
    private String name;
    private double marketPrice;
    private double price;
}
