package com.woniukeji.jianmerchant.http;

/**
 * Created by Administrator on 2016/8/12.
 */
 public abstract class SubscriberOnNextErrorListener<T> {
    public void onNext(T t) {
    }
    public void onError(String mes){

    }
}
