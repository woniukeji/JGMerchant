package com.woniukeji.jianmerchant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.activity.HistoryRecords;
import com.woniukeji.jianmerchant.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FinancialManagementFragment extends BaseFragment {
    private static final String PARAM1 = "param1";
    @InjectView(R.id.img_back)
    ImageView imgBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_history)
    TextView tvHistory;
    @InjectView(R.id.today_wage)
    TextView todayWage;
    @InjectView(R.id.today_admint)
    TextView todayAdmint;
    @InjectView(R.id.month_wage)
    TextView monthWage;
    @InjectView(R.id.month_admint)
    TextView monthAdmint;
    @InjectView(R.id.total_wage)
    TextView totalWage;
    @InjectView(R.id.total_admint)
    TextView totalAdmint;

    private String mParam1;


    public FinancialManagementFragment() {
    }

    public static FinancialManagementFragment newInstance(String param1) {
        FinancialManagementFragment fragment = new FinancialManagementFragment();
        Bundle args = new Bundle();
        args.putString(PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financial_management, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void visiableToUser() {

    }

    @Override
    protected void firstVisiableToUser() {

    }

    @Override
    protected void onSaveInfoToBundle(Bundle outState) {

    }

    @Override
    protected void onRestoreInfoFromBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_title, R.id.tv_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            case R.id.tv_history:
                Intent intent = new Intent(getHoldingContext(), HistoryRecords.class);
                startActivity(intent);
                break;
        }
    }
}
