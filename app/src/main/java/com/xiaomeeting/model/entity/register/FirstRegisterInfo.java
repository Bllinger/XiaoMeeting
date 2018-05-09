package com.xiaomeeting.model.entity.register;

/**
 * Created by Blinger on 2018/5/5.
 */

public class FirstRegisterInfo {
    /**
     * status : 0
     * info : 成功
     * Object : body
     */
    //注册第一步
    private int status;
    private String info;
    private String Object;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getObject() {
        return Object;
    }

    public void setObject(String Object) {
        this.Object = Object;
    }
}
