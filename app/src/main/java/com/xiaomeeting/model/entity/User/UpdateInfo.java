package com.xiaomeeting.model.entity.User;

/**
 * Created by Blinger on 2018/6/5.
 */

public class UpdateInfo {
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private String info;
    private int status;
    private Object object;
}
