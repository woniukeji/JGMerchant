package com.woniukeji.jianmerchant.affordwages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.utils.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.AddFloatingActionButton;
import ui.EmptyRecyclerView;

public class AddPersonActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.empty_list)
    EmptyRecyclerView emptyList;
    @BindView(R.id.add_plus)
    AddFloatingActionButton addPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_person);
        ButterKnife.bind(this);
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



    @OnClick({R.id.img_back, R.id.complete, R.id.add_plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.complete:
                // TODO: 2016/9/8 完成逻辑
                break;
            case R.id.add_plus:
                Intent intent = new Intent(this, AddPersonDialog.class);
                startActivity(intent);
                break;
        }
    }
}
