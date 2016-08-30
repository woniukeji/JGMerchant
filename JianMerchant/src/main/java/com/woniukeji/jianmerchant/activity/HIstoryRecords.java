package com.woniukeji.jianmerchant.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.utils.ActivityManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HistoryRecords extends BaseActivity {


    @InjectView(R.id.img_back)
    ImageView imgBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.rv_history)
    RecyclerView rvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_hostory_records);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
//        rvHistory.setAdapter();
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setHasFixedSize(true);
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



    @OnClick({R.id.img_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_search:
                // TODO: 2016/8/29 以后做搜索或者筛选
                break;
        }
    }
}
