package com.woniukeji.jianmerchant.base;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.sdsmdg.tastytoast.TastyToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.adapter.MyPagerAdapter;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.eventbus.AvatarEvent;
import com.woniukeji.jianmerchant.fragment.FinancialManagementFragment;
import com.woniukeji.jianmerchant.fragment.ManagerFragment;
import com.woniukeji.jianmerchant.fragment.PublishFragment;
import com.woniukeji.jianmerchant.mine.MineFragment;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.BitmapUtils;
import com.woniukeji.jianmerchant.utils.FileUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.MD5Coder;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.NoScrollViewPager;
import com.woniukeji.jianmerchant.widget.PublishPopupWindow;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import cn.leancloud.chatkit.event.LCIMUnReadCountEvent;
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends BaseActivity {

    private ImageView mBackView;
    private TextView mtitle;
    private ImageView mRightCorner;
    private CommonTabLayout mTabLayout;
    private NoScrollViewPager mVPContent;
    private String[] mTitles = {"首页", "财务","", "果聊", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.drawable.cw,R.mipmap.tem,
            R.drawable.chat, R.mipmap.tab_contact_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.drawable.cwclick,R.mipmap.tem,
            R.drawable.chactclick,  R.mipmap.tab_contact_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String filePath;

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
        mVPContent = (NoScrollViewPager) findViewById(R.id.viewpager_content);
        ImageView start= (ImageView) findViewById(R.id.start);
        findViewById(R.id.bg_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishPopupWindow share = new PublishPopupWindow(MainActivity.this);
                share.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                share.showAtLocation(MainActivity.this.getLayoutInflater().inflate(R.layout.activity_main_activity_new, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishPopupWindow share = new PublishPopupWindow(MainActivity.this);
                share.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                share.showAtLocation(MainActivity.this.getLayoutInflater().inflate(R.layout.activity_main_activity_new, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                TastyToast.makeText(MainActivity.this,"dianji",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
            }
        });
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
//                    mFragments.add(new MainVPFragment().newInstance("财务"));
                    mFragments.add(new FinancialManagementFragment().newInstance("财务"));
                    break;
                case 2:
                    mFragments.add(new FragmentText());
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
//        TextView titleView = mTabLayout.getTitleView(2);
//        titleView.setVisibility(View.GONE);

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
            mTabLayout.setMsgMargin(3,-5,-1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == -1) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                File file = new File(path.get(0));
                Uri imgSource = Uri.fromFile(file);
                startCropImageActivity(imgSource, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE1);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE1) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                filePath = FileUtils.getRealFilePath(this, result.getUri());
                Bitmap bitmap= BitmapUtils.compressBitmap(filePath,1080, 720);
                String key = MD5Coder.getQiNiuName("logo");
                String url1 = "http://7xlell.com2.z0.glb.qiniucdn.com/" + key;
                AvatarEvent avatarEvent=new AvatarEvent();
                avatarEvent.setBitmap(bitmap);
                avatarEvent.setUrl(url1);
                EventBus.getDefault().post(avatarEvent);
                upLoadQiNiu(this, key, filePath);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri,int requestCode) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this,requestCode,false);
    }

    public void upLoadQiNiu(final Context context, final String key, final String filePath) {
        String commonUploadToken = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, "");
        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(filePath, key, commonUploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                LogUtils.e("key","ok："+info.error);
            }
        }, new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    public void progress(String key, double percent) {
                    }
                }, null));

    }
}
