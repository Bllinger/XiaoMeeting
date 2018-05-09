package com.xiaomeeting.presenter;

import android.content.Context;

import com.xiaomeeting.model.executer.JobExecutor;
import com.xiaomeeting.model.executer.RxJavaExecuter;
import com.xiaomeeting.model.executer.UIThread;

/**
 * Created by Blinger on 2018/5/5.
 */

public abstract class Presenter {

    protected Context context;
    protected RxJavaExecuter rxJavaExecuter;

    public Presenter(Context context){
        this.context = context;
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());

    }

    public abstract void destroy();
}
