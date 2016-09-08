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
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.DateUtils;
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
//    @InjectView(R.id.list)
//    SwipeMenuRecyclerView list;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_DELETE_SUCCESS = 5;
    private int MSG_DELETE_FAIL = 6;
        private Handler mHandler = new Myhandler(this.getActivity());
    private ViewGroup container;

    private class Myhandler extends Handler {
    private WeakReference<Context> reference;

    public Myhandler(Context context) {
        reference = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                if (refreshLayout!=null&& refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                if (msg.arg1==0){
                    modleList.clear();
                }
                BaseBean<Model> modelBaseBean= (BaseBean<Model>) msg.obj;
                modleList.addAll(modelBaseBean.getData().getList_t_job());
                adapter.notifyDataSetChanged();
                break;
            case 1:
                if ( refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                String ErrorMessage = (String) msg.obj;
                Toast.makeText(getActivity(), ErrorMessage, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                break;
            case 3:
                String sms = (String) msg.obj;
                Toast.makeText(getActivity(), sms, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                String mes = (String) msg.obj;
                Toast.makeText(getActivity(), mes, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

    private Context mContext = this.getActivity();
    private List<Model.ListTJobEntity> modleList = new ArrayList<>();
    private PartJobManagerAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int merchant_id;
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
        list.setEmptyView(LayoutInflater.from(getHoldingContext()).inflate(R.layout.null_content,container,false));
        list.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.app_bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMerchantEmployStatus(String.valueOf(type),"0");
            }
        });
        merchant_id = (int) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.USER_MERCHANT_ID, 0);//userinfo sp文件中保存着3


        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (modleList.size() > 4 && lastVisibleItem == modleList.size()) {
                    getMerchantEmployStatus(String.valueOf(type),String.valueOf(lastVisibleItem));
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
    public void onResume() {
        super.onResume();
        getMerchantEmployStatus(String.valueOf(type),"0");
    }

    /**
     * 获取商家录录取和完成信息列表
     */
    private void getMerchantEmployStatus(String type, final String count) {
        BackgroundSubscriber<Model> subscriber = new BackgroundSubscriber<>(new SubscriberOnNextListener<Model>() {
            @Override
            public void onNext(Model model) {
                if (refreshLayout!=null&& refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                if (model.getList_t_job().size() > 0 && model != null && Integer.valueOf(count) == 0) {
                    modleList.clear();
                } else if (model!=null&&model.getList_t_job().size()<10){


                }
                modleList.addAll(model.getList_t_job());
                adapter.notifyDataSetChanged();
            }
        }, getHoldingContext());
        HttpMethods.getInstance().merchantEmployStatus(subscriber,String.valueOf(merchant_id),count,type);
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


    /**
     * postInfo
     */
    public void getJobs(String merchantId, String status, final String count) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());

        OkHttpUtils
                .get()
                .url(Constants.GET_PART_JOB_PUBLISH)
                .addParams("only", only)
                .addParams("merchant_id", merchantId)
                .addParams("count", count)
                .addParams("status", status)
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<Model>>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Model>>() {
                        }.getType());
                        return baseBean;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_GET_FAIL;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(BaseBean baseBean, int id) {
                        if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                            Message message = new Message();
                            message.obj = baseBean;
                            message.arg1 = Integer.parseInt(count);
                            message.what = MSG_GET_SUCCESS;
                            mHandler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }
                    }

                });
    }


}
