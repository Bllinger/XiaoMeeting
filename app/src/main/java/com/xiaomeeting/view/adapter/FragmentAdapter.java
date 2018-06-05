package com.xiaomeeting.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Blinger on 2018/6/2.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    String[] mTitiles;
    List<Fragment> mFragment;

    public void setTitiles(String[] titiles) {
        mTitiles = titiles;
    }

    public void setFragment(List<Fragment> fragment) {
        mFragment = fragment;
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment != null ? mFragment.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitiles[position];
    }
}
