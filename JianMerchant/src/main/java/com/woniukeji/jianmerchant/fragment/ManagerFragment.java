package com.woniukeji.jianmerchant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.FragmentText;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.eventbus.MessageEvent;
import com.woniukeji.jianmerchant.jpush.PushMessageActivity;
import com.woniukeji.jianmerchant.partjob.PartJobManagerActivity;
import com.woniukeji.jianmerchant.partjob.PartJobManagerFragment;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.woniukeji.jianmerchant.widget.UpDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

public class ManagerFragment extends BaseFragment {


    private View rootView;
    private CommonTabLayout tl6;
    private ViewPager mainPager;
    private ImageView imgBack;
    private ImageView imgShare;
    private TextView tvTitle;
    private RelativeLayout rlMessage;
    private CircleImageView circleDot;
    private ArrayList<CustomTabEntity> mTabEntities ;
    private String[] mTitles = {"录取中", "已完成"};
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
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("检测到新版本，是否更新？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    UpDialog upDataDialog = new UpDialog(getActivity(),url);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_part_job_manager, container, false);
        initView();
        initData();
        initListener();
        return rootView;
    }

    /**
     * 顯示極光推送過來的消息提醒
     * 首页信封險是紅點
     */
    public void onEvent(MessageEvent event) {
        circleDot.setVisibility(View.VISIBLE);
    }


    private void initView() {
        tl6 = (CommonTabLayout) rootView.findViewById(R.id.tl_6);
        mainPager = (ViewPager) rootView.findViewById(R.id.mainPager);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        imgShare = (ImageView) rootView.findViewById(R.id.img_share);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        rlMessage= (RelativeLayout) rootView.findViewById(R.id.rl_message);
        circleDot= (CircleImageView) rootView.findViewById(R.id.circle_dot);
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
        EventBus.getDefault().register(this);
        checkVersion(CommonUtils.getVersion(getActivity()));
        rlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleDot.setVisibility(View.GONE);
                startActivity(new Intent(getContext(), PushMessageActivity.class));
            }
        });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * getInfo
     * @param version
     */
    public void checkVersion(int version) {
        OkHttpUtils
                .get()
                .url(Constants.CHECK_VERSION)
                .addParams("v", String.valueOf(version))
                .build()
                .connTimeOut(80000)
                .readTimeOut(90000)
                .writeTimeOut(90000)
                .execute(new StringCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {

                             }

                             @Override
                             public void onResponse(String response, int id) {
                                 Gson gson=new Gson();
                                 Map<String, String> map = gson.fromJson(response, new TypeToken<Map<String, String>>() {}.getType());
                                 if (map!=null&&!map.get("url").equals("")){
                                     Message message=new Message();
                                     message.what=0;
                                     message.obj=map.get("url");
                                     handler.sendMessage(message);
                                 }else {
                                     Message message=new Message();
                                     message.what=1;
                                     handler.sendMessage(message);
                                 }
                             }
                         }
                );
    }
}
