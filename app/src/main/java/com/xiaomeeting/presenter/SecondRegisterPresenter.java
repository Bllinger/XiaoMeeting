package com.xiaomeeting.presenter;

import android.content.Context;

import com.xiaomeeting.IView.ISecondRegisterView;
import com.xiaomeeting.model.entity.register.SecondRegisterInfo;
import com.xiaomeeting.model.interfaces.IRespository;
import com.xiaomeeting.model.respository.UserRespository;
import com.xiaomeeting.utils.Md5;

import rx.Subscriber;

/**
 * Created by Blinger on 2018/5/8.
 */

public class SecondRegisterPresenter extends Presenter{
    private ISecondRegisterView iSecondRegisterView;
    private IRespository iRespository;

    public SecondRegisterPresenter(Context context,ISecondRegisterView iSecondRegisterView) {
        super(context);

        this.iSecondRegisterView = iSecondRegisterView;
        this.iRespository = UserRespository.getInstance();
    }

    public void SecondRegister(String phone,String password){
        rxJavaExecuter.execute(
                iRespository.getSecondRegister(phone,password),
                secondRegisterSubscriber = new SecondRegisterSubscriber()
        );
    }


    @Override
    public void destroy() {
        if (secondRegisterSubscriber != null)
            secondRegisterSubscriber.unsubscribe();
    }

    public String md5(String password, String slat){
        return Md5.md5(password,slat);
    }

    private SecondRegisterSubscriber secondRegisterSubscriber;
    private class SecondRegisterSubscriber extends Subscriber<SecondRegisterInfo>{
        private int status;
        private String info;
        @Override
        public void onCompleted() {
            switch (status){
                case 1:
                    iSecondRegisterView.onRegisterSuccess(info);
                default:
                    iSecondRegisterView.onRegisterFailure(info);
            }
        }

        @Override
        public void onError(Throwable e) {
            iSecondRegisterView.onRegisterFailure(info);
        }

        @Override
        public void onNext(SecondRegisterInfo secondRegisterInfo) {
            this.status = secondRegisterInfo.getStatus();
            this.info = secondRegisterInfo.getInfo();
        }
    }
}
