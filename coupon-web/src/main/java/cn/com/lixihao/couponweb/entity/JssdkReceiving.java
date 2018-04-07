package cn.com.lixihao.couponweb.entity;

import java.io.Serializable;

/**
 * create by lixihao on 2017/12/26.
 **/

public class JssdkReceiving implements Serializable{

    private static final long serialVersionUID = 1L;

    public String phone_number;
    public String user_id;
    public String openid;
    public String release_id;
    public Integer device_type;

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRelease_id() {
        return release_id;
    }

    public void setRelease_id(String release_id) {
        this.release_id = release_id;
    }
}
