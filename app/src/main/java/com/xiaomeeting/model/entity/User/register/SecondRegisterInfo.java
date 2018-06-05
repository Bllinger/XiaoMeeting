package com.xiaomeeting.model.entity.User.register;

/**
 * Created by Blinger on 2018/5/5.
 */

public class SecondRegisterInfo {


    /**
     * status : 1
     * info : ok
     * object : student
     */

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
