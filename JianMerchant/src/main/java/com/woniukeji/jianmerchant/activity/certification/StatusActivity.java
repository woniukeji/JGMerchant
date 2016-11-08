package com.woniukeji.jianmerchant.activity.certification;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
    private int type;
    private SubscriberOnNextListener<Status> subscriberOnNextListener;
    private int merchantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        merchantId = (int) SPUtils.getParam(StatusActivity.this, Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, 0);
        type = getIntent().getIntExtra("type", 0);
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
        HttpMethods.getInstance().getStatus(new ProgressSubscriber<Status>(subscriberOnNextListener,this),String.valueOf(merchantId));

    }

    @Override
    public void initListeners() {
        subscriberOnNextListener =  new SubscriberOnNextListener<Status>() {

            @Override
            public void onNext(Status o) {
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        HttpMethods.getInstance().getStatus(new ProgressSubscriber<Status>(subscriberOnNextListener,this),String.valueOf(merchantId));

    }

    @Override
    public void initData() {
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
                if (type == 1) {
                    finish();
                    ActivityManager.getActivityManager().finishAllActivity();
                } else if(type==2){
                    startActivity(new Intent(this,ChooseActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
        }
    }
}
