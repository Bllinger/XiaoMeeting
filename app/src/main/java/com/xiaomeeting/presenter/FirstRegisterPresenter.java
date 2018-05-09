package com.xiaomeeting.presenter;

import android.content.Context;

import com.xiaomeeting.IView.IFirstRegisterView;
import com.xiaomeeting.model.entity.register.FirstRegisterInfo;
import com.xiaomeeting.model.interfaces.IRespository;
import com.xiaomeeting.model.respository.UserRespository;

import rx.Subscriber;

/**
 * Created by Blinger on 2018/5/6.
 */

public class FirstRegisterPresenter extends Presenter {
    private IFirstRegisterView iFirstRegisterView;
    private IRespository iRespository;


    public FirstRegisterPresenter(Context context, IFirstRegisterView iFirstRegisterView){
        super(context);

        this.iFirstRegisterView = iFirstRegisterView;
        this.iRespository = UserRespository.getInstance();
    }
    @Override
    public void destroy() {
        if (firstRegisterSubscriber != null){
            firstRegisterSubscriber.unsubscribe();
        }
    }

    public void FirstRegister(String sNum,String sName){
        rxJavaExecuter.execute(
                iRespository.getFirstRegister(sNum,sName),
                firstRegisterSubscriber = new FirstRegisterSubscriber()
        );
    }
    private FirstRegisterSubscriber firstRegisterSubscriber;
    private class FirstRegisterSubscriber extends Subscriber<FirstRegisterInfo>{
        private int status;
        private String info;
        @Override
        public void onCompleted() {
            switch (status){
                case 1:
                    iFirstRegisterView.onFirstRegisterSuccess(info,status);
                    break;
                default:
                    iFirstRegisterView.onFirstRegisterFailure(info,status);
            }
        }

        @Override
        public void onError(Throwable e) {
            iFirstRegisterView.onFirstRegisterFailure(info,status);
        }

        @Override
        public void onNext(FirstRegisterInfo firstRegisterInfo) {
            status = firstRegisterInfo.getStatus();
            info = firstRegisterInfo.getInfo();
        }
    }
}
