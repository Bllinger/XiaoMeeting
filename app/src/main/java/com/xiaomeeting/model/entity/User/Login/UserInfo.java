package com.xiaomeeting.model.entity.User.Login;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Blinger on 2018/5/15.
 */

public class UserInfo extends DataSupport {
    private String majoy;
    private String school;
    private String email;
    private String sex;
    private String name;
    private String grade;
    private String password;
    private int leaderStatus;
    private int creditScore;
    private int singleStatus;
    private String telephone;
    @Column(unique = true)
    private String token;
    private String studentNum;
    private String userName;
    private String headImageUrl;

    public String getMajoy() {
        return majoy;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String phone) {
        this.telephone = phone;
    }

    public void setMajoy(String majoy) {
        this.majoy = majoy;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLeaderStatus() {
        return leaderStatus;
    }

    public void setLeaderStatus(int leaderStatus) {
        this.leaderStatus = leaderStatus;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getSingleStatus() {
        return singleStatus;
    }

    public void setSingleStatus(int singleStatus) {
        this.singleStatus = singleStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
