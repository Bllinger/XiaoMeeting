package com.xiaomeeting.model.executer;

import rx.Scheduler;

/**
 * Created by Blinger on 2018/5/6.
 */

public interface PostExecutionThread {
    public Scheduler getScheduler();
}
