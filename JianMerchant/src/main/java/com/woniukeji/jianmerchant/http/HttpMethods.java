package com.woniukeji.jianmerchant.http;

import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.CityAndCategoryBean;
import com.woniukeji.jianmerchant.entity.Jobs;
import com.woniukeji.jianmerchant.entity.Model;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/16.
 */
public class HttpMethods {
    private static HttpMethods ourInstance = null;
    private final HttpMethodsInterface methodsInterface;

    public static HttpMethods getInstance() {
        if (ourInstance == null) {
            synchronized (HttpMethods.class) {
                if (ourInstance == null) {
                    ourInstance = new HttpMethods();
                }
            }
        }
        return ourInstance;
    }
//    //在访问HttpMethods时创建单例
//    private static class SingletonHolder{
//        private static final HttpMethods INSTANCE = new HttpMethods();
//    }

    //获取单例
//    public static HttpMethods getInstance(){
//        return SingletonHolder.INSTANCE;
//    }

    private HttpMethods() {
        OkHttpClient.Builder ok = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS);
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl(Constants.JIANGUO_USING)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(ok.build())
                .build();

        methodsInterface = retrofit.create(HttpMethodsInterface.class);
    }

    private class BaseBeanFun<T> implements Func1<BaseBean<T>,T>{
        @Override
        public T call(BaseBean<T> baseBean) {
            if (Integer.valueOf(baseBean.getCode())==500) {
                throw new APIExecption(Integer.valueOf(baseBean.getCode()));
            }
            return baseBean.getData();
        }

    }

    public void getCityAndCategory(Subscriber<CityAndCategoryBean> subscriber, String only,String loginId) {
        Observable<BaseBean<CityAndCategoryBean>> cityCategory = methodsInterface.getCityCategory(only, loginId);
        cityCategory.map(new BaseBeanFun<CityAndCategoryBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void saveJobInfoToServer(Subscriber<Jobs> subscriber,String only,
                                    String city_id,String aera_id,String type_id,
                                    String merchant_id,String name,String name_image,
                                    String start_date,String stop_date,String address,String mode,
                                    String money,String term,String limit_sex,String sum,
                                    String hot,String alike,String lon,String lat,String tel,String start_time,String stop_time,
                                    String set_place,String set_time,String other,String work_content,String work_require,String job_model,
                                    String json_limit,String json_welfare,String json_label) {
        Observable<BaseBean<Jobs>> jobs = methodsInterface.saveJobInfoToServer(only, city_id, aera_id, type_id, merchant_id, name, name_image, start_date,
                stop_date, address, mode, money, term, limit_sex, sum, hot, alike, lon, lat, tel, start_time, stop_time, set_place, set_time, other, work_content, work_require, job_model,json_limit,json_welfare,json_label);
        jobs.map(new BaseBeanFun<Jobs>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getHistroyJobFromServer(Subscriber<Model> subscriber,String only,String merchant_id,String type,String count) {
        Observable<BaseBean<Model>> histroyJobFromServer = methodsInterface.getHistroyJobFromServer(only, merchant_id, type, count);
        histroyJobFromServer.map(new BaseBeanFun<Model>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void deleteModelInfo(Subscriber<BaseBean> subscriber,String only,String merchant_id,String job_id) {
        Observable<BaseBean> message = methodsInterface.deleteModelInfo(only,merchant_id, job_id);
        message.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getJobInfoAccordingToMerchantID(Subscriber<Model> subscriber,String only,String merchant_id,String count,String status) {
        methodsInterface.getJobInfoAccordingToMerchantID(only, merchant_id, count, status)
                .map(new BaseBeanFun<Model>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private class APIExecption extends RuntimeException {
        public APIExecption(Integer code) {
            this(getAPIExecptionMessage(code));
        }

        public APIExecption(String message) {
            super(message);
        }
    }

    private static String getAPIExecptionMessage(Integer code) {
        String message ="";
        if (code == 500) {
            message = "没有获取到信息";
        }
        return message;
    }


}
