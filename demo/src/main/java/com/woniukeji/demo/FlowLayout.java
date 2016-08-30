package com.woniukeji.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class FlowLayout extends ViewGroup {

    private int mGravity;
    private int mMaxSelect;
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;
    private List<View> lineViews;

    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mMaxSelect = a.getInt(R.styleable.TagFlowLayout_max_select, -1);
        mGravity = a.getInt(R.styleable.TagFlowLayout_gravity,LEFT);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //分配的宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        MeasureSpec.makeMeasureSpec(50, MeasureSpec.AT_MOST);
        //如果父控件是wrap_content 宽高需要计算后在设置

        int width = 0;//控件最终宽度
        int height = 0;//控件最终高度

        int currentWidth = 0;//当前行的宽度
        int currentHeight = 0;//当前行的高度
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //gone了就不参加计算
            if (child.getVisibility() == View.GONE) continue;
            //让child开始测量
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //适配child的margin属性
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            //child的宽
            int childWidth = lp.rightMargin+child.getMeasuredWidth()+lp.leftMargin;
            //child的高
            int childHeight = lp.topMargin+child.getMeasuredHeight()+lp.bottomMargin;
            //当前行宽+当前child的宽>此控件分配的控件
            if (currentWidth+childWidth>widthSize-getPaddingLeft()-getPaddingRight()) {
                //需要换行
                width = Math.max(childWidth, currentWidth);
                //重新设置当前行宽
                currentWidth = childWidth;
                //行高叠加
                // 最后一下叠加操作应该放在最后一个child身上处理
                height += currentHeight;
                //重新设置当前行高
                currentHeight = childHeight;
            } else {
                //叠加当前行的宽度
                currentWidth += childWidth;
                //比对获取当前行最大高度
                currentHeight = Math.max(childHeight, currentHeight);
            }
            if (i == childCount - 1) {
                height += currentHeight;
                //比较当前行和之前存储的最大宽度
                width = Math.max(currentWidth, width);
            }
        }
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ?  widthSize: width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom());
    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //1、将view区分行保存，保存每行的最大高度
        mAllViews.clear();
        mLineHeight.clear();
        lineViews.clear();
        //
        int width = getWidth();
        //当前行宽
        int lineWidth = 0;
        //当前行高
        int lineHeight = 0;
        // 存储每一行所有的childView  添加到mAllViews
        lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (childWidth+lp.leftMargin+lp.rightMargin+lineWidth>width-getPaddingLeft()-getPaddingRight()) {//当前宽度>布局分配的宽度
                //添加这一行最高的
                mLineHeight.add(lineHeight);
                //重置当前的行宽
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //添加到全部child中
                mAllViews.add(lineViews);
                //开启新的行view
                lineViews = new ArrayList<>();
            }
            lineWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(childHeight+lp.topMargin+lp.bottomMargin, lineHeight);
            lineViews.add(child);
            if (i == childCount - 1) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
            }
        }


        //2、布局子view

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                //计算每一个child的参数
                int cl = lp.leftMargin + left+getPaddingLeft();
                int ct = lp.topMargin + top;
                int cr = cl + child.getMeasuredWidth();
                int cb = ct +child.getMeasuredHeight();
                child.layout(cl,ct,cr,cb);
                left +=  child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }


    //为了使控件支持margin属性
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }

    //为了使控件支持margin属性
    @Override
    protected LayoutParams generateDefaultLayoutParams()
    {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    //为了使控件支持margin属性
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p)
    {
        return new MarginLayoutParams(p);
    }

}
