package cn.com.lixihao.couponapi.entity.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class UnifiedResponse implements Serializable{

    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    private int return_code;
    private String return_value;

    public UnifiedResponse(){}

    public UnifiedResponse(int return_code, String return_value) {
        this.return_code = return_code;
        this.return_value = return_value;
    }
}
