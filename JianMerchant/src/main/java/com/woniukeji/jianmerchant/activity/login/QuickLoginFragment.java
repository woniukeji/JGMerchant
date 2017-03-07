package com.woniukeji.jianmerchant.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.activity.certification.ChooseActivity;
import com.woniukeji.jianmerchant.activity.certification.StatusActivity;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.entity.NewMerchant;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.leancloud.chatkit.LCChatKit;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A Register screen that offers Register via email/password.
 */
public class QuickLoginFragment extends BaseFragment {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    @BindView(R.id.phoneNumber) EditText phoneNumber;
    @BindView(R.id.btn_get_code) Button btnGetCode;
    @BindView(R.id.icon_pass) ImageView iconPass;
    @BindView(R.id.phone_code) EditText phoneCode;
    @BindView(R.id.cb_rule) CheckBox cbRule;
    @BindView(R.id.tv_rule) TextView tvRule;
    @BindView(R.id.user_rule) LinearLayout userRule;
    @BindView(R.id.sign_in_button) Button signInButton;
    @BindView(R.id.email_login_form) LinearLayout emailLoginForm;
    @BindView(R.id.login_form) LinearLayout loginForm;

    private SubscriberOnNextListener<NewMerchant> subscriberOnNextListener;
    private SubscriberOnNextListener<String> smsSubscriberOnNextListener;
    private Handler mHandler = new Myhandler(getActivity());
    private TimeCount time;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (time!=null){
            time.cancel();
        }
    }

    @Override
    protected void visiableToUser() {

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


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity quickLoginActivity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(getActivity(), ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    TastyToast.makeText(getActivity().getApplicationContext(),  "验证码已经发送，请注意查收", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(getActivity(), sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login_quick, container, false);
        ButterKnife.bind(this, view);
        createLink(tvRule);
        initListeners();
        return view;

    }

    public void initListeners() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        subscriberOnNextListener=new SubscriberOnNextListener<NewMerchant>() {
            @Override
            public void onNext(NewMerchant merchant) {
                saveToSP( merchant);
                SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TYPE, 2);
            }
        };
        smsSubscriberOnNextListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                TastyToast.makeText(getActivity(), "验证码已发送请注意查收", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }
        };
    }


    /**
     * 创建一个超链接
     */
    private void createLink(TextView tv) {
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString("我已阅读并同意《兼果用户协议》");
        // 设置超链接
        sp.setSpan(new URLSpan("http://101.200.205.243:8080/user_agreement.html"), 7, 15,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_bg)), 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
        tv.setTextSize(12);
        //设置TextView可点击
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void saveToSP(final NewMerchant user) {
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_WQTOKEN, user.getToken() != null ? user.getToken() : "");
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TEL, user.getTel() != null ? user.getTel() : "");
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getPassword() != null ? user.getPassword() : "");
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, user.getId());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_GROUP_IMG, user.getHead_img_url());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_MERCHANT_STATUS, user.getAuth_status());
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_PERMISSIONS, user.getStatus());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_QNTOKEN, user.getQiniu_token());
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_WQTOKEN, user.getToken() != null ? user.getToken() : "");
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TEL, user.getTel() != null ? user.getTel() : "");
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getPassword() != null ? user.getPassword() : "");
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, user.getLoginId());
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, user.getMerchantId());
//        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_MERCHANT_STATUS, user.getMerchantInfoStatus());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_PERMISSIONS, user.getBusiness_type());
        if (!TextUtils.isEmpty(String.valueOf(user.getId()))) {
            if (JPushInterface.isPushStopped(getActivity().getApplicationContext())) {
                JPushInterface.resumePush(getActivity().getApplicationContext());
            }
        }
            //登陆leancloud服务器 给极光设置别名
            LCChatKit.getInstance().open(String.valueOf(user.getId()), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null != e) {
                        Toast.makeText(getActivity(), "聊天服务启动失败，稍后请重新登录", Toast.LENGTH_SHORT).show();
                    }else {
                        //是否填写商家资料信息 1未填写 2 正在审核 3审核拒绝 4审核通过
                        if (user.getAuth_status()==0){
                            Intent intent = new Intent(getActivity(), ChooseActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else if (user.getAuth_status()==1||user.getAuth_status()==2){
                            Intent intent1 = new Intent(getActivity(), StatusActivity.class);
                            intent1.putExtra("type",user.getAuth_status());
                            startActivity(intent1);
                            getActivity().finish();
                        }else {
                            Intent intent1 = new Intent(getActivity(), MainActivity.class);
                            intent1.putExtra("login",true);
                            startActivity(intent1);
                            getActivity().finish();
                        }
                    }
                }
            });

    }


    @OnClick({R.id.sign_in_button, R.id.btn_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                String tel = phoneNumber.getText().toString().trim();
                String sms = phoneCode.getText().toString().trim();
                if (CheckStatus()) {
                    HttpMethods.getInstance().codeLogin(new ProgressSubscriber<NewMerchant>(subscriberOnNextListener,getActivity()),tel, sms);
                }
                break;
            case R.id.btn_get_code:
                String phone = phoneNumber.getText().toString();
                boolean isOK = phone.length() == 11;
                if (isOK) {
                    time.start();
                    HttpMethods.getInstance().sms(new BackgroundSubscriber<String>(smsSubscriberOnNextListener,getActivity()),MD5Util.MD5(phone),phone,"1");

                } else {
                    Toast.makeText(getActivity(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private boolean CheckStatus() {
        if (phoneNumber.getText().toString().trim().length() != 11) {
            Toast.makeText(getActivity(), "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneCode.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!cbRule.isChecked()) {
            Toast.makeText(getActivity(), "请阅读并确认《兼果用户协议》", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (btnGetCode!=null){
                btnGetCode.setClickable(false);
                btnGetCode.setBackgroundColor(Color.GRAY);
                btnGetCode.setText(millisUntilFinished / 1000 + "秒");
            }
        }

        @Override
        public void onFinish() {
            if (time!=null){
                btnGetCode.setText("验证码");
            }
            btnGetCode.setText("验证码");
            btnGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_login));
            btnGetCode.setClickable(true);
        }
    }




}

