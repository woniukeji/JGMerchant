package com.woniukeji.jianmerchant.payWage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.adapter.AlreadyCalculateAdapter;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.EmptyRecyclerView;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AlreadyCalculateActivity extends BaseActivity {


//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.need_back)
//    ImageView needBack;
//    @BindView(R.id.top)
//    RelativeLayout top;
//    @BindView(R.id.rv_list)
//    EmptyRecyclerView rvList;
//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager recycleManager;
    private int lastSize;
    private List<AffordUser.ListBean> userList = new ArrayList<>();
    private AlreadyCalculateAdapter adapter;
    private boolean loadOk = true;
    private String tel;
    private long userId;
    private long jobid;
    private int lastVisibleItem;
    private ImageView needBack;
    private EmptyRecyclerView rvList;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_alreadycalculate);

    }

    @Override
    public void initViews() {
        needBack = (ImageView) findViewById(R.id.need_back);
        needBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvList = (EmptyRecyclerView) findViewById(R.id.rv_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        recycleManager = new LinearLayoutManager(this);
        recycleManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(recycleManager);
        adapter = new AlreadyCalculateAdapter(AlreadyCalculateActivity.this, userList);
        rvList.setAdapter(adapter);

    }

    @Override
    public void initListeners() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEmployeeInfo(0);
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        jobid = intent.getLongExtra("jobid", 0);
        tel = (String) SPUtils.getParam(AlreadyCalculateActivity.this, Constants.LOGIN_INFO, Constants.SP_TEL, "");
        userId = (long) SPUtils.getParam(AlreadyCalculateActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0L);
        getEmployeeInfo(0);
    }

    @Override
    public void addActivity() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (userList.size() > 5 && lastVisibleItem == userList.size() && userList.size() % 10 == 0 && loadOk) {
                    getEmployeeInfo(lastVisibleItem);
                    loadOk = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = recycleManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 获取结算人员信息表
     */
    private void getEmployeeInfo(final int count) {
        ProgressSubscriber<AffordUser> subscriber = new ProgressSubscriber<>(new SubscriberOnNextListener<AffordUser>() {
            @Override
            public void onNext(AffordUser affordUser) {
                int size = affordUser.getList().size();
                lastSize = userList.size();
                if (count == 0) {
                    //首次获取
                    userList.clear();
                    userList.addAll(affordUser.getList());
//                    for (int i = 0; i < size; i++) {
////                        isSelected.add(i, false);
//                    }
                } else {
                    userList.addAll(affordUser.getList());

//                    if (userList.size()<10){
//                        adapter.setFooterChange(true);
//                    }
//                    for (int i = lastSize; i < userList.size(); i++) {
////                        isSelected.add(i, false);
//                    }
                }
//                for (int i =0; i < userList.size(); i++) {
//                    userList.get(i).setMoney(money);
//                }
//                tvTitleSum.setText("总计" + affordUser.getTotal() + "人");
                if (userList.size() % 10 < 10 ){
                    adapter.setFooterChange(true);
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
                loadOk = true;
            }
        }, this, false);
//        isRefresh=true;
        long times = System.currentTimeMillis();
        String sign = MD5Util.getSign(AlreadyCalculateActivity.this, times);
        HttpMethods.getInstance().getPayList(subscriber, tel, sign, String.valueOf(times), String.valueOf(jobid), count / 10 + 1, 2);
    }


    @Override
    public void onClick(View v) {

    }
}
