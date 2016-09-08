package com.woniukeji.jianmerchant.affordwages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.woniukeji.jianmerchant.adapter.CalculateViewHolder;
import com.woniukeji.jianmerchant.adapter.FooterViewHolder;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

public class CalculateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<AffordUser.ListTUserInfoEntity> mValues;
    private List<Boolean> isSelected;
    private final Context mContext;
    private String money;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private String name;

    private CalculateViewHolder.ChangeMoney changeMoney;
    public void setChangeMoney(CalculateViewHolder.ChangeMoney changeMoney) {
        this.changeMoney = changeMoney;
    }

    public CalculateAdapter(List<AffordUser.ListTUserInfoEntity> items,List<Boolean> list, Context context, String money) {
        mValues = items;
        mContext = context;
        isSelected=list;
        this.money = money;
        name = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_NAME, "");
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case NORMAL:
                holder = new CalculateViewHolder(mContext, parent,isSelected,money);
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
            ((CalculateViewHolder)holder).bindData(mValues.get(position));
            ((CalculateViewHolder)holder).setPosition(position);
            if (changeMoney != null) {
                ((CalculateViewHolder)holder).setChangeMoney(changeMoney);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() != 0 ? mValues.size() + 1 : 0;
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
