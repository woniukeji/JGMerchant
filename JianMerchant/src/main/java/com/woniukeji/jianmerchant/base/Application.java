package com.woniukeji.jianmerchant.base;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.woniukeji.jianmerchant.chat.CustomUserProvider;
import com.woniukeji.jianmerchant.widget.city.dao.DBManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.leancloud.chatkit.LCChatKit;
import okhttp3.OkHttpClient;

/**
 * Created by invinjun on 2016/3/2.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //默认是本地
        UmengConfig();
        init();
        //导入数据库
        DBManager dbHelper = new DBManager(this);
        dbHelper.openDatabase();
//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
    }
    private void init(){
        JPushInterface.init(getApplicationContext());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance(getApplicationContext()));

//        AVOSCloud.setDebugLogEnabled(true);
        LCChatKit.getInstance().init(getApplicationContext(), "AtwJtfIJPKQFtti8D3gNjMmb-gzGzoHsz","spNrDrtGWAXP633DkMMWT65B");
        Fresco.initialize(this);
    }
    public void UmengConfig(){


    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//方法数过多 导致
    }
}
