package com.xiaomeeting.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xiaomeeting.presenter.Presenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Blinger on 2018/5/6.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context context;
    protected Presenter presenter;
    protected Unbinder unbind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        context = this;

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
