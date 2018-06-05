package com.xiaomeeting.presenter.User;

import android.content.Context;

import com.xiaomeeting.IView.ILoginView;
import com.xiaomeeting.IView.ISplashView;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.model.entity.User.Login.LoginInfo;
import com.xiaomeeting.model.entity.User.Login.UserInfo;
import com.xiaomeeting.model.interfaces.IRespository;
import com.xiaomeeting.model.respository.UserRespository;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.LitePalUtil;
import com.xiaomeeting.utils.LogUtil;

import rx.Subscriber;

/**
 * Created by Blinger on 2018/5/13.
 */

public class LoginPresenter extends Presenter {
    private final String TAG = LogUtil.createTag(this);
    private ILoginView iLoginView;
    private ISplashView iSplashView;
    private IRespository iRespository;
    private LoginSubscriber loginSubscriber;
    private String sNum;
    private String token;
    private int flag;


    public LoginPresenter(Context context, ILoginView iLoginView) {
        super(context);
        this.flag = Constant.LoginFLAG;
        this.iLoginView = iLoginView;
        this.iRespository = UserRespository.getInstance();

    }

    public LoginPresenter(Context context, ISplashView iSplashView) {
        super(context);
        this.flag = Constant.AutoLoginFLAG;
        this.iSplashView = iSplashView;
        this.iRespository = UserRespository.getInstance();
    }

    //账号密码登录
    public void login(String sNum, String password, String imei) {
        this.sNum = sNum;
        try {
            rxJavaExecuter.execute(
                    iRespository.getLoginInfo(sNum, password, imei)
                    , loginSubscriber = new LoginSubscriber()
            );
        } catch (Exception e) {
            LogUtil.e(TAG + "login:", e.toString());
        }
    }

    //自动登录
    public void autoLogin(String token) {
        this.token = token;
        try {
            rxJavaExecuter.execute(
                    iRespository.getLoginAutoInfo(token)
                    , loginSubscriber = new LoginSubscriber()
            );
        } catch (Exception e) {
            LogUtil.e(TAG + "___autoLogin", e.toString());
        }
    }

    //使用token登录，更新用户信息
    public void updateUserInfo() {
        try {
            UserInfo userInfo = new UserInfo();

            userInfo.setToken(loginSubscriber.userInfo.getToken());
            userInfo.setEmail(loginSubscriber.userInfo.getEmail());
            userInfo.setName(loginSubscriber.userInfo.getName());
            userInfo.setGrade(loginSubscriber.userInfo.getGrade());
            userInfo.setCreditScore(loginSubscriber.userInfo.getCreditScore());
            userInfo.setLeaderStatus(loginSubscriber.userInfo.getLeaderStatus());
            userInfo.setMajoy(loginSubscriber.userInfo.getMajoy());
            userInfo.setSchool(loginSubscriber.userInfo.getSchool());
            userInfo.setSingleStatus(loginSubscriber.userInfo.getSingleStatus());
            userInfo.setHeadImageUrl(loginSubscriber.userInfo.getHeadImageUrl());
            int flag = userInfo.updateAll("token = ?", this.token);

            LogUtil.d(TAG + "___updateUserInfo", "" + flag);
        } catch (Exception e) {
            LogUtil.e(TAG + "___updateUserInfo", e.toString());
        }
    }

    //使用password登录，然后将新用户信息装入数据库
    public void setUserInfo() {
        //
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setStudentNum(sNum);
            userInfo.setSex(loginSubscriber.userInfo.getSex());
            userInfo.setToken(loginSubscriber.userInfo.getToken());
            userInfo.setEmail(loginSubscriber.userInfo.getEmail());
            userInfo.setCreditScore(loginSubscriber.userInfo.getCreditScore());
            userInfo.setLeaderStatus(loginSubscriber.userInfo.getLeaderStatus());
            userInfo.setMajoy(loginSubscriber.userInfo.getMajoy());
            userInfo.setSchool(loginSubscriber.userInfo.getSchool());
            userInfo.setSingleStatus(loginSubscriber.userInfo.getSingleStatus());
            userInfo.setUserName(loginSubscriber.userInfo.getUserName());
            userInfo.setTelephone(loginSubscriber.userInfo.getTelephone());
            userInfo.setHeadImageUrl(loginSubscriber.userInfo.getHeadImageUrl());

            if (userInfo.save()) {
                LogUtil.d(TAG + "___setUserInfo:", "success!");
            } else {
                LogUtil.d(TAG + "___setUserInfo:", "failue");
            }
        } catch (Exception e) {
            LogUtil.d(TAG + "___setUserInfo:", "failue" + e.toString());
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LogUtil.d(TAG + "___thread:", Thread.currentThread().getName());
            try {
                switch (flag) {
                    case Constant.LoginFLAG:
                        LitePalUtil.deleteData(UserInfo.class);
                        setUserInfo();
                        break;
                    case Constant.AutoLoginFLAG:
                        //updateUserInfo();
                        break;
                    default:
                        LogUtil.e(TAG + "__onCompleted", "登录入口不对");
                }
            } catch (Exception e) {
                LogUtil.e(TAG + "___runnable", e.toString());
            }
        }
    };


    @Override
    public void destroy() {
        if (loginSubscriber != null) {
            loginSubscriber.unsubscribe();
        }
    }

    private class LoginSubscriber extends Subscriber<LoginInfo> {
        private String msg;
        private int status;

        private UserInfo userInfo;

        public LoginSubscriber() {

        }

        @Override
        public void onCompleted() {
            switch (status) {
                case 1:
                    //启动子线程，储存用户信息
                    Thread thread = new Thread(runnable);
                    thread.start();
                    switch (flag) {
                        case Constant.LoginFLAG:
                            iLoginView.LoginSuccess(msg);
                            break;
                        case Constant.AutoLoginFLAG:
                            iSplashView.AutoLoginSuccess(msg);
                            break;
                    }
                    break;
                default:
                    switch (flag) {
                        case Constant.LoginFLAG:
                            iLoginView.LoginFailure(msg);
                            break;
                        case Constant.AutoLoginFLAG:
                            iSplashView.AutoLoginFailue(msg);
                            break;
                    }

            }
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.e(TAG + "__onError", e.toString());
            if (msg == null) {
                msg = "网络错误";
            }
            switch (flag) {
                case Constant.LoginFLAG:
                    iLoginView.LoginFailure(msg);
                    break;
                case Constant.AutoLoginFLAG:
                    iSplashView.AutoLoginFailue(msg);
                    break;
            }
        }

        @Override
        public void onNext(LoginInfo loginInfo) {
            this.msg = loginInfo.getInfo();
            this.status = loginInfo.getStatus();

            if (flag == Constant.LoginFLAG) {
                this.userInfo = loginInfo.getObject();
                LogUtil.d(TAG + "___userInfo", userInfo.getName() + userInfo.getStudentNum() + userInfo.getGrade() + userInfo.getToken() + flag);
            }

        }


    }
}
