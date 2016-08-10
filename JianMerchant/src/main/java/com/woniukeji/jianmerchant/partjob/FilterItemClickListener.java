package com.woniukeji.jianmerchant.partjob;

import android.view.View;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface FilterItemClickListener {
    void onItemClick(int position);

    boolean onItemLongClick(int position,View view);
}
