package cn.com.lixihao.couponapi.entity.result.YougouGoods;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsCategoryRequest extends BaseYougou{
    //分类id
    private String categorySn;
    //父级分类id
    private String parentSn;
    //名称
    private String name;
    //分类名称
    private String categoryName;
}
