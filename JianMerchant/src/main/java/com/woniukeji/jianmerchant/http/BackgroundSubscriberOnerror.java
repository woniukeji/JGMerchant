package com.woniukeji.jianmerchant.http;

import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by invinjun on 2016/6/4.
 */

public class BackgroundSubscriberOnerror<T> extends Subscriber<T>{

    private SubscriberOnNextErrorListener subscriberOnNextListener;
    private Context mContext;
    public BackgroundSubscriberOnerror(SubscriberOnNextErrorListener subscriberOnNextListener, Context context) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
//        Toast.makeText(mContext, "获取数据成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        subscriberOnNextListener.onError(e.getMessage());
    }

    @Override


    public void onNext(T t) {
        subscriberOnNextListener.onNext(t);

    }
}
