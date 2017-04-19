package com.woniukeji.jianmerchant.payWage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.listener.ActivityExchangeManager;
import com.woniukeji.jianmerchant.utils.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPersonDialog extends BaseActivity {

    @BindView(R.id.add_name)
    EditText addName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.add_tel)
    EditText addTel;
    @BindView(R.id.rl_bg)
    RelativeLayout rlBg;
    @BindView(R.id.exit)
    ImageView exit;
    @BindView(R.id.confirm_add)
    ImageView confirmAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_dialog);
        Intent intent = getIntent();
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);
    }

    @OnClick({R.id.confirm_add, R.id.exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_add:
                String name = addName.getText().toString().trim();
                String tel = addTel.getText().toString().trim();
                //发送数据
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("tel", tel);
                ActivityExchangeManager.getInstance().sendDataSet(bundle);
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}