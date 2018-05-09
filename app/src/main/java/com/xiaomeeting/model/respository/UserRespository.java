package com.xiaomeeting.model.respository;

import com.google.gson.Gson;
import com.xiaomeeting.model.entity.Login.LoginInfo;
import com.xiaomeeting.model.entity.register.FirstRegisterInfo;
import com.xiaomeeting.model.entity.register.SecondRegisterInfo;
import com.xiaomeeting.model.interfaces.IRespository;
import com.xiaomeeting.model.interfaces.IRetrofitService;
import com.xiaomeeting.model.net.RetrofitService;

/**
 * Created by Blinger on 2018/5/5.
 */

public class UserRespository implements IRespository{

    private static UserRespository Instance = null;

    public static UserRespository getInstance(){
        if (Instance == null){
            Instance = new UserRespository();
        }

        return Instance;
    }

    private Gson gson;

    private UserRespository(){
        gson = new Gson();
    }
    @Override
    public rx.Observable<FirstRegisterInfo> getFirstRegister(final String sNum, final String sName) {
        IRetrofitService iRetrofitService = RetrofitService.getInstance().createApi(IRetrofitService.class);

        return iRetrofitService.getFirstRegister(sNum,sName);
    }

    @Override
    public rx.Observable<SecondRegisterInfo> getSecondRegister(final String phone,final String password) {
        IRetrofitService iRetrofitService = RetrofitService.getInstance().createApi(IRetrofitService.class);
        return iRetrofitService.getSecondRegister(phone,password);
    }

    @Override
    public rx.Observable<LoginInfo> getLoginInfo(final String userName, final String password) {
        IRetrofitService iRetrofitService = RetrofitService.getInstance().createApi(IRetrofitService.class);
        return iRetrofitService.getLoginInfo(userName,password);
    }

    @Override
    public rx.Observable<LoginInfo> getLoginAutoInfo(final String token) {
        IRetrofitService iRetrofitService = RetrofitService.getInstance().createApi(IRetrofitService.class);

        return iRetrofitService.getLoginAutoInfo(token);
    }
}
