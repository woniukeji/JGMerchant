package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AlreadyCalculateAdapter extends RecyclerView.Adapter<AlreadyCalculateAdapter.ViewHolder> {

    private List<AffordUser.ListBean> mData;
    private Context mContext;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private boolean isFooterChange = false;

    public AlreadyCalculateAdapter(Context context, List<AffordUser.ListBean> list) {
        mData = list;
        mContext = context;
    }

    public void setFooterChange(boolean isChange) {
        isFooterChange = isChange;
    }

    public void mmswoon(ViewHolder holder) {
        if (isFooterChange) {
            holder.loading.setText("已加载全部");
            holder.animLoading.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
        } else {
            holder.loading.setText("正在加载数据");
            holder.animLoading.setVisibility(View.VISIBLE);


        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = null;
        switch (viewType) {
            case NORMAL:
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alreadycalculate, parent, false);
                holder = new ViewHolder(VoteView, NORMAL);
                return holder;

            case IS_FOOTER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_footer, parent, false);
                holder = new ViewHolder(view, IS_FOOTER);
                return holder;

            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mData.size()< 1){
            holder.itemView.setVisibility(View.VISIBLE);
        }

        if (mData.size() == position) {
            if (mData.size() > 4) {
                mmswoon(holder);
                holder.itemView.setVisibility(View.VISIBLE);
            }else {
                mmswoon(holder);
                holder.itemView.setVisibility(View.GONE);
            }

        }else{
            if (mData.get(position).getNickname() != null) {
                holder.tvUserName.setText(mData.get(position).getNickname());
            } else {
                holder.tvUserName.setText(mData.get(position).getNickname() + "（未实名）");
            }

            holder.tvPhone.setText(mData.get(position).getTel());
            holder.tvWages.setText(mData.get(position).getMoney() + "元");
            Picasso.with(mContext).load(mData.get(position).getHead_img_url()).transform(new CropCircleTransfermation()).error(R.drawable.default_head).into(holder.imgHead);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size() > 0 ? mData.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return IS_FOOTER;
        } else {
            return NORMAL;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_wages)
        TextView tvWages;

        private ImageView animLoading;
        private TextView loading;

        private ProgressBar progressBar;

        public ViewHolder(View view, int type) {
            super(view);

            switch (type) {
                case NORMAL:
                    ButterKnife.bind(this, view);
                    break;
                case IS_FOOTER:
                    animLoading = (ImageView) view.findViewById(R.id.anim_loading);
                    loading = (TextView) view.findViewById(R.id.tv_loading);
                    progressBar = (ProgressBar) view.findViewById(R.id.pb_footer);
                    break;
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }


}
