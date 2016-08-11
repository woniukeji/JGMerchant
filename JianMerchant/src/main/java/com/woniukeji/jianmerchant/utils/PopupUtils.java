package com.woniukeji.jianmerchant.utils;

import android.content.Context;
import android.util.TypedValue;
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

    public PopupUtils(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
        content = (TextView) view.findViewById(R.id.popup_content);
        popupWindow = new PopupWindow(view, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,context.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,context.getResources().getDisplayMetrics()));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(null);
        initListener();
    }

    private void initListener() {
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSetupDove!=null) {
                    onSetupDove.onSetup(v);
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
    }

    public void show(View view) {

        int width = view.getWidth();
        int height = view.getHeight();
        //左下角显示的起点
        int popupWindowWidth = popupWindow.getWidth();
        int popupWindowHeight = popupWindow.getHeight();
        popupWindow.showAsDropDown(view,width/2-popupWindowWidth/2,-height/2-popupWindowHeight/2);

    }

    public void setOnSetupDove(PopupUtils.onSetupDove onSetupDove) {
        this.onSetupDove = onSetupDove;
    }

    private onSetupDove onSetupDove;

    public interface onSetupDove {
        void onSetup(View v);
    }


}
