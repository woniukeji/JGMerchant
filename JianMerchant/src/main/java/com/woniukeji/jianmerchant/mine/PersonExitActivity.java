package com.woniukeji.jianmerchant.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.login.LoginActivity;
import com.woniukeji.jianmerchant.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class PersonExitActivity extends Activity {

    @BindView(R.id.btn_logout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_exit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_logout)
    public void onClick() {
        SPUtils.deleteParams(this);
        startActivity(new Intent(PersonExitActivity.this,LoginActivity.class));
        finish();
    }
}
