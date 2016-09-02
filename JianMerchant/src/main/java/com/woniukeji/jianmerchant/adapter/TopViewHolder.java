package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Se7enGM on 2016/9/2.
 */
public abstract class TopViewHolder<T> extends RecyclerView.ViewHolder {

    public TopViewHolder(Context context, int LayoutResId, ViewGroup root) {
        super(LayoutInflater.from(context).inflate(LayoutResId,root,false));
        //this，哪里实例化，哪里绑定view
        ButterKnife.bind(this, itemView);

    }

    public Context getContext() {
        return itemView.getContext();
    }
    /**
     * 绑定数据
     * @param t 要绑定的数据
     */
    public abstract void bindData(T t);
}
