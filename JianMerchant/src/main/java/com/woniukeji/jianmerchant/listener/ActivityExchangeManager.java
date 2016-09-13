package com.woniukeji.jianmerchant.listener;

import android.os.Bundle;

/**
 * Created by Se7enGM on 2016/9/13.
 */
public class ActivityExchangeManager {
    private static ActivityExchangeManager instance;
    public static ActivityExchangeManager getInstance() {
        if (instance == null) {
            synchronized (ActivityExchangeManager.class) {
                if (instance == null) {
                    instance = new ActivityExchangeManager();
                    return instance;
                }
            }
        }
        return instance;
    }
    private ActivityExchangeManager() {
    }

    private ActivityExchange mActivityExchange;

    /**
     * 直接获取实例之后调用方法，发送数据
     */
    public void sendDataSet(Bundle bundle) {
        if (mActivityExchange==null)
            throw new NullPointerException("ActivityExchange没有被实现，或者没有调用registActivityExchangeManager方法");
        if (mActivityExchange != null) {
            mActivityExchange.exchangeDataSet(bundle);
        }
    }

    /**
     * 调用这需要先实现ActivityExchange接口，再注册此方法
     */
    public void registActivityExchangeManager(ActivityExchange exchange) {
        mActivityExchange = exchange;
    }
}
