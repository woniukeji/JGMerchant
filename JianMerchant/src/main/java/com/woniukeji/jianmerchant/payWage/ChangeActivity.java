package com.woniukeji.jianmerchant.payWage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class ChangeActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.img_head) ImageView imgHead;
    @BindView(R.id.tv_user_name) TextView userName;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.ll_publish_time) LinearLayout llPublishTime;
    @BindView(R.id.tv_wages) TextView tvWages;
    @BindView(R.id.ll_wages) LinearLayout llWages;
    @BindView(R.id.rl_info) RelativeLayout rlInfo;
    @BindView(R.id.tv_base_wages) TextView tvBaseWages;
    @BindView(R.id.et_pay_wages) EditText etPayWages;
    @BindView(R.id.tv_change_sum) TextView tvChangeSum;
    @BindView(R.id.et_note) EditText etNote;
    @BindView(R.id.btn_confirm_change) Button btnConfirmChange;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = ChangeActivity.this;
    AffordUser.ListBean user;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }



    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChangeActivity activity = (ChangeActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(activity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_wages);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        Intent intent=getIntent();
        user= (AffordUser.ListBean) intent.getSerializableExtra("user");
        String money=intent.getStringExtra("money");
        position=intent.getIntExtra("position",0);
        if (user.getName()!=null&&!user.getName().equals("0")){
          userName.setText(user.getName());
        }else {
           userName.setText("未实名");
        }
        tvPhone.setText(user.getTel());
        tvBaseWages.setText(money+"元");

        Picasso.with(ChangeActivity.this).load(user.getHead_img_url()).transform(new CropCircleTransfermation()).error(R.drawable.default_head).into(imgHead);


    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }

    @OnClick({R.id.img_back, R.id.btn_confirm_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_confirm_change:
                Intent intent=getIntent();
                user.setMoney(Double.parseDouble(etPayWages.getText().toString().trim()));
                user.setNote(etNote.getText().toString().trim());
                intent.putExtra("user",user);
                intent.putExtra("position",position);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent=getIntent();
            setResult(RESULT_CANCELED,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
