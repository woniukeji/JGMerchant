package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.NewJobDetail;
import com.woniukeji.jianmerchant.entity.NewJoinUser;
import com.woniukeji.jianmerchant.eventbus.FilterEvent;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.ExcelUtil;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import ui.EmptyRecyclerView;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class FilterFragment extends BaseFragment {

    private static String params1 = "type";
    private static String params2 = "jobid";
    @BindView(R.id.list)
    EmptyRecyclerView list;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.btn_out_info) TextView btnOutInfo;
    @BindView(R.id.null_content) View mEmptyView;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context mContext = this.getActivity();
    private List<NewJoinUser> modleList = new ArrayList<>();
    private int lastVisibleItem;
    //    private FilterAdapter adapter;
    private NewNewFilterAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int mPosition;
    private int type = 1;
    private String jobid;
    private String jobName;
    private boolean loadOk=false;
    private ProgressSubscriber<BaseBean> subscriber;
    private SubscriberOnNextListener<BaseBean> listenner;
    private SubscriberOnNextListener<List<NewJoinUser>> userListSubOnNextListener;
    private android.view.ActionMode mActionMode;
    private long merchantId;
    private ViewGroup container;
    private String tel;
    private boolean isRefresh=true;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_out_info)
    public void onClick() {
        exportUser();
    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(getActivity(), sms, Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    String me = (String) msg.obj;
                    Toast.makeText(getActivity(), me, Toast.LENGTH_SHORT).show();
                    FilterEvent filterEvent = new FilterEvent();
                    EventBus.getDefault().post(filterEvent);
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
    public static FilterFragment newInstance(int type, String jobid) {
        Bundle args = new Bundle();
        args.putInt(params1, type);
        args.putString(params2, jobid);
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static FilterFragment newInstance(int type, String jobid,String jobname) {
        Bundle args = new Bundle();
        args.putInt(params1, type);
        args.putString(params2, jobid);
        args.putString("jobname", jobname);
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(params1);
        jobid = getArguments().getString(params2);
        jobName = getArguments().getString("jobname");
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        View view = inflater.inflate(R.layout.fragment_admit, container, false);
        ButterKnife.bind(this, view);
        initview();
        return view;

    }


    private void initview() {
        adapter = new NewNewFilterAdapter(modleList,getActivity(),type,jobid);
        tel = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TEL, "");
        merchantId=(long) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0l);
        adapter.setEnrollOrRefuseClickListener(new EnrollOrRefuseClickListener() {
            @Override
            public void onClick(final int position, long userId, int type, View view) {
                ProgressSubscriber<BaseBean> subscriber1= new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean != null) {
                            modleList.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getHoldingContext(),baseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },getHoldingContext(),false);
                long times=System.currentTimeMillis();
                String sign= MD5Util.getSign(getActivity(),times);
                HttpMethods.getInstance().admitOrRefuseUser(subscriber1,tel,sign,String.valueOf(times),jobid, String.valueOf(userId),type);
                           }

        });
//        adapter.setFilterItemClickListener(new FilterItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//            }
//
//            @Override
//            public boolean onItemLongClick(final int position, View view, final int login_id) {
//                //用popupwindow
//                PopupUtils popupUtils = new PopupUtils(getHoldingContext());
//                popupUtils.setOnSetupDove(new PopupUtils.onSetupDove() {
//                    @Override
//                    public void onSetup(View v) {
//                        if (type != 1) {
//                            Toast.makeText(getHoldingContext(), "未录用,不能标记为鸽子!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            BackgroundSubscriber<Pigeon> subscriber = new BackgroundSubscriber<Pigeon>(new SubscriberOnNextListener<Pigeon>() {
//
//                                @Override
//                                public void onNext(Pigeon pigeon) {
//                                    modleList.get(position).setPigeon_count(pigeon.getUser_info().getPigeon_count());
//                                    adapter.notifyDataSetChanged();
//                                }
//                            },getHoldingContext());
//                            HttpMethods.getInstance().markPigeon(subscriber,jobid,String.valueOf(login_id), String.valueOf(merchantId));
//                        }
//                    }
//                });
//                popupUtils.show(view);
//                view.setSelected(true);
//                return true;
//            }
//        });
        adapter.setOnChatClickListener(new onChatClickListener() {
            @Override
            public void onChat(int postion, final long login_id, View v) {
                LogUtils.i("onChat","position-->"+postion+"    login_id-->"+login_id);
                LCChatKit.getInstance().open(String.valueOf(merchantId), new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        if (null == e) {
                            Intent intent = new Intent(getHoldingContext(), LCIMConversationActivity.class);
                            intent.putExtra(LCIMConstants.PEER_ID, String.valueOf(login_id));
                            startActivity(intent);
                        } else {
                            Toast.makeText(getHoldingContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setEmptyView(mEmptyView);
        list.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.app_bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh=true;
                long times=System.currentTimeMillis();
                String sign= MD5Util.getSign(getActivity(),times);
                HttpMethods.getInstance().getJobUserList(new ProgressSubscriber<List<NewJoinUser>>(userListSubOnNextListener,getActivity()),jobid,tel,sign,String.valueOf(times),type,"1");
            }
        });

        userListSubOnNextListener=new SubscriberOnNextListener<List<NewJoinUser>>() {
            @Override
            public void onNext(List<NewJoinUser> newJobDetail) {
                if (refreshLayout != null && refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                    modleList.clear();
                }
                if (isRefresh){
                    modleList.clear();
                }
                modleList.addAll(newJobDetail);
                adapter.notifyDataSetChanged();
                loadOk=true;
            }
        };
        long times=System.currentTimeMillis();
        String sign= MD5Util.getSign(getActivity(),times);
        HttpMethods.getInstance().getJobUserList(new ProgressSubscriber<List<NewJoinUser>>(userListSubOnNextListener,getActivity()),jobid,tel,sign,String.valueOf(times),type,"1");

        //标记鸽子
        listenner = new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                Toast.makeText(getActivity(), baseBean.getMessage(), Toast.LENGTH_SHORT).show();
                FilterEvent filterEvent = new FilterEvent();
                EventBus.getDefault().post(filterEvent);
            }
        };
        subscriber = new ProgressSubscriber<>(listenner,getActivity(),false);
    }

    public void onEvent(FilterEvent filterEvent) {
//        GetTask getTask = new GetTask(jobid, String.valueOf(type), "0");
//        getTask.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onStart() {
        super.onStart();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ( modleList.size() >= 10 && lastVisibleItem  == modleList.size()-1 && loadOk && modleList.size()%10 == 0) {
                    loadOk=false;
                    long times =System.currentTimeMillis();
                    String sign = MD5Util.getSign(getActivity(),times);
                    int pageNum =modleList.size()/10+1;
                    isRefresh=false;
                    HttpMethods.getInstance().getJobUserList(new ProgressSubscriber<List<NewJoinUser>>(userListSubOnNextListener,getActivity()),jobid,tel,sign,String.valueOf(times),type, String.valueOf(pageNum));
//                    refreshLayout.setRefreshing(true);
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

    void exportUser() {
      SweetAlertDialog sw=new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        sw.show();
        try {
            ExcelUtil excelUtils = new ExcelUtil(jobName, modleList);
            File file = excelUtils.create();
            ExcelUtil.openFileByOtherApp(getActivity(), file);
            if (sw.isShowing()) {
                sw.dismiss();
            }
        } catch (Exception e) {
            LogUtils.e("导出excel", e.getMessage());
            if (sw.isShowing()) {
                sw.dismiss();
            }
        }
    }



}
