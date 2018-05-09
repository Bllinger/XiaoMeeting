package com.xiaomeeting.model.entity.Login;

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

    private String sex;
    private String majoy;
    private String school;
    private String email;
    private int leaderStatus;
    private int creditScore;
    private int singleStatus;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMajoy() {
        return majoy;
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

    public void setCreditScore(int creaditScore) {
        this.creditScore = creaditScore;
    }

    public int getSingleStatus() {
        return singleStatus;
    }

    public void setSingleStatus(int singleStatus) {
        this.singleStatus = singleStatus;
    }


    /*private String sex;
    private String major;
    private String school;
    private String singleStatus;
    private String email;
    private String leaderStatus;
    private String creditScore;

    public LoginInfo(){
        //
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLeaderStatus() {
        return leaderStatus;
    }

    public void setLeaderStatus(String leaderStatus) {
        this.leaderStatus = leaderStatus;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSingleStatus() {
        return singleStatus;
    }

    public void setSingleStatus(String singleStatus) {
        this.singleStatus = singleStatus;
    }
*/
}
