package com.woniukeji.jianmerchant.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;

/**
 * Created by Administrator on 2016/8/10.
 */
public class PopupUtils {
    private Context context;
    private TextView content;
    private PopupWindow popupWindow;
    private final int height;

    public PopupUtils(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
        content = (TextView) view.findViewById(R.id.popup_content);
        popupWindow = new PopupWindow(view);
        height = popupWindow.getHeight();
        initListener();
    }

    private void initListener() {
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetupDove.onSetup(v);
            }
        });
    }

    public void show(View view) {
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        popupWindow.showAtLocation(view,Gravity.NO_GRAVITY ,rect.centerX(),rect.height()-height);
    }

    public void setOnSetupDove(PopupUtils.onSetupDove onSetupDove) {
        this.onSetupDove = onSetupDove;
    }

    private onSetupDove onSetupDove;

    public interface onSetupDove {
        void onSetup(View v);
    }


}
