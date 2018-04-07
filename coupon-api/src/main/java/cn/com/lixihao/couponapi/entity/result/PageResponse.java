package cn.com.lixihao.couponapi.entity.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class PageResponse extends UnifiedResponse {

    private static final long serialVersionUID = 1L;

    private Integer total;
    private List rows;

    public void setRows(List rows) {
        this.rows = rows;
        if (this.rows.get(0) instanceof Comparable) {
            Collections.reverse(this.rows);
        }
    }

    public PageResponse(){
        super.setReturn_code(UnifiedResponse.SUCCESS);
        super.setReturn_value("ok");
    }
    public PageResponse(int return_code, String return_value) {
        super(return_code, return_value);
    }
}
