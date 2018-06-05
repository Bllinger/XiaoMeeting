package com.xiaomeeting.model.entity.User.register;

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
    private Object object;

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

    public Object getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
