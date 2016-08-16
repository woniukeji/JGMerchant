package com.woniukeji.jianmerchant.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment
 *
 * @author invinjun
 */
public abstract class BaseFragment extends Fragment {

    private boolean isFirstVisiable =true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Context getHoldingContext() {
        return context;
    }

    private Context context;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //可见true 不可见false
        if (isVisibleToUser) {//可见
            if (isFirstVisiable) {
                isFirstVisiable = false;
                firstVisiableToUser();
            } else {
                visiableToUser();
            }
        } else {//不可见，这里不做什么操作了

        }
    }

    /**
     * 用户可以看见，
     * 这种情况包括：
     * 当前fragment后台未被摧毁，再次出现;
     * 当前fragment后台摧毁后，再次出现.
     */
    protected abstract void visiableToUser();

    /**
     * 第一次对用户可见，并且是在onCreateView方法之前，onCreate之后，
     * 可以根据需求都可以在次方法中做些操作，一般不做操作
     */
    protected abstract void firstVisiableToUser();
}
