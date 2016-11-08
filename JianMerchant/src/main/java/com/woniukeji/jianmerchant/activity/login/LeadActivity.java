package com.woniukeji.jianmerchant.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;

//import com.woniukeji.jianguo.utils.PicassoLoader;

public class LeadActivity extends Activity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.btn_enter) Button btnEnter;
//    private InfiniteIndicator mAnimCircleIndicator;
//    private ArrayList<Page> pageViews = new ArrayList<>();
    private int[] drawables = new int[]{R.mipmap.lead1, R.mipmap.lead2, R.mipmap.lead3};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }


//    @Override
//    public void onPageClick(int position, Page page) {
//
//    }
//
//    private void initBannerData() {
//        for (int i = 0; i < 3; i++) {
//            pageViews.add(new Page(String.valueOf(String.valueOf(i)), drawables[i]));
//        }
//        mAnimCircleIndicator.addPages(pageViews);
//    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        ViewPager viewpager = (ViewPager)findViewById(R.id.viewpager);
        PageAdapter mPageAdapter=new PageAdapter(drawables.length,drawables);
        viewpager.setAdapter(mPageAdapter);
//        indicatorDefaultCircle.setImageLoader(new PicassoLoader());
//
//        mAnimCircleIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center_Bottom);
//        mAnimCircleIndicator.setOnPageChangeListener(this);
//
//        mAnimCircleIndicator.isStopScrollWhenTouch();
//        mAnimCircleIndicator.scrollOnce();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        mAnimCircleIndicator.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initBannerData();
//        mAnimCircleIndicator.start();
//        mAnimCircleIndicator.start();
    }

    @Override
    protected void onStop() {
//        mAnimCircleIndicator.stop();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.btn_enter)
    public void onClick() {
        SPUtils.setParam(LeadActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, 1);
        startActivity(new Intent(LeadActivity.this, LoginNewActivity.class));
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position > 1) {
            btnEnter.setVisibility(View.VISIBLE);
//            mAnimCircleIndicator.stop();
        } else {
            btnEnter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position > 1) {
            btnEnter.setVisibility(View.VISIBLE);
//            mAnimCircleIndicator.stop();
        } else {
            btnEnter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public class PageAdapter extends PagerAdapter {

        private final Random random = new Random();
        private int mSize;
        private int[] mDrawables;
        public PageAdapter() {
            mSize = 5;
        }

        public PageAdapter(int count,int[] drawables) {
            mSize = count;
            mDrawables=drawables;
        }

        @Override public int getCount() {
            return mSize;
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override public Object instantiateItem(ViewGroup view, int position) {
            ImageView imageView=new ImageView(view.getContext());
            imageView.setImageResource(mDrawables[position]);
//            textView.setText(String.valueOf(position + 1));
//            textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextColor(Color.WHITE);
//            textView.setTextSize(48);
            view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
        }

        public void addItem() {
            mSize++;
            notifyDataSetChanged();
        }

        public void removeItem() {
            mSize--;
            mSize = mSize < 0 ? 0 : mSize;

            notifyDataSetChanged();
        }
    }

}
