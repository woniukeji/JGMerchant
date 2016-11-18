package com.woniukeji.jianmerchant.publish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.adapter.FooterViewHolder;
import com.woniukeji.jianmerchant.adapter.HistoryJobItemViewHolder;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.JobInfo;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

import butterknife.BindView;

public class HistoryJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<JobInfo> mValues;
    private final Context mContext;
    private  String mType;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    @BindView(R.id.tv_merchant_name) TextView tvTitle;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_location) TextView tvLocation;
    @BindView(R.id.ll_publish_time) LinearLayout llPublishTime;
    @BindView(R.id.tv_human) TextView tvHuman;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.ll_publisher) LinearLayout llPublisher;
    @BindView(R.id.tv_enroll_num) TextView tvEnrollNum;
    @BindView(R.id.btn_muban_use) Button btnMubanUse;
    @BindView(R.id.btn_muban_delete) Button btnMubanDelete;
    @BindView(R.id.ll_muban) LinearLayout llMuban;
    @BindView(R.id.img_his) ImageView imgHis;
    @BindView(R.id.rl_job) RelativeLayout rlJob;

    private String name;




    public HistoryJobAdapter(List<JobInfo> items, Context context, String type) {
        mValues = items;
        mContext = context;
        mType=type;
        name= (String) SPUtils.getParam(mContext, Constants.LOGIN_INFO,Constants.SP_TEL,"");
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case NORMAL:
                holder = new HistoryJobItemViewHolder(mContext, parent,mType,name);
                return holder;
            case IS_FOOTER:
                holder = new FooterViewHolder(mContext, parent);
                return holder;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position ==mValues.size()) {
            //最后一条
            if (mValues.size() > 0 && mValues.size() < 5||mValues.size()>0&&mValues.size()%10!=0) {
                ((FooterViewHolder) holder).bindData(1);
            } else {
                ((FooterViewHolder) holder).bindData(0);
            }
        } else {
            ((HistoryJobItemViewHolder)holder).bindData(mValues.get(position));
            ((HistoryJobItemViewHolder)holder).setPosition(position);
            ((HistoryJobItemViewHolder)holder).setDeleteCallBack(deleteCallback);
        }
    }


    @Override
    public int getItemCount() {
        return mValues.size() > 0 ? mValues.size() + 1 : 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == mValues.size()) {
            return IS_FOOTER;
        } else {
            return NORMAL;
        }
    }

    private HistoryJobItemViewHolder.DeleteCallBack deleteCallback;
    public void setDeleteCallback(HistoryJobItemViewHolder.DeleteCallBack deleteCallback) {
        this.deleteCallback = deleteCallback;
    }
}
