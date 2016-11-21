package com.woniukeji.jianmerchant.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.activity.certification.ChooseActivity;
import com.woniukeji.jianmerchant.activity.certification.StatusActivity;
import com.woniukeji.jianmerchant.activity.login.LeadActivity;
import com.woniukeji.jianmerchant.activity.login.LoginNewActivity;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.entity.NewMerchant;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.BackgroundSubscriberOnerror;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextErrorListener;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.leancloud.chatkit.LCChatKit;
import okhttp3.Call;
import okhttp3.Response;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.img_splash) ImageView imgSplash;
    private Handler mHandler = new Myhandler(this);
    private Context context = SplashActivity.this;
    private SubscriberOnNextErrorListener<NewMerchant> subscriberOnNextListener;

    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity = (SplashActivity) reference.get();
            switch (msg.what) {

                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initListeners() {
        subscriberOnNextListener=new SubscriberOnNextErrorListener<NewMerchant>() {
            @Override
            public void onNext(NewMerchant merchantBean) {
                saveToSP(merchantBean);
                SPUtils.setParam(SplashActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, 2);
            }

            @Override
            public void onError(String mes) {
                Intent intent = new Intent(SplashActivity.this, LoginNewActivity.class);
                startActivity(intent);
                finish();
                super.onError(mes);
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(SplashActivity.this);
    }

    private void saveToSP(NewMerchant user) {
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_WQTOKEN, user.getToken() != null ? user.getToken() : "");
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_TEL, user.getTel()!= null ? user.getTel() : "");
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getPassword() != null ? user.getPassword() : "");
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_USERID, user.getId());
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, user.getMerchantId());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_MERCHANT_STATUS, user.getAuth_status());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_PERMISSIONS, user.getBusiness_type());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, user.getQiniu_token());
            if (JPushInterface.isPushStopped(this.getApplicationContext())) {
                JPushInterface.resumePush(this.getApplicationContext());
            }
            //登陆leancloud服务器 给极光设置别名
            LCChatKit.getInstance().open(String.valueOf(user.getId()), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null != e) {
                        Toast.makeText(SplashActivity.this, "聊天服务启动失败，稍后请重新登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            JPushInterface.setAlias(this.getApplicationContext(), "jianguo" + user.getId(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    LogUtils.e("jpush", s + ",code=" + i);
                }
            });
        //是否填写商家资料信息 1未填写 2 正在审核 3审核拒绝 4审核通过
        if (user.getAuth_status()==0){
            Intent intent = new Intent(this, ChooseActivity.class);
            startActivity(intent);
            this.finish();
        }else if (user.getAuth_status()==1||user.getAuth_status()==2){
            Intent intent1 = new Intent(this, StatusActivity.class);
            intent1.putExtra("type",user.getAuth_status());
            startActivity(intent1);
            this.finish();
        }else {
            Intent intent1 = new Intent(this, MainActivity.class);
            intent1.putExtra("login",true);
            startActivity(intent1);
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {

        // 在当前的界面变为用户可见的时候调用的方法
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                chooseActivity();
            }


        }.start();
        super.onStart();
    }
    /**
     * chooseActivity
     * 根据保存的登陆信息 跳转不同界面
     */
    private void chooseActivity() {
        int loginType = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_TYPE, 0);

        if (loginType==0) {
            SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_TYPE, 1);
            startActivity(new Intent(context, LeadActivity.class));
            finish();
        }else if(loginType==1){
            startActivity(new Intent(context, LoginNewActivity.class));
            finish();
        }else {
            String phone= (String) SPUtils.getParam(context,Constants.LOGIN_INFO,Constants.SP_TEL,"");
            String token= (String) SPUtils.getParam(context,Constants.LOGIN_INFO,Constants.SP_WQTOKEN,"");
            HttpMethods.getInstance().autoLogin(new BackgroundSubscriberOnerror<NewMerchant>(subscriberOnNextListener,this),phone,token);
        }
    }

    @Override
    public void onClick(View view) {

    }



}
