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
import com.woniukeji.jianmerchant.activity.login.LoginNewActivity;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.entity.User;
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
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = SplashActivity.this;



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
                case 0:
                    BaseBean<MerchantBean> merchantBean = (BaseBean<MerchantBean>) msg.obj;
                    splashActivity.saveToSP(merchantBean.getData());
//                    Intent intent = new Intent(splashActivity, MainActivity.class);
//                    splashActivity.startActivity(intent);
//                    splashActivity.finish();
                    break;
                case 1:
                    splashActivity.startActivity(new Intent(splashActivity, LoginNewActivity.class));
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(splashActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    splashActivity.finish();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(splashActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        //初始化SDK
//        ShareSDK.initSDK(this);
//        Picasso.with(context).load(R.mipmap.splash).into(imgSplash);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(SplashActivity.this);
    }

    private void saveToSP(MerchantBean user) {
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_WQTOKEN, user.getToken() != null ? user.getToken() : "");
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_TEL, user.getTel() != null ? user.getTel() : "");
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getPassword() != null ? user.getPassword() : "");
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_USERID, user.getLoginId());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, user.getMerchantId());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_MERCHANT_STATUS, user.getMerchantInfoStatus());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_PERMISSIONS, user.getPermissions());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_GROUP_NAME, user.getCompanyName());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_GROUP_IMG, user.getUserImage());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_PAYSTATUS, user.getPayStatus());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, user.getQiniuToken());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_PROVINCE, user.getProvince());
        SPUtils.setParam(this, Constants.LOGIN_INFO, Constants.SP_CITY, user.getCity());
        SPUtils.setParam(this, Constants.LOGIN_INFO,Constants.SP_ADDRESS, user.getCompanyAddress());   if (!TextUtils.isEmpty(String.valueOf(user.getLoginId()))) {
            if (JPushInterface.isPushStopped(this.getApplicationContext())) {
                JPushInterface.resumePush(this.getApplicationContext());
            }
            //登陆leancloud服务器 给极光设置别名
            LCChatKit.getInstance().open(String.valueOf(user.getLoginId()), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null != e) {
                        Toast.makeText(SplashActivity.this, "聊天服务启动失败，稍后请重新登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            JPushInterface.setAlias(this.getApplicationContext(), "jianguo" + user.getLoginId(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    LogUtils.e("jpush", s + ",code=" + i);
                }
            });
        }
        //是否填写商家资料信息 0未填写 1 正在审核 2审核拒绝 3审核通过
        if (user.getMerchantInfoStatus()==0){
            Intent intent = new Intent(this, ChooseActivity.class);
            startActivity(intent);
            this.finish();
        }else if (user.getMerchantInfoStatus()==1||user.getMerchantInfoStatus()==2){
            Intent intent1 = new Intent(this, StatusActivity.class);
            intent1.putExtra("type",user.getMerchantInfoStatus());
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

            ;
        }.start();
        super.onStart();
    }

    /**
     * chooseActivity
     * 根据保存的登陆信息 跳转不同界面
     */
    private void chooseActivity() {
        int loginType = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);

        if (loginType==0){
            startActivity(new Intent(context, LoginNewActivity.class));
            finish();
        }else {
            String phone= (String) SPUtils.getParam(context,Constants.LOGIN_INFO,Constants.SP_TEL,"");
            String pass= (String) SPUtils.getParam(context,Constants.LOGIN_INFO,Constants.SP_WQTOKEN,"");
            PhoneLogin(phone, pass);
        }
    }

    @Override
    public void onClick(View view) {

    }

        /**
         * phoneLogin
         * @param phone
         * @param pass
         */
        public void PhoneLogin(String phone, String pass) {
            OkHttpUtils
                    .post()
                    .url(Constants.LOGIN)
                    .addParams("tel", phone)
                    .addParams("token", pass)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<MerchantBean>>() {
                        @Override
                        public BaseBean<MerchantBean> parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson(string, new TypeToken<BaseBean<MerchantBean>>() {
                            }.getType());
                            return user;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean<MerchantBean> response, int id) {
                            if (response.getCode().equals("200")) {
                                SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = response;
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }



                    });
        }


}
