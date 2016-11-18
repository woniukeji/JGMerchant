package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.woniukeji.jianmerchant.adapter.FooterViewHolder;
import com.woniukeji.jianmerchant.adapter.PJMItemViewHolder;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.JobInfo;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

public class PartJobManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<JobInfo> mValues;
    private final Context mContext;
    private int mType;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private String name;
    public PartJobManagerAdapter(List<JobInfo> items, Context context, int type) {
        mValues = items;
        mContext = context;
        mType = type;
        name = (String) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_TEL, "");
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case NORMAL:
                holder = new PJMItemViewHolder(mContext, parent);
                return holder;

            case IS_FOOTER:
                holder = new FooterViewHolder(mContext,parent);
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
            ((PJMItemViewHolder)holder).bindData(mValues.get(position));
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

}
