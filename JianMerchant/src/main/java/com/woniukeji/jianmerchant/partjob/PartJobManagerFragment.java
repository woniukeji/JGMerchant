package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.JobInfo;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import ui.EmptyRecyclerView;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PartJobManagerFragment extends BaseFragment {

    /**
     * params1 0完成 1录取
     */
    private static String params1;
    @BindView(R.id.list)
    EmptyRecyclerView list;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private ViewGroup container;
    private boolean loadOk = true;
    BackgroundSubscriber<List<JobInfo>> subscriber;
    int startPosition =0;
    private int pageNum=1;


    private Context mContext = this.getActivity();
    private List<JobInfo> modleList = new ArrayList<>();
    private PartJobManagerAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private String tel;
    private int type = 1;//0完成 1录取
    private int lastVisibleItem;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public static PartJobManagerFragment newInstance(int pid) {
        //通过Bundle保存数据
        Bundle args = new Bundle();
        args.putInt(params1, pid);
        PartJobManagerFragment fragment = new PartJobManagerFragment();
        //将Bundle设置为fragment的参数
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(params1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        LogUtils.i("container",container.toString());
        View view = inflater.inflate(R.layout.fragment_part_manager, container, false);
        ButterKnife.bind(this, view);
        initview();
        return view;

    }

    private void initview() {
        adapter = new PartJobManagerAdapter(modleList, getActivity(), type);
        mLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.app_bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMerchantEmployStatus(String.valueOf(type), String.valueOf(pageNum));
            }
        });
        tel = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TEL, "");
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (modleList.size() > 4 && lastVisibleItem == modleList.size()&&modleList.size()%10==0&&loadOk) {
                    getMerchantEmployStatus(String.valueOf(type),String.valueOf(lastVisibleItem));
                    loadOk = false;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
       subscriber = new BackgroundSubscriber<>(new SubscriberOnNextListener<List<JobInfo>>() {
            @Override
            public void onNext(List<JobInfo> model) {
                if (refreshLayout!=null&& refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                if (model.size() > 0 && model != null && Integer.valueOf(startPosition) == 0) {
                    modleList.clear();
                } else if (model!=null&&model.size()<10){

                }
                modleList.addAll(model);
                adapter.notifyDataSetChanged();
                pageNum = modleList.size() / 10+1;
                loadOk = true;
            }
        }, getHoldingContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        getMerchantEmployStatus(String.valueOf(type),"0");
    }

    /**
     * 获取商家录录取和完成信息列表
     */
    private void getMerchantEmployStatus(String type, final String count) {
        startPosition= Integer.parseInt(count);
        long timeMillis=System.currentTimeMillis();
         String sign= MD5Util.getSign(getActivity(),timeMillis);
        HttpMethods.getInstance().getJobList(subscriber,tel,sign, String.valueOf(timeMillis),type, String.valueOf(pageNum));
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
