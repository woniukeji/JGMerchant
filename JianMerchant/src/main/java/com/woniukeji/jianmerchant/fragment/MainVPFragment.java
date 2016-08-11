package com.woniukeji.jianmerchant.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woniukeji.jianmerchant.base.BaseFragment;

public class MainVPFragment extends BaseFragment {
    private static final String PARAM = "param";

    private String mParam;

    public static MainVPFragment newInstance(String param) {
        MainVPFragment fragment = new MainVPFragment();
        Bundle args = new Bundle();
        args.putString(PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(mParam+"页");
        textView.setTextSize(100);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }


}
