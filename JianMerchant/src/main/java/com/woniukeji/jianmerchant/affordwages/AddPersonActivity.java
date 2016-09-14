package com.woniukeji.jianmerchant.affordwages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.adapter.AddPersonAdapter;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.entity.AddPerson;
import com.woniukeji.jianmerchant.listener.ActivityExchange;
import com.woniukeji.jianmerchant.listener.ActivityExchangeManager;
import com.woniukeji.jianmerchant.utils.ActivityManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.AddFloatingActionButton;
import ui.EmptyRecyclerView;
import ui.PinnedHeaderItemDecoration;

public class AddPersonActivity extends BaseActivity implements ActivityExchange {

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
    @BindView(R.id.rl_null)
    RelativeLayout mRlNull;
    private ArrayList<AddPerson> mAddPersons = new ArrayList<>();
    private AddPersonAdapter mAdapter;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_person);
        //获取实例注册回掉方法
        ActivityExchangeManager.getInstance().registActivityExchangeManager(this);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("补录信息");
        emptyList.setEmptyView(mRlNull);
        emptyList.setHasFixedSize(true);
        emptyList.setLayoutManager(new LinearLayoutManager(this));
        emptyList.setItemAnimator(new DefaultItemAnimator());
        for (int i = 0; i < 10; i++) {
            mAddPersons.add(new AddPerson("高明"+i,"18645980292#" + i));
        }

        mAdapter = new AddPersonAdapter(this, mAddPersons);
        emptyList.setAdapter(mAdapter);
        PinnedHeaderItemDecoration itemDecoration = new PinnedHeaderItemDecoration();
        emptyList.addItemDecoration(itemDecoration);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        // TODO: 2016/9/13 网络拉去填充数据
        // TODO: 2016/9/13 手动添加的数据需要追加到最前面
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

    @Override
    public void exchangeDataSet(Bundle bundle) {
        String name = bundle.getString("name");
        String tel = bundle.getString("tel");
        AddPerson addPerson = new AddPerson(name, tel);
        mAddPersons.add(addPerson);
        //刷新数据
        mAdapter.notifyDataSetChanged();
    }
}
