package cn.com.lixihao.couponapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * create by lixihao on 2018/3/13.
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String password;
}
