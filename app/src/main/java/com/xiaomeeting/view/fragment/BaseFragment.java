package com.xiaomeeting.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaomeeting.presenter.Presenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Blinger on 2018/5/7.
 */

public abstract class BaseFragment extends Fragment{

    protected Presenter presenter;
    private Unbinder butterKnife;
    private Activity activity;
    //protected Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(),container,false);

        butterKnife = ButterKnife.bind(this,view);
        //this.context = getActivity();
        initView(inflater,container,savedInstanceState);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (presenter == null && getPresenter() != null){
            presenter = getPresenter();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        butterKnife.unbind();

        if (presenter != null)
            presenter.destroy();

        //activity.finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    protected abstract int getLayoutId();
    protected abstract void initView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState);
    protected abstract Presenter getPresenter();
}
