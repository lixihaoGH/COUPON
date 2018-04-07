package cn.com.lixihao.couponapi.entity.result.YougouGoods;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class  BaseYougou {

    //页面索引
    private Integer pageIndex;
    //页面大小
    private Integer pageSize;

    public Integer getPageIndex() {
        if (this.pageIndex == null) {
            this.pageIndex = 0;
        }
        if (this.pageSize == null) {
            this.pageSize = 1;
        }
        return this.pageIndex * this.pageSize;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex - 1 < 0 ? 0 : pageIndex - 1;
    }
}
