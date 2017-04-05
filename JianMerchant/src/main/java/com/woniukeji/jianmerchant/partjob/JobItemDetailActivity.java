package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.payWage.CalculateActivity;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.JobInfo;
import com.woniukeji.jianmerchant.entity.NewJobDetail;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.publish.ChangeJobActivity;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class JobItemDetailActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_merchant_name) TextView tvMerchantName;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_type) ImageView imgType;
    @BindView(R.id.tv_manager_name) TextView tvManagerName;
    @BindView(R.id.tv_wages) TextView tvWages;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.ll_publish_time) LinearLayout llPublishTime;
    @BindView(R.id.tv_chakan_browse) TextView tvChakanBrowse;
    @BindView(R.id.ll_browse) LinearLayout llBrowse;
    @BindView(R.id.tv_human) TextView tvHuman;
    @BindView(R.id.tv_count) TextView tvCount;
    @BindView(R.id.ll_publisher) LinearLayout llPublisher;
    @BindView(R.id.ll_limit_sex) LinearLayout llLimitSex;
    @BindView(R.id.tv_no_limit) TextView tvNoLimit;
    @BindView(R.id.tv_work_location) TextView tvWorkLocation;
    @BindView(R.id.tv_location_detail) TextView tvLocationDetail;
    @BindView(R.id.tv_work_date) TextView tvWorkDate;
    @BindView(R.id.tv_work_time) TextView tvWorkTime;
    @BindView(R.id.tv_collection_sites) TextView tvCollectionSites;
    @BindView(R.id.tv_collection_time) TextView tvCollectionTime;
    @BindView(R.id.tv_sex) TextView tvSex;
    @BindView(R.id.tv_pay_method) TextView tvPayMethod;
    @BindView(R.id.tv_other) TextView tvOther;
    @BindView(R.id.tv_notic) TextView tvNotic;
    @BindView(R.id.tv_notic6) TextView tvNotic6;
    @BindView(R.id.tv_work_content) TextView tvWorkContent;
    @BindView(R.id.rl_work) RelativeLayout rlWork;
    @BindView(R.id.tv_notic7) TextView tvNotic7;
    @BindView(R.id.tv_work_require) TextView tvWorkRequire;
    @BindView(R.id.tv_worker) TextView tvWorker;
    @BindView(R.id.cirimg_work) CircleImageView cirimgWork;
    @BindView(R.id.tv_company_name) TextView tvCompanyName;
    @BindView(R.id.tv_jobs_count) TextView tvJobsCount;
    @BindView(R.id.rl_company) RelativeLayout rlCompany;
    @BindView(R.id.btn_change) Button btnChange;
    @BindView(R.id.btn_finish) Button btnFinish;
    @BindView(R.id.btn_down) Button btnDown;
    @BindView(R.id.btn_clearing) Button btnClearing;
    @BindView(R.id.btn_no_limit) Button btnNoLimit;
    @BindView(R.id.btn_boy) Button btnBoy;
    @BindView(R.id.btn_girl) Button btnGirl;
    @BindView(R.id.ll_offlin_pay) LinearLayout layoutOfflin ;

    private Context mContext = JobItemDetailActivity.this;
    private JobInfo modleJob;
    private long jobid;
   NewJobDetail jobDetailsBaseBean;
    private int permission;
    private String phone;
    private SubscriberOnNextListener<NewJobDetail> jobBaseSubscriberOnNextListener;
    private SubscriberOnNextListener<String> changeSubscriberOnNextListener;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.btn_boy, R.id.btn_girl, R.id.btn_no_limit, R.id.btn_change, R.id.btn_finish, R.id.btn_down,R.id.btn_clearing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clearing:
                Intent intent1 = new Intent(mContext, CalculateActivity.class);
                intent1.putExtra("money", tvWages.getText().toString());
                intent1.putExtra("name", tvMerchantName.getText().toString());
                intent1.putExtra("jobid", jobDetailsBaseBean.getId());
                startActivity(intent1);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_boy:
                Intent boyIntent = new Intent(this, FilterActivity.class);
                boyIntent.putExtra("jobid", jobid);
                boyIntent.putExtra("jobname", jobDetailsBaseBean.getJob_name());
                startActivity(boyIntent);
                break;
            case R.id.btn_girl:
                break;
            case R.id.btn_no_limit:
                Intent Intent = new Intent(this, FilterActivity.class);
                Intent.putExtra("jobid", String.valueOf(jobid));
                Intent.putExtra("jobname", modleJob.getJob_name());
                startActivity(Intent);
                break;
            case R.id.btn_change:
                new SweetAlertDialog(JobItemDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("您确认修改当前兼职吗？")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent changeIntent = new Intent(JobItemDetailActivity.this, ChangeJobActivity.class);
                                changeIntent.putExtra("jobid", String.valueOf(jobid));
                                changeIntent.putExtra("type", "change");
                                startActivity(changeIntent);
                            }
                        }).show();

                break;
            case R.id.btn_finish:
                new SweetAlertDialog(JobItemDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("您确认停止并结束招聘该兼职？")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                status=3;
                                long times=System.currentTimeMillis();
                                String sign= MD5Util.getSign(JobItemDetailActivity.this,times);
                                HttpMethods.getInstance().changeJobStatus(new ProgressSubscriber<String>(changeSubscriberOnNextListener, JobItemDetailActivity.this), String.valueOf(jobid),phone,sign,String.valueOf(times),"3");
                            }
                        }).show();
                break;
            case R.id.btn_down:
               if(modleJob.getStatus() == 1){
                    new SweetAlertDialog(JobItemDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确认暂停招聘么？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    status=2;
                                    long times=System.currentTimeMillis();
                                    String sign= MD5Util.getSign(JobItemDetailActivity.this,times);
                                    HttpMethods.getInstance().changeJobStatus(new ProgressSubscriber<String>(changeSubscriberOnNextListener, JobItemDetailActivity.this), String.valueOf(jobid),phone,sign,String.valueOf(times),"2");
//                                    postDown(String.valueOf(loginId), String.valueOf(jobid), "13");
                                }
                            }).show();
                }else if(modleJob.getStatus() == 2){
                    new SweetAlertDialog(JobItemDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确认恢复招聘么？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    status=1;
                                    long times=System.currentTimeMillis();
                                    String sign= MD5Util.getSign(JobItemDetailActivity.this,times);
                                    HttpMethods.getInstance().changeJobStatus(new ProgressSubscriber<String>(changeSubscriberOnNextListener, JobItemDetailActivity.this), String.valueOf(jobid),phone,sign,String.valueOf(times),"1");
                                }
                            }).show();
                }
                break;
        }
    }
    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;
        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JobItemDetailActivity jobDetailActivity = (JobItemDetailActivity) reference.get();
            switch (msg.what) {
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_partjob_item_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        jobBaseSubscriberOnNextListener=new SubscriberOnNextListener<NewJobDetail>() {
            @Override
            public void onNext(NewJobDetail jobBase) {
                TastyToast.makeText(JobItemDetailActivity.this,"获取成功", TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                jobDetailsBaseBean =  jobBase;
                fillData();
            }
        };

        changeSubscriberOnNextListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                if (status==1){
                    btnDown.setText("暂停招聘");
                }else if(status==2){
                    btnDown.setText("恢复招聘");
                }else {
                    finish();
                }
                TastyToast.makeText(JobItemDetailActivity.this,"操作成功", TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
            }
        };
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        modleJob = (JobInfo) intent.getSerializableExtra("job");
        jobid = modleJob.getId();
        permission = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_PERMISSIONS, 1);
        phone = (String) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_TEL, "");
        long times=System.currentTimeMillis();
        String sign= MD5Util.getSign(JobItemDetailActivity.this,times);
        HttpMethods.getInstance().getjobDetail(new ProgressSubscriber<NewJobDetail>(jobBaseSubscriberOnNextListener, this), String.valueOf(jobid),phone,sign,String.valueOf(times));

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(JobItemDetailActivity.this);
    }




    private void fillData() {
        tvTitle.setText("兼职详情");
        tvMerchantName.setText(modleJob.getJob_name());
        tvWorkLocation.setText(jobDetailsBaseBean.getAddress());
        tvManagerName.setText(modleJob.getJob_name());
        tvChakanBrowse.setText(modleJob.getBrowse_count()+"");
        String date = DateUtils.getTime(Long.valueOf(jobDetailsBaseBean.getStart_date()), Long.valueOf(jobDetailsBaseBean.getEnd_date()));
        tvWorkDate.setText(date);
        tvDate.setText(date);
        tvWorkTime.setText(DateUtils.getHm(jobDetailsBaseBean.getBegin_time()) + "-" + DateUtils.getHm(jobDetailsBaseBean.getEnd_time()));
        tvCollectionSites.setText(jobDetailsBaseBean.getSet_place());
        tvCollectionTime.setText(jobDetailsBaseBean.getSet_time());
        tvJobsCount.setText(modleJob.getCount() + "/" + modleJob.getSum());
        if (modleJob.getTerm() == 0) {//0=月结，1=周结，2=日结，3=小时结，4=次，5=义工
            tvWages.setText(modleJob.getMoney() + "/月");
        } else if (modleJob.getTerm() == 1) {
            tvWages.setText(modleJob.getMoney() + "/周");
        } else if (modleJob.getTerm() == 2) {
            tvWages.setText(modleJob.getMoney() + "/日");
        } else if (modleJob.getTerm() == 3) {
            tvWages.setText(modleJob.getMoney() + "/小时");
        } else if (modleJob.getTerm() == 4) {
            tvWages.setText(modleJob.getMoney() + "/次");
        }else if(modleJob.getTerm()==5){
            tvWages.setText("义工");
        }else if(modleJob.getTerm()==6){
            tvWages.setText("面议");
        }
        tvCount.setText(modleJob.getCount() + "/" + modleJob.getSum());

        if (jobDetailsBaseBean.getLimit_sex() == 0) {
            btnNoLimit.setVisibility(View.VISIBLE);
            llLimitSex.setVisibility(View.GONE);
            btnNoLimit.setText("报名人数： " + jobDetailsBaseBean.getUser_count() + "/" + modleJob.getSum());
            tvSex.setText("女");
        } else if (jobDetailsBaseBean.getLimit_sex() == 1) {
            btnNoLimit.setVisibility(View.VISIBLE);
            llLimitSex.setVisibility(View.GONE);
            btnNoLimit.setText("报名人数： " + jobDetailsBaseBean.getUser_count() + "/" + modleJob.getSum());
            tvSex.setText("男");
        } else if (jobDetailsBaseBean.getLimit_sex() == 2) {
            btnNoLimit.setVisibility(View.VISIBLE);
            llLimitSex.setVisibility(View.GONE);
            btnNoLimit.setText("报名人数： " +jobDetailsBaseBean.getUser_count() + "/" + modleJob.getSum());
            tvSex.setText("男女不限");
        } else {
            btnNoLimit.setVisibility(View.VISIBLE);
            llLimitSex.setVisibility(View.GONE);
            btnNoLimit.setText("报名人数： " + jobDetailsBaseBean.getUser_count() + "/" + modleJob.getSum());
            tvSex.setText("男女各需");//性别限制（0=只招女，1=只招男，2=不限男女，30,31，男女各需）
        }

        //期限（1=月结，2=周结，3=日结，4=小时结）
        if (jobDetailsBaseBean.getMode() == 0) {
            tvPayMethod.setText("月结");
        } else if (jobDetailsBaseBean.getMode() == 1) {
            tvPayMethod.setText("周结");
        } else if (jobDetailsBaseBean.getMode() == 2) {
            tvPayMethod.setText("日结");
        } else
            tvPayMethod.setText("小时结");
        tvWorkContent.setText(jobDetailsBaseBean.getContent());
        tvWorkRequire.setText(jobDetailsBaseBean.getRequire());
        //商家信息
        tvCompanyName.setText(jobDetailsBaseBean.getContact_name());


        //根据当前兼职状态显示对应操作按钮
        if (modleJob.getStatus() == 2) {
            btnDown.setText("恢复招聘");
        } else if (modleJob.getStatus() == 1) {
            btnDown.setText("暂停招聘");
        } else if (modleJob.getStatus() >= 3) {
            btnFinish.setVisibility(View.GONE);
            btnChange.setVisibility(View.GONE);
            btnDown.setVisibility(View.GONE);
            layoutOfflin.setVisibility(View.VISIBLE);
        }
        //3是内部1是外部商家，2是个人商户
        if (permission==3){
            btnClearing.setVisibility(View.VISIBLE);
            layoutOfflin.setVisibility(View.GONE);
        }else {
            btnClearing.setVisibility(View.GONE);
            layoutOfflin.setVisibility(View.VISIBLE);
        }

    }

}
