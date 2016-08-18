package com.woniukeji.jianmerchant.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.FragmentText;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.partjob.PartJobManagerFragment;

import java.util.ArrayList;

public class ManagerFragment extends BaseFragment {


    private View rootView;
    private CommonTabLayout tl6;
    private ViewPager mainPager;
    private ImageView imgBack;
    private ImageView imgShare;
    private TextView tvTitle;
    private ArrayList<CustomTabEntity> mTabEntities ;
    private String[] mTitles = {"录取", "完成"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_about_me_select};
    private ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_part_job_manager, container, false);
        initView();
        initData();
        initListener();
        return rootView;
    }




    private void initView() {
        tl6 = (CommonTabLayout) rootView.findViewById(R.id.tl_6);
        mainPager = (ViewPager) rootView.findViewById(R.id.mainPager);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        imgShare = (ImageView) rootView.findViewById(R.id.img_share);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
    }

    private void initData() {
        mTabEntities = new ArrayList<>();
        imgBack.setVisibility(View.GONE);
        imgShare.setVisibility(View.GONE);
        tvTitle.setText("兼职管理");
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        mainPager.setAdapter(adapter);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tl6.setTabData(mTabEntities);
        tl6.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mainPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initListener() {
        tl6.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mainPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tl6.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PartJobManagerFragment.newInstance(1);           //录取
                case 1:
                    return PartJobManagerFragment.newInstance(0);          //完成
            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    protected void visiableToUser() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void firstVisiableToUser() {

    }

    @Override
    protected void onSaveInfoToBundle(Bundle outState) {

    }

    @Override
    protected void onRestoreInfoFromBundle(Bundle savedInstanceState) {

    }

}
