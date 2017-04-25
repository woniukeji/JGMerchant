package com.woniukeji.jianmerchant.activity.certification;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.Status;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_status) ImageView imgStatus;
    @BindView(R.id.tv_status) TextView tvStatus;
    @BindView(R.id.tv_content) TextView tvContent;
    @BindView(R.id.btns_next) Button btnsNext;
    @BindView(R.id.rl_job) LinearLayout rlJob;
    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.activity_status) RelativeLayout activityStatus;
    private int type; //认证状态 1 正在审核中 2 认证失败
    private SubscriberOnNextListener<Status> subscriberOnNextListener;
    private int merchantId;
    private String tel;
    private String token;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int businesstype;//商家权限（3是内部,2是外部商家，1是个人商户）


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        merchantId = (int) SPUtils.getParam(StatusActivity.this, Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, 0);
        type = getIntent().getIntExtra("type", 0);
        businesstype = getIntent().getIntExtra("businesstype",5);
        if (type == 1) {
            tvStatus.setText("正在审核中");
            tvContent.setText("您的认证资料正在审核中，我们的工作人员会在2小时之内为您处理，请耐心等待，如有疑问可咨询客服人员：01053350021");
            btnNext.setText("确定");
        } else {
            tvStatus.setText("认证失败");
            tvContent.setText("由于您的相关资料不符合认证要求，请重新提交，如有疑问可咨询客服人员：01053350021");
            btnNext.setText("重新审核");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        long timestamp = System.currentTimeMillis();
        String sign = MD5Util.getSign(StatusActivity.this, timestamp);
        HttpMethods.getInstance().getStatus(new ProgressSubscriber<Status>(subscriberOnNextListener,this),tel, String.valueOf(timestamp),sign);

    }

    @Override
    public void initListeners() {
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.top);
        subscriberOnNextListener =  new SubscriberOnNextListener<Status>() {
            @Override
            public void onNext(Status o) {
                swipeRefreshLayout.setRefreshing(false);
                type=o.getStatus();
                if (type == 1) {
                    tvStatus.setText("正在审核中");
                    tvContent.setText("您的认证资料正在审核中，我们的工作人员会在2小时之内为您处理，请耐心等待，如有疑问可咨询客服人员：01053350021");
                    btnNext.setText("确定");
                } else if(type == 2){
                    tvStatus.setText("认证失败");
                    tvContent.setText("由于您的相关资料不符合认证要求，请重新提交，如有疑问可咨询客服人员：01053350021");
                    btnNext.setText("重新审核");
                }else {
                    tvStatus.setText("认证成功");
                    tvContent.setText("您可以进入app，发布兼职啦");
                    btnNext.setText("确定");
                }
            }
        };
        swipeRefreshLayout.setColorSchemeResources(R.color.app_bg);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                long timestamp = System.currentTimeMillis();
                String sign = MD5Util.getSign(StatusActivity.this, timestamp);
                HttpMethods.getInstance().getStatus(new ProgressSubscriber<Status>(subscriberOnNextListener,StatusActivity.this),tel, String.valueOf(timestamp),sign);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        long timestamp = System.currentTimeMillis();
        String sign = MD5Util.getSign(StatusActivity.this, timestamp);
        HttpMethods.getInstance().getStatus(new ProgressSubscriber<Status>(subscriberOnNextListener,this),tel, String.valueOf(timestamp),sign);

    }

    @Override
    public void initData() {
        tel = (String) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_TEL, "");
        token = (String) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_WQTOKEN, "");
          }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);
    }



    @OnClick({R.id.img_back, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                if(businesstype == 1){

                    if (type == 1) {
                        finish();
//                    ActivityManager.getActivityManager().finishAllActivity();
                    } else if(type==2){
                        startActivity(new Intent(this,PersonalDetailActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }

                }else{
                    if (type == 1) {
                        finish();
//                    ActivityManager.getActivityManager().finishAllActivity();
                    } else if(type==2){
                        startActivity(new Intent(this,MerchantDetailActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                }


                break;
        }
    }
}
