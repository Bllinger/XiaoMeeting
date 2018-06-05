package com.xiaomeeting.view.fragment.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaomeeting.R;
import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.model.entity.User.Login.UserInfo;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.LogUtil;
import com.xiaomeeting.view.activity.LoginActivity;
import com.xiaomeeting.view.activity.UserInfoSettingActivity;
import com.xiaomeeting.view.fragment.BaseFragment;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Blinger on 2018/5/21.
 */

public class UserFragment extends BaseFragment {
    private final String TAG = LogUtil.createTag(MyApplication.getContext());

    @BindView(R.id.user_button_line)
    LinearLayout userButtonLine;
    @BindView(R.id.myartile_button_line)
    LinearLayout myartileButtonLine;
    @BindView(R.id.label_button_line)
    LinearLayout labelButtonLine;
    @BindView(R.id.history_button_line)
    LinearLayout historyButtonLine;
    @BindView(R.id.feedback_button_line)
    LinearLayout feedbackButtonLine;
    @BindView(R.id.setting_button_line)
    LinearLayout settingButtonLine;
    @BindView(R.id.head_image_home)
    CircleImageView headImageHome;
    @BindView(R.id.username)
    TextView username;
    private APPSettingFragment appSettingFragment;

    private UserInfo userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        appSettingFragment = new APPSettingFragment();
        //已登录状态更新ui
        if (MyApplication.getLOGINSTATUS() == 1) {
            userInfo = DataSupport.findFirst(UserInfo.class);
            username.setText(userInfo.getUserName());
            if (userInfo.getHeadImageUrl() != null) {
                //设置头像
                Picasso.with(MyApplication.getContext())
                        .load(userInfo.getHeadImageUrl())
                        .into(headImageHome);
            }

        }

    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }


    @OnClick({R.id.user_button_line, R.id.myartile_button_line, R.id.label_button_line, R.id.history_button_line, R.id.feedback_button_line, R.id.setting_button_line})
    public void onViewClicked(View view) {
        if (MyApplication.getLOGINSTATUS() == 1) {
            switch (view.getId()) {
                case R.id.user_button_line:
                    Intent intent = new Intent(MyApplication.getContext(), UserInfoSettingActivity.class);
                    startActivity(intent);
                    LogUtil.d(TAG + "___click", "hello user button");
                    break;
                case R.id.myartile_button_line:
                    LogUtil.d(TAG + "___click", "hello my article button");
                    break;
                case R.id.label_button_line:
                    LogUtil.d(TAG + "___click", "hello label button");
                    break;
                case R.id.history_button_line:
                    LogUtil.d(TAG + "___click", "hello history button");
                    break;
                case R.id.feedback_button_line:
                    LogUtil.d(TAG + "___click", "hello feedback button");
                    break;
                case R.id.setting_button_line:
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_other, appSettingFragment);
                    fragmentTransaction.commit();
                    LogUtil.d(TAG + "___click", "hello setting button");
                    break;
            }
        } else {
            Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }


}
