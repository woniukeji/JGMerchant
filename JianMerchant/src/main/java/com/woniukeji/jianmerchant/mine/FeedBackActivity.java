package com.woniukeji.jianmerchant.mine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.et_content) EditText etContent;
    @BindView(R.id.et_contact) EditText etContact;
    @BindView(R.id.btn_confirm) Button btnConfirm;
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private Handler mHandler = new Myhandler(this);
    private String tel;
    private SubscriberOnNextListener<String> stringSubscriberOnNextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        stringSubscriberOnNextListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                TastyToast.makeText(FeedBackActivity.this,"反馈提交成功，我们会及时处理您的反馈的！",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                finish();
            }
        };
    }

    @Override
    public void initData() {
        tel = (String) SPUtils.getParam(FeedBackActivity.this, Constants.LOGIN_INFO, Constants.SP_TEL, "");
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(FeedBackActivity.this);
    }



    @OnClick({R.id.img_back, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_confirm:
                if (etContent.getText().toString().equals("")){
                    showShortToast("请输入反馈内容");
                    return;
                }else if(etContact.getText().toString().equals("")){
                    showShortToast("请输入手机号");
                    return;
                }
                HttpMethods.getInstance().postFeedback(FeedBackActivity.this,new ProgressSubscriber<String>(stringSubscriberOnNextListener,FeedBackActivity.this),tel,etContent.getText().toString());
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
            FeedBackActivity jobDetailActivity = (FeedBackActivity) reference.get();
            switch (msg.what) {
                case 0:
                    String ErrorMessage1 = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, ErrorMessage1, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String sms = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    String sms1 = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, sms1, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }


        /**
         * postInfo
         */
        public void postOpinion(String tel, String text) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.FEED_BACK)
                    .addParams("only", only)
                    .addParams("tel", tel)
                    .addParams("text", text)
                    .build()
                    .connTimeOut(6000)
                    .readTimeOut(2000)
                    .writeTimeOut(2000)
                    .execute(new Callback<BaseBean>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response ,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean,int id) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
}
