package com.xiaomeeting.presenter.User;

import android.content.Context;

import com.xiaomeeting.IView.ISecondRegisterView;
import com.xiaomeeting.model.entity.User.register.SecondRegisterInfo;
import com.xiaomeeting.model.interfaces.IRespository;
import com.xiaomeeting.model.respository.UserRespository;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.LogUtil;

import rx.Subscriber;

/**
 * Created by Blinger on 2018/5/8.
 */

public class SecondRegisterPresenter extends Presenter {
    private ISecondRegisterView iSecondRegisterView;
    private IRespository iRespository;
    private final String TAG = LogUtil.createTag(this);

    public SecondRegisterPresenter(Context context,ISecondRegisterView iSecondRegisterView) {
        super(context);

        this.iSecondRegisterView = iSecondRegisterView;
        this.iRespository = UserRespository.getInstance();
    }

    public void SecondRegister(String cookie, String phone, String password) {
        try {
            rxJavaExecuter.execute(
                    iRespository.getSecondRegister(cookie, phone, password),
                    secondRegisterSubscriber = new SecondRegisterSubscriber()
            );
        } catch (Exception e) {
            LogUtil.e(TAG + "___SecondRegister", e.toString());
        }

    }


    @Override
    public void destroy() {
        if (secondRegisterSubscriber != null)
            secondRegisterSubscriber.unsubscribe();
    }

    /*public String md5(String password, String slat){
        return Md5.md5(password,slat);
    }*/

    private SecondRegisterSubscriber secondRegisterSubscriber;
    private class SecondRegisterSubscriber extends Subscriber<SecondRegisterInfo>{
        private int status;
        private String info;
        @Override
        public void onCompleted() {
            switch (status){
                case 1:
                    iSecondRegisterView.onRegisterSuccess(info);
                    LogUtil.d(TAG + "___onCompleted_success", info);
                    break;
                default:
                    iSecondRegisterView.onRegisterFailure(info);
                    LogUtil.d(TAG + "___onCompleted_failue", info);
            }
        }

        @Override
        public void onError(Throwable e) {
            if (info == null) {
                info = "网络错误";
            }

            LogUtil.d(TAG + "___onError", e.toString());
            iSecondRegisterView.onRegisterFailure(info);
        }

        @Override
        public void onNext(SecondRegisterInfo secondRegisterInfo) {
            this.status = secondRegisterInfo.getStatus();
            this.info = secondRegisterInfo.getInfo();
        }
    }
}
