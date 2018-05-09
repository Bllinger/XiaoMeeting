package com.xiaomeeting.model.interfaces;


import com.xiaomeeting.model.entity.Login.LoginInfo;
import com.xiaomeeting.model.entity.register.FirstRegisterInfo;
import com.xiaomeeting.model.entity.register.SecondRegisterInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Blinger on 2018/5/5.
 */

public interface IRetrofitService {
    //注册第一步
    @FormUrlEncoded
    @POST("/xiaomeeting/register1")
    rx.Observable<FirstRegisterInfo> getFirstRegister(@Field("studentNum") String sNum, @Field("name") String sName);
    //注册第二步
    @FormUrlEncoded
    @POST("/xiaomeeting/register2")
    rx.Observable<SecondRegisterInfo> getSecondRegister(@Field("phone") String phone,@Field("password") String password);
    //登录
    @FormUrlEncoded
    @POST("/xiaomeeting/login")
    rx.Observable<LoginInfo> getLoginInfo(@Field("user_num") String userName,@Field("password") String password);

    //第二次启动客户端自动登录
    @FormUrlEncoded
    @POST("/xiaomeeting/login_auto")
    rx.Observable<LoginInfo> getLoginAutoInfo(@Field("token") String token);
}
