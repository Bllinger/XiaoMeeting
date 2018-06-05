package com.xiaomeeting.model.entity.User.Login;

/**
 * Created by Blinger on 2018/5/5.
 */

public class LoginInfo {
    /**
     * sex : 男
     * majoy : cs
     * school : cqupt
     * email : xxx@mail.com
     * leaderStatus : 0
     * creaditScore : 60
     * singleStatus : 0
     */

    /*private String sex;
    private String majoy;
    private String school;
    private String email;
    private int leaderStatus;
    private int creditScore;
    private int singleStatus;
    private String token;*/
    private String info;
    private int status;
    //存放UserInfo
    private UserInfo object;

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

    public UserInfo getObject() {
        return object;
    }

    public void setObject(UserInfo object) {
        this.object = object;
    }
}
