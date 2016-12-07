package com.woniukeji.jianmerchant.partjob;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.FragmentText;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.widget.UpDialog;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PartJobManagerActivity extends BaseActivity {

    @BindView(R.id.tl_6) CommonTabLayout tl6;
    @BindView(R.id.mainPager) ViewPager mainPager;
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"录取", "完成"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_about_me_select};
    private ViewPagerAdapter adapter;

    private Handler handler = new Handler() {
        // 处理子线程给我们发送的消息。
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    final String url= (String) msg.obj;
                    new SweetAlertDialog(PartJobManagerActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("检测到新版本，是否更新？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    UpDialog upDataDialog = new UpDialog(PartJobManagerActivity.this,url);
                                    upDataDialog.setCanceledOnTouchOutside(false);
                                    upDataDialog.setCanceledOnTouchOutside(false);
                                    upDataDialog.show();
                                }
                            }).show();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }

    };

    public int mType=1;//用于判断是录取还是完成的fragment（同一个对象实例化，需要区分）
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_part_job_manager);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("管理兼职");
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
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
//                mainPager.setCurrentItem(position);
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

    @Override
    public void initListeners() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);
    }

    @Override
    public void onClick(View view) {

    }
    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
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

}
