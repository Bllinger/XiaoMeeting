

package com.xiaomeeting.view.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.xiaomeeting.R;
import com.xiaomeeting.utils.LogUtil;
import com.xiaomeeting.utils.QiNiu.StatusBarUtil;
import com.xiaomeeting.view.fragment.BaseFragment;
import com.xiaomeeting.view.fragment.home.BoilingFragment;
import com.xiaomeeting.view.fragment.home.EngagementFragment;
import com.xiaomeeting.view.fragment.home.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {
    private final String TAG = LogUtil.createTag(this);
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private FrameLayout frameLayout;
    private BottomBarLayout bottomBarLayout;
    private RotateAnimation rotateAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //StatusBarUtil.setTransparent(HomeActivity.this);
        StatusBarUtil.setColor(HomeActivity.this, getResources().getColor(R.color.colorGray));
        initView();
        initData();
        initListener();
    }

    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.fl_content);
        bottomBarLayout = (BottomBarLayout) findViewById(R.id.bbl);
    }

    private void initData() {
        BoilingFragment boilingFragment = new BoilingFragment();
        EngagementFragment engagementFragment = new EngagementFragment();
        UserFragment userFragment = new UserFragment();

        mFragmentList.add(boilingFragment);
        mFragmentList.add(engagementFragment);
        mFragmentList.add(userFragment);
        changeFragment(0);

    }

    private void initListener() {
        bottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPositon) {
                LogUtil.d(TAG + "___initListener:", "position" + currentPositon);
                changeFragment(currentPositon);
            }

        });
    }

    private void changeFragment(int currentPosition) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        fragmentTransaction.commit();
    }
}
