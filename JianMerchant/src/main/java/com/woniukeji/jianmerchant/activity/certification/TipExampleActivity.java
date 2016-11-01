package com.woniukeji.jianmerchant.activity.certification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.woniukeji.jianmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TipExampleActivity extends Activity {

    @BindView(R.id.top) ImageView top;
    @BindView(R.id.btn_iknow) Button btnIknow;
    int front;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        front=intent.getIntExtra("type",0);
        if (front==0){
            top.setImageResource(R.mipmap.idcard);
        }else if(front==1){
            top.setImageResource(R.mipmap.picture);
        }else if(front==2){
            top.setImageResource(R.mipmap.businesslicense);
        }

    }

    @OnClick(R.id.btn_iknow)
    public void onClick() {
             finish();

    }
}
