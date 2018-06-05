package com.xiaomeeting.app;

import android.app.Application;
import android.content.Context;

import com.xiaomeeting.constants.Constant;

import org.litepal.LitePal;

/**
 * Created by Blinger on 2018/5/8.
 */

public class MyApplication extends Application {
    public static Context context;
    private static int LOGINSTATUS = Constant.LoginStatusFAILUE;
    private final String TAG = "MyApplication";

    public static int getLOGINSTATUS() {
        return LOGINSTATUS;
    }

    public static void setLOGINSTATUS(int LOGINSTATUS) {
        MyApplication.LOGINSTATUS = LOGINSTATUS;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public void init() {
        //
    }

    public static Context getContext() {
        return context;
    }
}
