package com.woniukeji.jianmerchant.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;

public class FinancialManagementFragment extends BaseFragment {
    private static final String PARAM1 = "param1";

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
        return inflater.inflate(R.layout.fragment_financial_management, container, false);
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

}
