package com.woniukeji.jianmerchant.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/8/1.
 */
public abstract class WeakReferenceHandler<T> extends Handler {

    private final WeakReference<T> mReference;

    public WeakReferenceHandler(T reference) {
        mReference = new WeakReference<>(reference);
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mReference.get() == null) {
            return;
        }
        handleMessage(mReference.get(),msg);
    }

    protected abstract void handleMessage(T reference, Message msg);
}
