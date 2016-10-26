package com.woniukeji.jianmerchant.activity.certification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends BaseActivity {

    @BindView(R.id.img_personal) ImageView imgPersonal;
    @BindView(R.id.img_enterprise) ImageView imgEnterprise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {

        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        imgEnterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ChooseActivity.this,PersonalActivity.class);
                startActivity(intent);
            };
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }


    @OnClick({R.id.img_personal, R.id.img_enterprise})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_personal:
                startActivity(new Intent(this,PersonalActivity.class));
                break;
            case R.id.img_enterprise:
                startActivity(new Intent(this,PersonalActivity.class));
                break;
        }
    }
}
