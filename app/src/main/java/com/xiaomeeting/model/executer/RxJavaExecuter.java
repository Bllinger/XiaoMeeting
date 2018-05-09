package com.xiaomeeting.model.executer;


/**
 * Created by Blinger on 2018/5/5.
 */

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class RxJavaExecuter {

    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    public RxJavaExecuter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    public void execute(Observable observable, Subscriber subscriber) {
        this.subscription = observable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(subscriber);
    }
}