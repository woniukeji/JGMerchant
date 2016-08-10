package com.woniukeji.jianmerchant.partjob;

import android.view.View;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface onChatClickListener {

    /**
     * @param postion 当前adapter中的位置
     * @param login_id 用户login_id
     * @param v 当前点击的View
     */
    void onChat(int postion, int login_id, View v);
}
