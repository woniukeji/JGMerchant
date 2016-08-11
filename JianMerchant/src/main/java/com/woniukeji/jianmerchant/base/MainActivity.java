package com.woniukeji.jianmerchant.base;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.adapter.MyPagerAdapter;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.fragment.MainVPFragment;
import com.woniukeji.jianmerchant.utils.ActivityManager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends BaseActivity {

    private ImageView mBackView;
    private TextView mtitle;
    private ImageView mRightCorner;
    private CommonTabLayout mTabLayout;
    private ViewPager mVPContent;
    private String[] mTitles = {"发布", "管理", "果聊", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<MainVPFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main_activity_new);
    }

    @Override
    public void initViews() {
        mBackView = (ImageView) findViewById(R.id.img_back);
        mtitle = (TextView) findViewById(R.id.tv_title);
        mRightCorner = (ImageView) findViewById(R.id.img_share);
        mTabLayout = (CommonTabLayout) findViewById(R.id.bottom_tab);
        mVPContent = (ViewPager) findViewById(R.id.viewpager_content);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        mBackView.setVisibility(View.GONE);
        mRightCorner.setVisibility(View.GONE);
        mtitle.setText("兼果");
        //设置ViewPager
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(new MainVPFragment().newInstance(mTitles[i]));

        }
        mVPContent.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragments));
        mVPContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置底部导航栏
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.showMsg(2, new Random().nextInt(100) + 1);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVPContent.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                mTabLayout.showMsg(2, new Random().nextInt(100) + 1);
            }
        });


        mVPContent.setCurrentItem(0);

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);
    }

    @Override
    public void onClick(View v) {

    }

    private long touchTime = 0;
    private long waitTime = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - touchTime >= waitTime) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
//                finish();
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
