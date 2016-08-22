package com.woniukeji.demo;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/8/20.
 */
public class TagView extends FrameLayout implements Checkable {
    private boolean isChecked;
    //这个是可能是checkbox或者类似控件的相关属性，
    //举个例子便于理解，用xml绘制drawable时，可以通过select.xml执行状态，这个就是了。
    //state_check=true，这里会刷新执行refreshdrawablestate。再执行oncreatedrawablestate
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};
    public TagView(Context context) {
        super(context);//这里之通过代码创建对象，所以不需要另外两个构造
    }

    public View getTagView() {
        return getChildAt(0);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            refreshDrawableState();
        }

    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
