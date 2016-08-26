package com.woniukeji.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;


public class TagLayout extends FlowLayout implements OnDataChangedListener {

    private int mMaxSelect;
    private TagAdapter mTagAdapter;

    public TagLayout(Context context) {
        this(context,null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(R.styleable.TagFlowLayout);
        mMaxSelect = a.getInt(R.styleable.TagFlowLayout_max_select, -1);
        a.recycle();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            TagView tagView = (TagView) getChildAt(i);
            if (tagView.getVisibility()== View.GONE) continue;
            if (tagView.getTagView().getVisibility() == View.GONE) {
                tagView.setVisibility(View.GONE);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setAdapter(TagAdapter adapter) {
        mTagAdapter = adapter;
        mTagAdapter.setmOnDataChangeListener(this);

    }

















    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onChange() {

    }
}
