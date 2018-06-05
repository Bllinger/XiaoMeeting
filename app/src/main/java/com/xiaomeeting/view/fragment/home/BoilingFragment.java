package com.xiaomeeting.view.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiaomeeting.R;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.view.fragment.BaseFragment;

/**
 * Created by Blinger on 2018/5/21.
 */

public class BoilingFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_boiling;
    }

    @Override
    protected void initView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {

    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }
}
