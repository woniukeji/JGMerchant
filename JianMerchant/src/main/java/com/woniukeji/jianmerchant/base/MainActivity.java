package com.woniukeji.jianmerchant.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.woniukeji.jianmerchant.fragment.ManagerFragment;
import com.woniukeji.jianmerchant.fragment.PublishFragment;
import com.woniukeji.jianmerchant.mine.MineFragment;
import com.woniukeji.jianmerchant.utils.ActivityManager;

import java.util.ArrayList;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import cn.leancloud.chatkit.event.LCIMUnReadCountEvent;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {

    private ImageView mBackView;
    private TextView mtitle;
    private ImageView mRightCorner;
    private CommonTabLayout mTabLayout;
    private ViewPager mVPContent;
    private String[] mTitles = {"首页", "财务","", "果聊", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.drawable.cw,R.drawable.fb,
            R.drawable.chat, R.mipmap.tab_contact_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.drawable.cwclick,R.drawable.fbclick,
            R.drawable.chactclick,  R.mipmap.tab_contact_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main_activity_new);
    }

    @Override
    public void initViews() {
        mRightCorner = (ImageView) findViewById(R.id.img_share);
        mTabLayout = (CommonTabLayout) findViewById(R.id.bottom_tab);
        mVPContent = (ViewPager) findViewById(R.id.viewpager_content);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        //设置ViewPager
        for (int i = 0; i < mTitles.length+1; i++) {
            switch (i) {
                case 0:
                    mFragments.add(new ManagerFragment());
                    break;
                case 1:
                    mFragments.add(new MainVPFragment().newInstance("财务"));
                    break;
                case 2:
                    mFragments.add(new PublishFragment());
                    break;
                case 3:
                    mFragments.add(new LCIMConversationListFragment());
                    break;
                case 4:
                    mFragments.add(new MineFragment());
                    break;
            }
        }
        mVPContent.setOffscreenPageLimit(4);
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
        TextView titleView = mTabLayout.getTitleView(2);
        titleView.setVisibility(View.GONE);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVPContent.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 3) {
                    mTabLayout.hideMsg(3);
                }
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

    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     * 当前选中的是果聊界面的时候不显示消息数量
     * 否则消息数量自加
     */
    private int msgCount = 0;
    public void onEvent(LCIMIMTypeMessageEvent messageEvent) {

//        if (tabHost.getCurrentTab()==2) {
//            tabHost.hideMsg(2);
//            msgCount=0;
//        }else {
//            msgCount++;
//            tabHost.showMsg(2,msgCount);
//            tabHost.setMsgMargin(2, -7, 5);
//        }
        if (mTabLayout.getCurrentTab() != 3) {
            mTabLayout.showMsg(3, ++msgCount);
        } else {
            msgCount = 0;
            mTabLayout.hideMsg(3);
        }

    }
    public void onEvent(LCIMUnReadCountEvent event) {
        if (event.unReadCount!=0) {
            mTabLayout.showMsg(3,event.unReadCount);
//            tabHost.setMsgMargin(2, -7, 5);
        }
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
                finish();
//                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
