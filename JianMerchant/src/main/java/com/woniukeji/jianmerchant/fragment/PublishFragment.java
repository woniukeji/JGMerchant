package com.woniukeji.jianmerchant.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.ArrayList;

public class PublishFragment extends BaseFragment {

    private View view;
    private ArrayList<CustomTabEntity> tabList;
    private String[] tabContents = {"创建新兼职", "历史纪录", "模板"};

    private ArrayList<PublishPartJobFragment> fragmentsList = new ArrayList<>();
    private String[] fragmentsType = {"cjxjz","lsjl","mb"};
    private ImageView imgBack;
    private TextView tvTitle;
    private ImageView imgShare;
    private CommonTabLayout tlNew;
    private ViewPager vpPublishPartjob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_publish_new, container, false);
        initView();
        initData();
        return view;
    }



    private void initView() {
        imgBack = (ImageView) view.findViewById(R.id.img_back);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        imgShare = (ImageView) view.findViewById(R.id.img_share);
        tlNew = (CommonTabLayout) view.findViewById(R.id.tl_new);
        vpPublishPartjob = (ViewPager) view.findViewById(R.id.vp_publish_partjob);

    }

    private void initData() {
        tvTitle.setText("发布兼职");
        tabList = new ArrayList<>();
        imgShare.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);
        for (int i = 0; i < tabContents.length; i++) {
            fragmentsList.add(PublishPartJobFragment.newInstance(fragmentsType[i]));
        }
        vpPublishPartjob.setOffscreenPageLimit(2);
        vpPublishPartjob.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentsList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentsList.size();
            }
        });
        vpPublishPartjob.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tlNew.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < tabContents.length; i++) {
            tabList.add(new TabEntity(tabContents[i],0,0));
        }
        tlNew.setTabData(tabList);
        tlNew.setCurrentTab(0);
        tlNew.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPublishPartjob.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void visiableToUser() {

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

    private void saveToSP(User user) {
        SPUtils.setParam(getHoldingContext(), Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getT_user_login().getPassword());
    }

}
