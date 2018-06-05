package com.xiaomeeting.presenter.User;

import android.content.Context;

import com.xiaomeeting.IView.IFirstRegisterView;
import com.xiaomeeting.model.entity.User.register.FirstRegisterInfo;
import com.xiaomeeting.model.interfaces.IRespository;
import com.xiaomeeting.model.respository.UserRespository;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.LogUtil;

import rx.Subscriber;

/**
 * Created by Blinger on 2018/5/6.
 */

public class FirstRegisterPresenter extends Presenter {
    private IFirstRegisterView iFirstRegisterView;
    private IRespository iRespository;
    private final String TAG = LogUtil.createTag(this);


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
        try {
            rxJavaExecuter.execute(
                    iRespository.getFirstRegister(sNum, sName),
                    firstRegisterSubscriber = new FirstRegisterSubscriber()
            );
        } catch (Exception e) {
            LogUtil.e(TAG + "___FirstRegister：", e.toString());
        }

    }
    private FirstRegisterSubscriber firstRegisterSubscriber;
    private class FirstRegisterSubscriber extends Subscriber<FirstRegisterInfo>{
        private int status;
        private String info;
        @Override
        public void onCompleted() {
            LogUtil.d(TAG + "___onCompleted", "___请求完成");
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
            LogUtil.d(TAG + "___onError", e.toString());
            if (info == null) {
                info = "网络错误";
            }
            iFirstRegisterView.onFirstRegisterFailure(info,status);
        }

        @Override
        public void onNext(FirstRegisterInfo firstRegisterInfo) {
            status = firstRegisterInfo.getStatus();
            info = firstRegisterInfo.getInfo();
        }
    }
}
