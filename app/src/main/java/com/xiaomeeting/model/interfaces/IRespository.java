package com.xiaomeeting.model.interfaces;

import com.xiaomeeting.model.entity.Login.LoginInfo;
import com.xiaomeeting.model.entity.register.FirstRegisterInfo;
import com.xiaomeeting.model.entity.register.SecondRegisterInfo;

/**
 * Created by Blinger on 2018/5/5.
 */

public interface IRespository {

    public rx.Observable<FirstRegisterInfo> getFirstRegister(final String sNum, final String sName);
    public rx.Observable<SecondRegisterInfo> getSecondRegister(final String phone,final String password);
    public rx.Observable<LoginInfo> getLoginInfo(final String userName, final String password);
    public rx.Observable<LoginInfo> getLoginAutoInfo(final String token);
}
