package com.woniukeji.jianmerchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.woniukeji.jianmerchant.fragment.MainVPFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<MainVPFragment> fragments;

    public MyPagerAdapter(FragmentManager fm, ArrayList<MainVPFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
