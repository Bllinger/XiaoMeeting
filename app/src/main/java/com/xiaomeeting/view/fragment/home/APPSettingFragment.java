package com.xiaomeeting.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaomeeting.R;
import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.model.entity.User.Login.UserInfo;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.LitePalUtil;
import com.xiaomeeting.view.activity.LoginActivity;
import com.xiaomeeting.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Blinger on 2018/6/5.
 */

public class APPSettingFragment extends BaseFragment {
    @BindView(R.id.logout)
    TextView logout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_app_setting;
    }

    @Override
    protected void initView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {


    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }


    @OnClick(R.id.logout)
    public void onViewClicked() {
        MyApplication.setLOGINSTATUS(Constant.LoginStatusFAILUE);
        LitePalUtil.deleteData(UserInfo.class);

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

        getActivity().finish();
    }
}
