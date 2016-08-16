package com.woniukeji.jianmerchant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woniukeji.jianmerchant.base.BaseFragment;

public class MainVPFragment extends BaseFragment {
    private static final String TAG = "Fragment";
    private static final String PARAM = "param";

    private String mParam;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(PARAM);
        }
        Log.i(TAG, "onCreate: "+getArguments().getString(PARAM));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: "+getArguments().getString(PARAM));
        TextView textView = new TextView(getActivity());
        textView.setText(mParam+"页");
        textView.setTextSize(100);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: "+getArguments().getString(PARAM));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: "+getArguments().getString(PARAM));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: "+getArguments().getString(PARAM));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: "+getArguments().getString(PARAM));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: "+getArguments().getString(PARAM));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: "+getArguments().getString(PARAM));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: "+getArguments().getString(PARAM));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: "+getArguments().getString(PARAM));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: "+getArguments().getString(PARAM));
    }

    public static MainVPFragment newInstance(String param) {
        MainVPFragment fragment = new MainVPFragment();
        Bundle args = new Bundle();
        args.putString(PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //可见true 不可见false
        Log.i(TAG, "setUserVisibleHint: "+isVisibleToUser+"      "+getArguments().getString(PARAM));
    }

    @Override
    protected void visiableToUser() {

    }

    @Override
    protected void firstVisiableToUser() {

    }


}
