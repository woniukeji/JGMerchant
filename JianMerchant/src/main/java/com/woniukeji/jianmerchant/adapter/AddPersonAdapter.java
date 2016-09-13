package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AddPerson;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

/**
 * Created by Se7enGM on 2016/9/13.
 */
public class AddPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AddPerson> mValues;
    private final int merchantId;
    private final String merchantName;
    private static final int Footer = 0;
    private static final int NORMAL = 1;


    public AddPersonAdapter(Context context, List<AddPerson> dataSet) {
        this.mContext = context;
        this.mValues = dataSet;
        merchantId = (int) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_MERCHANT_ID, 0);
        merchantName = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_NAME, "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Footer) {
            return new FooterViewHolder(mContext, parent);
        } else {
            return new AddPersonViewHolder(mContext, parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (position)
    }

    @Override
    public int getItemCount() {
        return mValues.size()==0?0:mValues.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mValues.size()) {
            return Footer;
        } else {
            return NORMAL;
        }
    }
}
