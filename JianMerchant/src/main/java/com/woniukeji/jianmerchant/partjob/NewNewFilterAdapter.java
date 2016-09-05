package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.woniukeji.jianmerchant.adapter.FooterViewHolder;
import com.woniukeji.jianmerchant.adapter.FilterItemViewHolder;
import com.woniukeji.jianmerchant.entity.PublishUser;

import java.util.List;

/**
 * Created by Se7enGM on 2016/9/2.
 */
public class NewNewFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PublishUser.ListTUserInfoEntity> mValues;
    private Context context;
    private int type;
    private String jobid;

    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;

    private onChatClickListener onChatClickListener;
    public void setOnChatClickListener(com.woniukeji.jianmerchant.partjob.onChatClickListener onChatClickListener) {
        this.onChatClickListener = onChatClickListener;
    }
    private FilterItemClickListener filterItemClickListener;
    public void setFilterItemClickListener(FilterItemClickListener filterItemClickListener) {
        this.filterItemClickListener = filterItemClickListener;
    }

    private EnrollOrRefuseClickListener enrollOrRefuseClickListener;
    public void setEnrollOrRefuseClickListener(EnrollOrRefuseClickListener enrollOrRefuseClickListener) {
        this.enrollOrRefuseClickListener = enrollOrRefuseClickListener;
    }




    public NewNewFilterAdapter(List<PublishUser.ListTUserInfoEntity> values, Context context, int type, String jobid) {
        this.mValues = values;
        this.context = context;
        this.type = type;
        this.jobid = jobid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==NORMAL) {
            return new FilterItemViewHolder(context, parent);
        } else if (viewType == IS_FOOTER) {
            return new FooterViewHolder(context, parent);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (mValues.size() == position) {
            //最后一条数据
            if (mValues.size()<=4&&mValues.size()>0) {
                ((FooterViewHolder)holder).bindData(1);
            } else {
                ((FooterViewHolder)holder).bindData(0);
            }
        } else {
            ((FilterItemViewHolder)holder).bindData(mValues.get(position));
            if (filterItemClickListener != null) {
                ((FilterItemViewHolder)holder).setFilterItemClickListener(filterItemClickListener);
            }
            if (enrollOrRefuseClickListener != null) {
                ((FilterItemViewHolder)holder).setEnrollOrRefuseClickListener(enrollOrRefuseClickListener);
            }
            if (onChatClickListener!=null) {
                ((FilterItemViewHolder)holder).setOnChatClickListener(onChatClickListener);
            }
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
