package com.xiaomeeting.model.interfaces;

import com.xiaomeeting.model.entity.User.Login.LoginInfo;
import com.xiaomeeting.model.entity.User.UpdateInfo;
import com.xiaomeeting.model.entity.User.register.FirstRegisterInfo;
import com.xiaomeeting.model.entity.User.register.SecondRegisterInfo;

/**
 * Created by Blinger on 2018/5/5.
 */

public interface IRespository {

    public rx.Observable<FirstRegisterInfo> getFirstRegister(final String sNum, final String sName);

    public rx.Observable<SecondRegisterInfo> getSecondRegister(final String cookie, final String phone, final String password);

    public rx.Observable<LoginInfo> getLoginInfo(final String userName, final String password, final String imei);
    public rx.Observable<LoginInfo> getLoginAutoInfo(final String token);

    public rx.Observable<UpdateInfo> getUpdateInfo(final String token, final String headImageUrl, final String userName, final String sex, final String phone, final String email);
}
