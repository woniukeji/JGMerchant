package com.woniukeji.jianmerchant.login;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.SmsCode;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.utils.TimeCount;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * Created by Se7enGM on 2016/8/25.
 */
public class RegistActivity extends BaseActivity {
    private Context mContext = RegistActivity.this;
    private static final int RESULT_CODE = 2;
    private SmsCode smsCode;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.phoneCode)
    EditText phoneCode;
    @BindView(R.id.passWord1)
    EditText passWord1;
    @BindView(R.id.passWord2)
    EditText passWord2;
    @BindView(R.id.cb_rule)
    CheckBox cbRule;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.user_rule)
    LinearLayout userRule;
    @BindView(R.id.phone_sign_in_button)
    Button phoneSignInButton;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

    }

    @Override
    public void initViews() {
        tvTitle.setText("注册账户");
        imgShare.setVisibility(View.GONE);
        userRule.setVisibility(View.VISIBLE);
//        MyTextWather watcher = new MyTextWather();
//        phoneNumber.addTextChangedListener(watcher);
        declarationLink(tvRule);
    }
    /**
     * 点击声明条款
     */
    private void declarationLink(TextView tv) {
        SpannableString sp = new SpannableString("我已阅读并同意《兼果用户协议》");
        // 设置超链接
//        sp.setSpan(new URLSpan("http://inke.tv/privacy/privacy.html"), 7, 15,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_bg)), 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setTextSize(12);
        tv.setText(sp);
//        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void initListeners() {
        imgBack.setOnClickListener(this);
        phoneSignInButton.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(RegistActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //保存状态信息
                finish();
                break;
            case R.id.phone_sign_in_button:
                //1、判断
                if (CheckAll()) {
                    String phone = phoneNumber.getText().toString().trim();
                    String pass = passWord2.getText().toString().trim();
//                    注册
                    BackgroundSubscriber<User> subscriber = new BackgroundSubscriber<>(new SubscriberOnNextListener<User>() {
                        @Override
                        public void onNext(User user) {
                            if (user!=null){
                                saveToSP(user);
                                //2、提示注册成功并且将手机号返回给刚才的登陆页面
                                Intent intent = new Intent();
                                intent.putExtra("tel", phoneNumber.getText().toString().trim());
                                setResult(RESULT_CODE,intent);
                                showShortToast("注册成功");
                                finish();
                            }
                        }
                    },mContext);
                    HttpMethods.getInstance().registAccount(subscriber,phone,MD5Util.MD5(pass));

                }

                break;
            case R.id.btn_get_code:
                String tel = phoneNumber.getText().toString();
                if (isMobile(tel)) {
                    new TimeCount(60000, 1000,btnGetCode).start();//构造CountDownTimer对象
                    //获取验证码
                    BackgroundSubscriber<SmsCode> subscriber = new BackgroundSubscriber<>(new SubscriberOnNextListener<SmsCode>() {
                        @Override
                        public void onNext(SmsCode smsCode) {
                            if (smsCode.getIs_tel().equals("1")) {
                                showShortToast("该手机已注册，请登录！");
                            } else {
                                showShortToast("验证码已发送，请查收");
                            }

                        }
                    },mContext);
                    HttpMethods.getInstance().getCodes(subscriber,tel);
                } else {
                    showLongToast("请输入正确的手机号");
                }
                break;
        }
    }
    private void saveToSP(User user) {
        SPUtils.setParam(mContext, Constants.LOGIN_INFO, Constants.SP_TEL, user.getT_user_login().getTel() != null ? user.getT_user_login().getTel() : "");
        SPUtils.setParam(mContext, Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getT_user_login().getPassword() != null ? user.getT_user_login().getPassword() : "");
        SPUtils.setParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, user.getT_user_login().getId());
        SPUtils.setParam(mContext, Constants.LOGIN_INFO, Constants.SP_STATUS, user.getT_user_login().getStatus());
        SPUtils.setParam(mContext, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, user.getT_user_login().getQiniu());
        SPUtils.setParam(mContext, Constants.USER_INFO, Constants.USER_NICK, user.getT_merchant().getName() != null ? user.getT_merchant().getName() : "");
        SPUtils.setParam(mContext, Constants.USER_INFO, Constants.SP_MERCHANT_ID, user.getT_merchant().getId());
        SPUtils.setParam(mContext, Constants.USER_INFO, Constants.USER_PAY_PASS, user.getT_merchant().getPay_password());
        SPUtils.setParam(mContext, Constants.USER_INFO, Constants.USER_IMG, user.getT_merchant().getName_image() != null ? user.getT_merchant().getName_image() : "");
    }


    /**
     * 检查填写的信息是否正确
     */
    private boolean CheckAll() {
        if (!isMobile(phoneNumber.getText().toString().trim())||phoneNumber.getText().toString().trim().equals("")) {
            showShortToast("输入正确的手机号");
            return false;
        }else if (phoneCode.getText().toString().trim().length() != 6 || phoneCode.getText().toString().trim().equals("")) {
            showShortToast("请输入正确的验证码");
            return false;
        } else if (passWord1.getText().toString().trim().equals("")||passWord2.getText().toString().trim().equals("")) {
            showShortToast("请输入密码");
            return false;
        } else if (!passWord1.getText().toString().trim().equals(passWord2.getText().toString().trim())) {
            showShortToast("两次密码不一致");
            return false;
        } else if (passWord1.getText().toString().trim().length() < 6) {
            showShortToast("密码太短了");
            return false;
        } else if (!cbRule.isChecked()) {
            showShortToast("注册需同意兼果协议");
            return false;
        }
        return true;
    }


//    class MyTextWather implements TextWatcher {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            if (!isMobile(s.toString())) {
//                showShortToast("请输入正确的手机号码！");
//            }
//        }
//    }

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}$";
//    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

}
