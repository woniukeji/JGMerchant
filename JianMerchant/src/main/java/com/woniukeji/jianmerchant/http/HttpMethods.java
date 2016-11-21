package com.woniukeji.jianmerchant.http;

import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.CityAndCategoryBean;
import com.woniukeji.jianmerchant.entity.JobBase;
import com.woniukeji.jianmerchant.entity.JobInfo;
import com.woniukeji.jianmerchant.entity.Jobs;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.entity.NewJobDetail;
import com.woniukeji.jianmerchant.entity.NewMerchant;
import com.woniukeji.jianmerchant.entity.Pigeon;
import com.woniukeji.jianmerchant.entity.PublishUser;
import com.woniukeji.jianmerchant.entity.SmsCode;
import com.woniukeji.jianmerchant.entity.Status;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.jpush.PushMessage;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.leancloud.chatkit.LCChatKitUser;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
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
        OkHttpClient.Builder ok = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS);
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
            if (Integer.valueOf(baseBean.getCode())!=200) {
                throw new APIExecption(baseBean.getMessage());
            }
            return baseBean.getData();
        }

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
    /**
     *推送信息获取
     *@param
     *@param
     *@author invinjun
     *created at 2016/7/26 16:46
     */
    public void getPush(Subscriber<PushMessage> subscriber, String loginid){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.getPush(only,loginid)
                .map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     *审核状态
     *@param
     *@param
     *@author invinjun
     *created at 2016/7/26 16:46
     */
    public void getStatus(Subscriber<Status> subscriber, String tel,String timestamp,String sign){
        String appid=MD5Util.MD5(tel);
        methodsInterface.getStatus(appid,sign,timestamp,"2")
                .map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
    *商家注册
    *@param tel
    *@param smsCode
     * @param password
    *@author invinjun
    *created at 2016/10/21 15:27
    */
    public void register(Subscriber<String> subscriber,String appid, String tel,String smsCode,String password,String type) {
        Observable<BaseBean> cityCategory = methodsInterface.sign(appid,tel, smsCode,password,type);
        cityCategory.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
    /**
     *短信验证码
     *@param tel
     *@author invinjun
     *created at 2016/10/21 15:27
     */
    public void sms(Subscriber<String> subscriber,  String appid,String tel, String type) {
        Observable<BaseBean> cityCategory = methodsInterface.sendCode(appid,tel,type);
        cityCategory.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

 /**
 *忘记密码 重置密码
 *@param tel
 *@param code
  * @param passwd
  *@author invinjun
 *created at 2016/11/14 15:08
 */
    public void passReset(Subscriber<String> subscriber, String tel,String code, String passwd) {
        String appid=MD5Util.MD5(tel);
        Observable<BaseBean> cityCategory = methodsInterface.passReset(appid,tel,code,passwd);
        cityCategory.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     *账户密码登陆
     *@param tel
     *@author invinjun
     *created at 2016/10/21 15:27
     */
    public void passLogin(Subscriber<NewMerchant> subscriber, String tel, String password) {
        String appid= MD5Util.MD5(tel);
        Observable<BaseBean<NewMerchant>> cityCategory = methodsInterface.passwdLogin(appid,tel,password,"2");
        cityCategory.map(new BaseBeanFun<NewMerchant>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     *账户密码登陆
     *@param tel
     *@author invinjun
     *created at 2016/10/21 15:27
     */
    public void codeLogin(Subscriber<NewMerchant> subscriber, String tel, String code) {
        String appid= MD5Util.MD5(tel);
        Observable<BaseBean<NewMerchant>> cityCategory = methodsInterface.smsLogin(appid,tel,code,"2");
        cityCategory.map(new BaseBeanFun<NewMerchant>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     *自动登录
     *@param tel
     *@author invinjun
     *created at 2016/10/21 15:27
     */
    public void autoLogin(Subscriber<NewMerchant> subscriber, String tel,String token) {
        String appid= MD5Util.MD5(tel);
        Observable<BaseBean<NewMerchant>> cityCategory = methodsInterface.autoLogin(appid,token,"2");
        cityCategory.map(new BaseBeanFun<NewMerchant>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *审核商家信息
     *@param tel
     *@param sign
     * @param timestamp
     *@author invinjun
     *created at 2016/10/21 15:27
     */
    public void cerfication(Subscriber<String> subscriber,String tel, String sign,String timestamp,String front_img_url,String head_img_url
            ,String realname,String nickName,String IDcard,String phone,String type,String company_img_url,String hold_img_url,String companyAdress,
                            String companyName, String bus_licence_num, String contact_name, String contact_phone, String email,String province_id,
                            String city_id,String introduce) {
        String appid= MD5Util.MD5(tel);
        Observable<BaseBean> cityCategory = methodsInterface.Cerfication(appid,sign,timestamp,front_img_url,head_img_url,
                realname,nickName, IDcard,phone,type,company_img_url,hold_img_url,companyAdress,companyName,bus_licence_num,contact_name,contact_phone, email,province_id,city_id,introduce);
        cityCategory.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
    public void makeJob(Subscriber<String> subscriber,String sign,String timestamp,
                                    String login_phone,
                                    String city_id,String aera_id,String type_id,
                                    String name,String name_image,String type,
                                    String start_date,String stop_date,String address,String mode,
                                    String money,String term,String limit_sex,String girl_sum,String boy_sum,String sum,
                                    String tel,String start_time,String stop_time,
                                    String set_place,String set_time,String work_content,String work_require,
                                    String json_limit,String json_welfare,String json_label,String job_model) {
                String appid=MD5Util.MD5(login_phone);
                Observable<BaseBean> jobs = methodsInterface.makeJob(appid, sign,timestamp,type,name,type_id,name_image,
                start_date,stop_date, address, mode, money, term, limit_sex, girl_sum,boy_sum,sum,city_id, aera_id, tel, start_time, stop_time, set_place, set_time, work_content, work_require, json_limit,json_welfare,json_label,job_model);
                jobs.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void changeJob(Subscriber<String> subscriber,String job_id,String sign,String timestamp,
                        String login_phone,
                        String city_id,String aera_id,String type_id,
                        String name,String name_image,String type,
                        String start_date,String stop_date,String address,String mode,
                        String money,String term,String limit_sex,String girl_sum,String boy_sum,String sum,
                        String tel,String start_time,String stop_time,
                        String set_place,String set_time,String work_content,String work_require,
                        String json_limit,String json_welfare,String json_label,String job_model) {
        String appid=MD5Util.MD5(login_phone);
        Observable<BaseBean> jobs = methodsInterface.changeJob(job_id,appid, sign,timestamp,type,name,type_id,name_image,
                start_date,stop_date, address, mode, money, term, limit_sex, girl_sum,boy_sum,sum,city_id, aera_id, tel, start_time, stop_time, set_place, set_time, work_content, work_require, json_limit,json_welfare,json_label,job_model);
        jobs.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     * 兼职历史记录
     */
    public void getHistroyJobFromServer(Subscriber<List<JobInfo>> subscriber, String tel, String sign,String type, String timestamp, String pageNum) {
        String appid=MD5Util.MD5(tel);
        methodsInterface.getHistroyJobFromServer(appid,sign,type,timestamp,pageNum)
                .map(new BaseBeanFun<List<JobInfo>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     * 商家录用状态信息，录取or完成
     */
    public void getJobList(Subscriber<List<JobInfo>> subscriber, String tel, String sign, String timestamp, String type, String pageNum) {
        String appid=MD5Util.MD5(tel);
        methodsInterface.getJobList(appid,sign,timestamp,type,pageNum)
                .map(new BaseBeanFun<List<JobInfo>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
/**
*兼职详情
*/
    public void getjobDetail(Subscriber<NewJobDetail> subscriber, String jobid, String tel, String sign, String timestamp) {
        String appid=MD5Util.MD5(tel);
        Observable<BaseBean<NewJobDetail>> cityCategory = methodsInterface.jobDetail(jobid,appid, sign,timestamp);
        cityCategory.map(new BaseBeanFun<NewJobDetail>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
    /**
     *修改兼职状态
     */
    public void changeJobStatus(Subscriber<String> subscriber, String jobid, String tel, String sign, String timestamp,String status) {
        String appid=MD5Util.MD5(tel);
        Observable<BaseBean> cityCategory = methodsInterface.changeJobStatus(jobid,appid, sign,timestamp,status);
        cityCategory.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
    /** -----------------------------------------老版分割线-----------------------------------------------------------------------------------？、、*/
        /**
         * *验证码登陆
         *@param tel
         *@author invinjun
         *created at 2016/10/21 15:27
         */
        public void smsLogin(Subscriber<MerchantBean> subscriber, String tel, String code) {
            Observable<BaseBean<MerchantBean>> cityCategory = methodsInterface.smsLogin(tel,code);
            cityCategory.map(new BaseBeanFun<MerchantBean>())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);

        }


    /**
     *审核商家信息
     *@param token
     *@param merchantid
     * @param loginId
     *@author invinjun
     *created at 2016/10/21 15:27
     */
    public void UpLoadLogo(Subscriber<String> subscriber,String loginId, String merchantid,String token,String url) {
        Observable<BaseBean> cityCategory = methodsInterface.UpLoadLogo(loginId,merchantid, token,url);
        cityCategory.map(new BaseBeanFun())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }




    public void getCityAndCategory(Subscriber<JobBase> subscriber, String tel, String sign, String timestamp) {
        String appid=MD5Util.MD5(tel);
        Observable<BaseBean<JobBase>> cityCategory = methodsInterface.getCityCategory(appid, sign,timestamp);
        cityCategory.map(new BaseBeanFun<JobBase>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void saveJobInfoToServer(Subscriber<Jobs> subscriber,String only,
                                    String city_id,String aera_id,String type_id,
                                    String merchant_id,String name,String name_image,
                                    String start_date,String stop_date,String address,String mode,
                                    String money,String term,String limit_sex,String sum,String girl_sum,
                                    String hot,String alike,String lon,String lat,String tel,String start_time,String stop_time,
                                    String set_place,String set_time,String other,String work_content,String work_require,String job_model,
                                    String json_limit,String json_welfare,String json_label) {
        Observable<BaseBean<Jobs>> jobs = methodsInterface.saveJobInfoToServer(only, city_id, aera_id, type_id, merchant_id, name, name_image, start_date,
                stop_date, address, mode, money, term, limit_sex,sum, girl_sum,hot, alike, lon, lat, tel, start_time, stop_time, set_place, set_time, other, work_content, work_require, job_model,json_limit,json_welfare,json_label);
        jobs.map(new BaseBeanFun<Jobs>())
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

    /**
     * 用于兼职管理界面中的录取或者完成界面
     * @param subscriber
     * @param only
     * @param merchant_id
     * @param count
     * @param staus 0完成 1录取
     */
    public void getPublishJobs(Subscriber<Model> subscriber, String only, String merchant_id, String count, String staus) {
        methodsInterface.getPublishJobs(only, merchant_id, count, staus)
                .map(new BaseBeanFun<Model>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取已报名人员信息
     * @param subscriber
     * @param only
     * @param job_id
     * @param count
     * @param type
     */
    public void getEnrollJobs(Subscriber<PublishUser> subscriber, String only, String job_id, String count, String type) {
        methodsInterface.getEnrollJobs(only,job_id,count,type)
                .map(new BaseBeanFun<PublishUser>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void admitOrRefuseUser(Subscriber<BaseBean> subscriber, String only, String job_id, String login_id, String offer) {
        methodsInterface.admitUser(only,job_id,login_id,offer)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     *获取果聊用户资料
     */
    public void getTalkUser(Subscriber<List<LCChatKitUser>> lcChatKitUserSubscriber, String loginid){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.getTalkUser(only,loginid)
                .map(new BaseBeanFun<List<LCChatKitUser>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lcChatKitUserSubscriber);
    }

    public void markPigeon(Subscriber<Pigeon> subscriber, String job_id, String login_id,String merchant_id) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.markPigeon(only,job_id,login_id,merchant_id)
                .map(new BaseBeanFun<Pigeon>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void singleJobDetail(Subscriber<Model> subscriber, String job_id) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.singleJobDetail(only, job_id)
                .map(new BaseBeanFun<Model>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCodes(Subscriber<SmsCode> subscriber, String tel) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.getCodes(only, tel)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void registAccount(Subscriber<User> subscriber,String tel,String password) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.registAccount(only,tel,password)
                .map(new BaseBeanFun<User>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void updateJob(Subscriber<BaseBean> subscriber,
                          String only,String job_id,
                          String city_id,String aera_id,String type_id,
                          String merchant_id,String name,String name_image,
                          String start_date,String stop_date,String address,String mode,
                          String money,String term,String limit_sex,String sum,String girl_sum,
                          String hot,String alike,String lon,String lat,String tel,String start_time,String stop_time,
                          String set_place,String set_time,String other,String work_content,String work_require,String job_model,
                          String json_limit,String json_welfare,String json_label) {

        methodsInterface.updateJob(only, job_id,city_id, aera_id, type_id, merchant_id, name, name_image, start_date, stop_date, address,
                mode, money, term, limit_sex, sum, girl_sum, hot, alike, lon, lat, tel, start_time, stop_time, set_place, set_time,
                other, work_content, work_require, job_model, json_limit, json_welfare, json_label)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 商家录用状态信息，录取or完成
     */
    public void merchantEmployStatus(Subscriber<Model> subscriber, String merchant_id, String count, String status) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.merchantEmployStatus(only,merchant_id,count,status)
                .map(new BaseBeanFun<Model>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取支付工资人员列表
     */
    public void paywage(Subscriber<AffordUser> subscriber,String job_id,String count) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.getPayList(only,job_id,count)
                .map(new BaseBeanFun<AffordUser>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 结算
     */
    public void checkout(Subscriber<BaseBean> subscriber,String job_id,String json) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodsInterface.checkout(only,job_id,json)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }





}
