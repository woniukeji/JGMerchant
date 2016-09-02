package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;

import butterknife.BindView;

/**
 * Created by Se7enGM on 2016/9/2.
 */
public class FilterFooterViewHolder extends TopViewHolder<Integer> {

    @BindView(R.id.tv_loading)
    TextView tvLoading;
    @BindView(R.id.pb_footer)
    ProgressBar pbFooter;

    public FilterFooterViewHolder(Context context, ViewGroup root) {
        super(context, R.layout.recycle_footer, root);
    }


    /**
     * @param showState 0正在努力加载
     *                  1已经加载全部
     *                  2不显示footer
     */
    @Override
    public void bindData(Integer showState) {
        switch (showState) {
            case 0:
//                代表努力加载
                tvLoading.setText("正在努力加载");
                pbFooter.setVisibility(View.VISIBLE);
                break;
            case 1:
//                代表已经是全部
                tvLoading.setText("已加载全部");
                pbFooter.setVisibility(View.INVISIBLE);
                break;
            case 2:
//                代表不显示
                itemView.setVisibility(View.GONE);
                break;
        }
    }
}
