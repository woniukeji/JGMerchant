package com.woniukeji.jianmerchant.activity.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.utils.TimeCount;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class ForgetPassActivity extends BaseActivity {

    @BindView(R.id.phoneNumber) EditText phoneNumber;
    @BindView(R.id.btn_get_code) Button btnGetCode;
    @BindView(R.id.phoneCode) EditText phoneCode;
    @BindView(R.id.passWord1) EditText passWord1;
    @BindView(R.id.passWord2) EditText passWord2;
    @BindView(R.id.phone_sign_in_button) Button phoneSignInButton;
    @BindView(R.id.email_login_form) LinearLayout emailLoginForm;
    @BindView(R.id.user_rule) LinearLayout userRule;
    @BindView(R.id.cb_rule) CheckBox cbRule;
    @BindView(R.id.tv_rule) TextView tvRule;
    private String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
    private Handler mHandler = new Myhandler(this);
    private SubscriberOnNextListener<String> smsSubscriberOnNextListener;
    private SubscriberOnNextListener<String> subscriberOnNextListener;
    @OnClick(R.id.tv_rule)
    public void onClick() {
    }

    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ForgetPassActivity registActivity = (ForgetPassActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    Intent intent = new Intent(registActivity, MainActivity.class);
//                    registActivity.startActivity(intent);
                    registActivity.showShortToast("密码修改成功，请重新登录");
                    SPUtils.deleteParams(registActivity);
                    registActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    TastyToast.makeText(registActivity, ErrorMessage, TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    TastyToast.makeText(registActivity, sms, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        userRule.setVisibility(View.GONE);
    }

    @Override
    public void initListeners() {
        subscriberOnNextListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String message) {
                    TastyToast.makeText(ForgetPassActivity.this, "密码修改成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }
        };
        smsSubscriberOnNextListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                TastyToast.makeText(ForgetPassActivity.this, "验证码已发送请注意查收", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(ForgetPassActivity.this);
    }

    /**
     * 创建一个超链接
     */
    private void createLink(TextView tv) {
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString("我已阅读并同意《兼果用户协议》");
        // 设置超链接
        sp.setSpan(new URLSpan("http://inke.tv/privacy/privacy.html"), 7, 15,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
        //设置TextView可点击
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.img_back, R.id.btn_get_code, R.id.phone_sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_get_code:
                String tel = phoneNumber.getText().toString();
                boolean isOK = tel.length()==11;
                if (isOK) {
//                    showShortToast("正在发送验证码，请稍后");
                    new TimeCount(60000, 1000,btnGetCode).start();//构造CountDownTimer对象
                    HttpMethods.getInstance().sms(new BackgroundSubscriber<String>(smsSubscriberOnNextListener,this),MD5Util.MD5(tel),tel,"2");
                } else {
                    showLongToast("请输入正确的手机号");
                }

                break;
            case R.id.phone_sign_in_button:
                String phone = phoneNumber.getText().toString();
                String pass = passWord2.getText().toString();
                String code=phoneCode.getText().toString();
                if (CheckStatus()) {
                    HttpMethods.getInstance().passReset(new BackgroundSubscriber<String>(subscriberOnNextListener,this),phone,code,pass);
                }
                break;
        }
    }

    private boolean CheckStatus() {

        if (phoneNumber.getText().toString().equals("")) {
            showShortToast("手机号不能为空");
            return false;
        } else if (phoneNumber.getText().toString().length()!=11) {
            showShortToast("手机号码格式不正确");
            return false;
        } else if (passWord1.getText().toString().equals("")) {
            showShortToast("密码不能为空");
            return false;
        } else if (passWord2.getText().toString().equals("")) {
            showShortToast("请再次输入密码");
            return false;
        } else if (passWord1.getText().toString().trim().length() < 6) {
            showShortToast("您的密码太短了");
            return false;
        } else if (!passWord1.getText().toString().trim().equals(passWord2.getText().toString().trim())) {
            showShortToast("您两次输入的密码不同！");
            return false;
        } else if (phoneCode.getText().toString().equals("")) {
            showShortToast("验证码不能为空");
            return false;
        }
        return true;
    }




    }


