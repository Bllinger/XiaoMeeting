package com.xiaomeeting.app;

import android.app.Application;

/**
 * Created by Blinger on 2018/5/8.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static MyApplication getInstance(){
        if (mInstance == null){
            mInstance = new MyApplication();
        }

        return mInstance;
    }
}
