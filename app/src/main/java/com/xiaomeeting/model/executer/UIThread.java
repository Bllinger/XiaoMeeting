package com.xiaomeeting.model.executer;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Blinger on 2018/5/6.
 */

public class UIThread implements PostExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

    private UIThread(){
        //
    }
    public static UIThread instance(){
        return Holder.INSTANCE;
    }

    private final static class Holder{
        private static final UIThread INSTANCE = new UIThread();
    }
}
