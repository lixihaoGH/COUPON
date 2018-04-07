package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by haoweige on 2017/8/29.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class BaseCondition {

    private Integer page_index;
    private Integer page_size;

    public Integer getPage_index() {
        if (this.page_index == null) {
            this.page_index = 0;
        }
        if (this.page_size == null) {
            this.page_size = 20;
        }
        return this.page_index * this.page_size;
    }

    public void setPage_index(Integer page_index) {
        this.page_index = page_index - 1 < 0 ? 0 : page_index - 1;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
}
