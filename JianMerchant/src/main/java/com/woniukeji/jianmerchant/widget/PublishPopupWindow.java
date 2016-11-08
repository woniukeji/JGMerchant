package com.woniukeji.jianmerchant.widget;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.publish.PublishActivity;


/**
 * @data: 2015-4-21
 * @version: V1.0
 */

public class PublishPopupWindow extends PopupWindow implements OnClickListener {

    private Context context;
    private ImageView bussiness;
    private ImageView jianguo;
    private ImageView sina;
    private ImageView qzone;
    private Handler mHandler;
    private SharedPreferences SearchSp;
    private String accessToken;
    private String userId;
    private String videoId;
    private ImageView share_dialog_close;
    private RelativeLayout ll_subscribe_share;
    private RelativeLayout rl_red_bg;
    private String date;
    private String wage;
    private String jobid;
    //分享相关

    public PublishPopupWindow(Context cx) {
        this.context = cx;
    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.publish_popupwindow, null);
        //三个类别的分享
        bussiness = (ImageView) view.findViewById(R.id.img_buss_icon);
        jianguo = (ImageView) view.findViewById(R.id.img_jianguo_icon);
        view.findViewById(R.id.img_dismiss).setOnClickListener(this);
//        share_dialog_close = (ImageView) view.findViewById(R.id.share_dialog_close);
//        ll_subscribe_share = (RelativeLayout) view.findViewById(R.id.ll_subscribe_share);
//        rl_red_bg = (RelativeLayout) view.findViewById(R.id.rl_red_bg);
        //取消分享
        //添加点击事件
        bussiness.setOnClickListener(this);
        jianguo.setOnClickListener(this);
//

        // 添加布局
        this.setContentView(view);
        // 设置SharePopupWindow宽度
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SharePopupWindow高度
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置setFocusable可获取焦点
        this.setFocusable(true);
        // 设置setFocusable动画风格
//        this.setAnimationStyle(R.style.AnimBottom);
        //画背景
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置背景
        this.setBackgroundDrawable(dw);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_buss_icon:
            Intent intent = new Intent(context, PublishActivity.class);
             context.startActivity(intent);
                dismiss();
                break;
            case R.id.img_jianguo_icon:
                Intent intent1 = new Intent(context, WebViewActivity.class);
                intent1.putExtra("url", "https://jinshuju.net/f/TboSOV");
                context.startActivity(intent1);
                dismiss();
                break;
            case R.id.img_dismiss:
              dismiss();
                break;
            default:
                break;
        }
    }










}
