package com.xiaomeeting.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.QiNiu.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Blinger on 2018/5/6.
 */

public abstract class BaseActivity extends Activity {
    protected Context context;
    protected Presenter presenter;
    protected Unbinder unbind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        StatusBarUtil.setTransparent(BaseActivity.this);
        context = MyApplication.getContext();

        unbind = ButterKnife.bind(this);

        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //

        if (presenter == null && getPresenter() != null){
            presenter = getPresenter();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbind.unbind();

        if (presenter != null){
            presenter.destroy();
        }
    }

    public Context getContext(){
        return context;
    }

    protected abstract int getLayoutId();
    protected abstract Presenter getPresenter();
    protected abstract void init(@Nullable Bundle savedInstanceState);
}
