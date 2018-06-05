package com.xiaomeeting.model.interfaces;


import com.xiaomeeting.model.entity.User.Login.LoginInfo;
import com.xiaomeeting.model.entity.User.UpdateInfo;
import com.xiaomeeting.model.entity.User.register.FirstRegisterInfo;
import com.xiaomeeting.model.entity.User.register.SecondRegisterInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Blinger on 2018/5/5.
 */

public interface IRetrofitService {
    //注册第一步
    @FormUrlEncoded
    @POST("/xiaomeeting/register1")
    rx.Observable<FirstRegisterInfo> getFirstRegister(@Field("studentNum") String sNum, @Field("idNum") String sName);
    //注册第二步
    @FormUrlEncoded
    @POST("/xiaomeeting/register2")
    rx.Observable<SecondRegisterInfo> getSecondRegister(@Header("cookie") String cookie, @Field("phone") String phone, @Field("password") String password);
    //登录
    @FormUrlEncoded
    @POST("/xiaomeeting/login")
    rx.Observable<LoginInfo> getLoginInfo(@Field("studentNum") String userName, @Field("password") String password, @Field("imei") String imei);

    //第二次启动客户端自动登录
    @FormUrlEncoded
    @POST("/xiaomeeting/autoLogin")
    rx.Observable<LoginInfo> getLoginAutoInfo(@Field("token") String token);

    //更新个人信息
    @FormUrlEncoded
    @POST("/xiaomeeting/updateInfo")
    rx.Observable<UpdateInfo> getUpdateInfo(@Field("token") String token, @Field("headImageUrl") String headImageUrl, @Field("userName") String userName, @Field("sex") String sex, @Field("phone") String phone, @Field("email") String email);
}
