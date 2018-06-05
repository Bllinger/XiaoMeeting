package com.xiaomeeting.view.activity;
/**
 *
 *
 //  ┏┓　　　┏┓
 //┏┛┻━━━┛┻┓
 //┃　　　　　　　┃
 //┃　　　━　　　┃
 //┃　┳┛　┗┳　┃
 //┃　　　　　　　┃
 //┃　　　┻　　　┃
 //┃　　　　　　　┃
 //┗━┓　　　┏━┛
 //   ┃　　　┃   神兽保佑
 //   ┃　　　┃   阿弥陀佛
 //   ┃　　　┗━━━┓
 //   ┃　　　　　　　┣┓
 //   ┃　　　　　　　┏┛
 //   ┗┓┓┏━┳┓┏┛
 //     ┃┫┫　┃┫┫
 //     ┗┻┛　┗┻┛
 //
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xiaomeeting.IView.ISplashView;
import com.xiaomeeting.R;
import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.model.entity.User.Login.UserInfo;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.presenter.User.LoginPresenter;
import com.xiaomeeting.utils.LogUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.UUID;

/**
 * Created by Blinger on 2018/5/4.
 */

public class SplashActivity extends BaseActivity implements ISplashView {
    private ImageView imageView;
    private LoginPresenter loginPresenter;
    private final String TAG = LogUtil.createTag(this);
    private String token;
    private int status = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected Presenter getPresenter() {
        return loginPresenter;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        imageView = (ImageView) findViewById(R.id.splash_image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        loginPresenter = new LoginPresenter(this, this);
        //启动子线程自动登录
        Thread thread = new Thread(runnable);
        thread.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);

                                    boolean isFirstStart = sharedPreferences.getBoolean("firstStart", true);
                                    LogUtil.d(TAG + "_isFirstStart", "" + isFirstStart);

                                    if (isFirstStart) {
                                        status = 0;

                                        LogUtil.d(TAG + "_Splash", "进入Intro");
                                        String uuid = UUID.randomUUID().toString();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("firstStart", false);
                                        editor.putString("uuid", uuid);
                                        editor.apply();

                                        LogUtil.d(TAG + "_isFirstStart", "firstStart:" + sharedPreferences.getBoolean("firstStart", true));
                                        LogUtil.d(TAG + "_UUID", "uuid:" + sharedPreferences.getString("uuid", ""));

                                        SQLiteDatabase db = LitePal.getDatabase();

                                        LogUtil.d(TAG + "_database", "Creating Success!");

                                    } else {
                                        status = 1;
                                        LogUtil.d(TAG + "_isFirstStart", "进入Home");
                                    }
                                    StartAcrivity(status);
                                    LogUtil.d(TAG + "___test:", "2");
                                }
                            }
                , 800);

        LogUtil.d(TAG + "___test:", "1");
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List<UserInfo> userInfo = DataSupport.select("token").limit(1).find(UserInfo.class);
            if (!userInfo.isEmpty()) {
                for (UserInfo user : userInfo) {
                    token = user.getToken();
                    LogUtil.d(TAG + "___子线程中", userInfo.get(0).getToken());
                }
                loginPresenter.autoLogin(token);
            } else {
                MyApplication.setLOGINSTATUS(Constant.LoginStatusFAILUE);
            }
        }
    };

    @Override
    public void AutoLoginSuccess(String msg) {
        LogUtil.d(TAG + "___AutoLoginSuccess", msg);
        MyApplication.setLOGINSTATUS(Constant.LoginStatusSUCCESS);
    }

    @Override
    public void AutoLoginFailue(String msg) {
        LogUtil.d(TAG + "___AutoLoginFailue:", msg);
        //MyApplication.setLOGINSTATUS(Constant.LoginStatusFAILUE);
    }

    private void StartAcrivity(int status) {
        status = 1;
        switch (status) {
            case 0:
                Intent intent = new Intent(this, MyAppIntro.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(this, HomeActivity.class);
                startActivity(intent1);
                break;
        }
        this.finish();
    }
}
