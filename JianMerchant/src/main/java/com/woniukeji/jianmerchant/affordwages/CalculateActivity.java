package com.woniukeji.jianmerchant.affordwages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.adapter.CalculateViewHolder;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.eventbus.PayPassWordEvent;
import com.woniukeji.jianmerchant.eventbus.UserWagesEvent;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import ui.EmptyRecyclerView;

public class CalculateActivity extends BaseActivity implements CalculateViewHolder.ChangeMoney {

    @BindView(R.id.list)
    EmptyRecyclerView list;
    @BindView(R.id.tv_job_name)
    TextView tvJobName;
    @BindView(R.id.tv_job_date)
    TextView tvJobDate;
    @BindView(R.id.ll_jobname)
    LinearLayout llJobname;
    @BindView(R.id.tv_title_sum)
    TextView tvTitleSum;
    @BindView(R.id.tv_job_wages)
    TextView tvJobWages;
    @BindView(R.id.btn_change_wages)
    Button btnChangeWages;
    @BindView(R.id.ll_jobinfo)
    LinearLayout llJobinfo;
    @BindView(R.id.ch_all)
    CheckBox chAll;
    @BindView(R.id.btn_pay_wages)
    Button btnPayWages;
    @BindView(R.id.tv_wages_sum)
    TextView tvWagesSum;
    @BindView(R.id.tv_choose_sum)
    TextView tvChooseSum;
    @BindView(R.id.top)
    Toolbar top;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private Context mContext = CalculateActivity.this;
    private CalculateAdapter adapter;
    private int merchantid;
    private List<Boolean> isSelected = new ArrayList<>();
    private List<AffordUser.ListTUserInfoEntity> userList = new ArrayList<>();
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    private String jobid;
    private String moneyStr;
    private String jobName;
    private double money;
    private String str;
    private ActionBar actionBar;
    private ImageView back;
    private TextView title;
    private TextView add_person;
    private int lastSize;
    private View mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.btn_change_wages, R.id.ch_all, R.id.btn_pay_wages})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_wages:
                showSweetDialog();
                break;
            case R.id.btn_pay_wages:
                if (checkStatus()) {
                    Mdialog mdialog = new Mdialog(mContext);
                    mdialog.show();
                }
                break;
        }
    }

    /**
     * 检查是否有勾选
     */
    private boolean checkStatus() {
        Boolean b = false;
        for (int i = 0; i < isSelected.size(); i++) {
            if (isSelected.get(i)) {
                b = true;
                return true;
            }

        }
        if (!b) {
            showShortToast("请至少勾选一人进行结算");
        }
        return false;
    }

    private void showSweetDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("修改应发工资")
                .setInputType("number")
                .setContentEdit("请输入应发工资数目")
                .setCancelText("取消")
                .setConfirmText("确定")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                        money = Double.parseDouble(sweetAlertDialog.getEditContent());
                        tvJobWages.setText(money + str);
                        for (int i = 0; i < userList.size(); i++) {
                            userList.get(i).setReal_money(money);
                        }
                        Calculate();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onChangeMoney(String money, AffordUser.ListTUserInfoEntity user, int position) {
        Intent intent = new Intent(mContext, ChangeActivity.class);
        intent.putExtra("money", money);
        intent.putExtra("user", user);
        intent.putExtra("position", position);
        startActivityForResult(intent, 1);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pay_wages);
        ButterKnife.bind(this);
        setSupportActionBar(top);
        actionBar = getSupportActionBar();
        mEmptyView = findViewById(R.id.null_content);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        jobName = intent.getStringExtra("name");
        moneyStr = intent.getStringExtra("money");
        str = moneyStr.substring(moneyStr.indexOf("/"));
        money = Double.parseDouble(moneyStr.substring(0, moneyStr.indexOf("/")));
        jobid = intent.getStringExtra("jobid");
    }

    @Override
    public void initViews() {
        setupToolBar();
        tvJobName.setText(jobName);
        tvJobWages.setText(moneyStr);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEmployeeInfo(0);
            }
        });
        adapter = new CalculateAdapter(userList, isSelected, this, String.valueOf(money));
        adapter.setChangeMoney(this);
        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        LogUtils.i("calculateActivity",list.getParent().toString());
        list.setEmptyView(mEmptyView);
        list.setAdapter(adapter);
    }

    private void setupToolBar() {
        View view = View.inflate(this, R.layout.app_title_bar_new, null);
        top.addView(view, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        top.setContentInsetsAbsolute(0, 0);
        back = (ImageView) top.findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title = (TextView) top.findViewById(R.id.tv_title);
        title.setText("结算");
        add_person = (TextView) top.findViewById(R.id.add_person);
        add_person.setVisibility(View.GONE);
        add_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculateActivity.this, AddPersonActivity.class);
                intent.putExtra("job_id", jobid);
                intent.putExtra("job_name", jobName);
                //这里不用forResult启动，用eventbus代替
                startActivity(intent);
            }
        });
    }

    @Override
    public void initListeners() {
        chAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < isSelected.size(); i++) {
                        isSelected.set(i, true);
                    }
                } else {
                    for (int i = 0; i < isSelected.size(); i++) {
                        isSelected.set(i, false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initData() {
        merchantid = (int) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_MERCHANT_ID, 0);
        getEmployeeInfo(0);
    }

    /**
     * 获取结算人员信息表
     */
    private void getEmployeeInfo(final int count) {
        ProgressSubscriber<AffordUser> subscriber = new ProgressSubscriber<>(new SubscriberOnNextListener<AffordUser>() {
            @Override
            public void onNext(AffordUser affordUser) {
                int size = affordUser.getList_t_user_info().size();
                lastSize = userList.size();
                if (count == 0) {
                    //首次获取
                    userList.clear();
                    userList.addAll(affordUser.getList_t_user_info());
                    for (int i = 0; i < size; i++) {
                        isSelected.add(i, false);
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        isSelected.add(userList.size() + i, false);
                    }
                    userList.addAll(affordUser.getList_t_user_info());
                }
                for (int i =lastSize; i < userList.size(); i++) {
                    userList.get(i).setReal_money(money);
                }
                tvTitleSum.setText("总计" + affordUser.getUser_sum() + "人");
                adapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        }, this, false);
        HttpMethods.getInstance().paywage(subscriber, jobid, String.valueOf(count));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (userList.size() > 5 && lastVisibleItem == userList.size() && userList.size() % 10 == 0) {
                    getEmployeeInfo(lastVisibleItem);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);
    }

    public void onEvent(UserWagesEvent event) {
        Calculate();
    }

    private void Calculate() {
        int sum = 0;
        int person = 0;
        for (int i = 0; i < userList.size(); i++) {
            if (isSelected.get(i)) {
                sum += userList.get(i).getReal_money();
                person++;
            }
        }
        tvWagesSum.setText("合计：" + sum + "元");
        tvChooseSum.setText("选中" + person + "人");
    }

    /**
     * 当确认结算并且密码输入正确之后会走这里
     */
    public void onEventMainThread(PayPassWordEvent event) {

        if (event.isCorrect) {
            Map map = new HashMap();
            ArrayList list = new ArrayList();
            for (int i = 0; i < userList.size(); i++) {
                if (isSelected.get(i)) {
                    list.add(userList.get(i));
                }
            }
            map.put("list_t_wages_Bean", list);
            String json = new Gson().toJson(map);
            checkout(jobid, json);
        }
    }

    /**
     * 确认结算
     */
    private void checkout(String jobid, String json) {
        ProgressSubscriber<BaseBean> subscriber = new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                Intent intent = new Intent(mContext, FinishActivity.class);
                intent.putExtra("sum", baseBean.getSum());
                startActivityForResult(intent, 0);
            }
        }, mContext, false){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                finish();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                Toast.makeText(mContext, "结算成功", Toast.LENGTH_SHORT).show();
            }
        };
        HttpMethods.getInstance().checkout(subscriber, jobid, json);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//修改界面返回值
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);
                AffordUser.ListTUserInfoEntity user = (AffordUser.ListTUserInfoEntity) data.getSerializableExtra("user");
                userList.set(position, user);
                Calculate();
                LogUtils.e("json", "修改" + user.getReal_money());
                adapter.notifyDataSetChanged();
            }
        } else {
            if (resultCode == RESULT_OK) {
                //结算成功后，重新拉取列表
                getEmployeeInfo(0);
            }
        }
    }
}
