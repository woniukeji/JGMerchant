package com.woniukeji.jianmerchant.http;

import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.CityAndCategoryBean;
import com.woniukeji.jianmerchant.entity.JobDetails;
import com.woniukeji.jianmerchant.entity.Jobs;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.entity.Pigeon;
import com.woniukeji.jianmerchant.entity.PublishUser;
import com.woniukeji.jianmerchant.entity.SmsCode;
import com.woniukeji.jianmerchant.entity.Status;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.jpush.PushMessage;

import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/16.
 */
public interface HttpMethodsInterface {
    //    @GET("top250")
//    rx.Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    /**
     *查询推送记录接口
     *@author invinjun
     *created at 2016/7/26 16:44
     */
    @GET("T_push_List_Servlet")
    Observable<BaseBean<PushMessage>> getPush(@Query("only") String only, @Query("login_id") String login_id);

    @POST("GetStatusServlet")
    Observable<BaseBean<Status>> getStatus(@Query("merchantId") String merchantId);

    @POST("RegisterServlet")
    Observable<BaseBean> register(@Query("tel") String tel, @Query("smsCode") String smsCode, @Query("password") String password);

    @POST("QuickSmsServlet")
    Observable<BaseBean> sendMS(@Query("tel") String tel,@Query("type") String type);

    @POST("PasswordLoginServlet")
    Observable<BaseBean<MerchantBean>> passwordLogin(@Query("tel") String tel, @Query("password") String password);

    @POST("LoginServlet")
    Observable<BaseBean<MerchantBean>> smsLogin(@Query("tel") String tel,@Query("smsCode") String smsCode);

    @POST("AutoLoginServlet")
    Observable<BaseBean<MerchantBean>> autoLogin(@Query("tel") String tel,@Query("token") String token);

    @POST("CerficationServlet")
    Observable<BaseBean> Cerfication( @Query("loginId") String loginId, @Query("merchantId") String merchantId, @Query("token") String token, @Query("merchantInfo") String merchantInfo);

    @POST("UpLogoServlet")
    Observable<BaseBean> UpLoadLogo(@Query("loginId") String loginId, @Query("merchantId") String merchantId, @Query("token") String token,@Query("logoUrl") String url );


    @GET("T_Job_Area_City_List_Servlet")
    Observable<BaseBean<CityAndCategoryBean>> getCityCategory(@Query("only") String only, @Query("login_id") String loginId);

    @GET("T_Job_Insert_Servlet")
    Observable<BaseBean<Jobs>> saveJobInfoToServer(@Query("only") String only,@Query("city_id") String city_id,
                                                   @Query("aera_id") String aera_id,@Query("type_id") String type_id,
                                                   @Query("merchant_id") String merchant_id,@Query("name") String name,
                                                   @Query("name_image") String name_image,@Query("start_date") String start_date,
                                                   @Query("stop_date") String stop_date,@Query("address") String address,
                                                   @Query("mode") String mode,@Query("money") String money,
                                                   @Query("term") String term,@Query("limit_sex") String limit_sex,
                                                   @Query("sum") String sum,@Query("girl_sum") String girl_sum,@Query("max") String max,
                                                   @Query("alike") String alike,@Query("lon") String lon,
                                                   @Query("lat") String lat,@Query("tel") String tel,
                                                   @Query("start_time") String start_time,@Query("stop_time") String stop_time,
                                                   @Query("set_place") String set_place,@Query("set_time") String set_time,
                                                   @Query("other") String other,@Query("work_content") String work_content,
                                                   @Query("work_require") String work_require,@Query("job_model") String job_model,
                                                   @Query("json_limit") String json_limit,@Query("json_welfare") String json_welfare,
                                                   @Query("json_label") String json_label);

    @GET("T_job_Model_List_Servlet")
    Observable<BaseBean<Model>> getHistroyJobFromServer(@Query("only") String only, @Query("merchant_id")String merchant_id, @Query("type")String type, @Query("count") String count);

    @GET("T_job_Model_Delete_Servlet")
    Observable<BaseBean> deleteModelInfo(@Query("only") String only,@Query("merchant_id")String merchant_id,@Query("job_id")String job_id);

    @GET("T_job_Merchant_Id_Zhong_Servlet")
    Observable<BaseBean<Model>> getJobInfoAccordingToMerchantID(@Query("only") String only,@Query("merchant_id")String merchant_id,@Query("count")String count, @Query("status")String status);

    @GET("T_Job_info_Select_JobId_Servlet")
    Observable<JobDetails> getJobDetailAccordingToJobID(@Query("only") String only,@Query("login_id")String login_id,@Query("job_id")String job_id,@Query("merchant_id")String merchant_id,@Query("alike")String alike);

    @GET("T_enroll_Agree_Servlet")
    Observable<BaseBean> overOrRemoveOffShelves(@Query("only")String only,@Query("job_id")String job_id,@Query("offer")String offer);

    @GET("T_enroll_Job_User_Servlet")
    Observable<BaseBean<AffordUser>> getPayList(@Query("only")String only,@Query("job_id")String job_id,@Query("count")String count);

    @GET("T_job_Merchant_Id_Zhong_Servlet")
    Observable<BaseBean<Model>> getPublishJobs(@Query("only")String only,@Query("merchant_id")String merchant_id,@Query("count")String count,@Query("status")String status);

    @GET("T_enroll_Job_Servlet")
    Observable<BaseBean<PublishUser>> getEnrollJobs(@Query("only") String only,@Query("job_id")String job_id,@Query("count")String count,@Query("type")String type);

    @GET("T_enroll_Offer_Servlet")
    Observable<BaseBean<PublishUser>> admitUser(@Query("only") String only,@Query("job_id") String job_id,@Query("login_id")String login_id,@Query("offer")String offer);

    /**
     *查询果聊用户信息
     */
    @GET("T_UserGroup_Servlet")//T_UserGroup_Servlet
    Observable<BaseBean<List<LCChatKitUser>>> getTalkUser(@Query("only") String only, @Query("login_id") String login_id);

    /**
     *标记鸽子
     */
    @GET("T_UserPigeon_Servlet")
    Observable<BaseBean<Pigeon>> markPigeon(@Query("only") String only, @Query("job_id") String job_id, @Query("login_id") String login_id,@Query("merchant_id") String merchant_id);

    /**
     * 获取单个兼职的详细信息
     */
    @GET("T_job_Id_Servlet")
    Observable<BaseBean<Model>> singleJobDetail(@Query("only") String only,@Query("job_id") String job_id);

    /**
     * 获取验证码
     */
    @GET("T_user_login_Check_Tel_Servlet")
    Observable<SmsCode> getCodes(@Query("only") String only, @Query("tel") String tel);

    @GET("T_user_login_Insert_Servlet")
    Observable<BaseBean<User>> registAccount(@Query("only") String only,@Query("tel") String tel,@Query("password") String password);

    /**
     * 更改兼职信息
     */
    @GET("T_job_Update_Servlet")
    Observable<BaseBean> updateJob(@Query("only") String only,@Query("job_id") String job_id, @Query("city_id") String city_id,
                                   @Query("aera_id") String aera_id, @Query("type_id") String type_id,
                                   @Query("merchant_id") String merchant_id, @Query("name") String name,
                                   @Query("name_image") String name_image, @Query("start_date") String start_date,
                                   @Query("stop_date") String stop_date, @Query("address") String address,
                                   @Query("mode") String mode, @Query("money") String money,
                                   @Query("term") String term, @Query("limit_sex") String limit_sex,
                                   @Query("sum") String sum, @Query("girl_sum") String girl_sum, @Query("max") String max,
                                   @Query("alike") String alike, @Query("lon") String lon,
                                   @Query("lat") String lat, @Query("tel") String tel,
                                   @Query("start_time") String start_time, @Query("stop_time") String stop_time,
                                   @Query("set_place") String set_place, @Query("set_time") String set_time,
                                   @Query("other") String other, @Query("work_content") String work_content,
                                   @Query("work_require") String work_require, @Query("job_model") String job_model,
                                   @Query("json_limit") String json_limit, @Query("json_welfare") String json_welfare,
                                   @Query("json_label") String json_label);

    @GET("T_job_Merchant_Id_Zhong_Servlet")
    Observable<BaseBean<Model>> merchantEmployStatus(@Query("only") String only, @Query("merchant_id") String merchant_id, @Query("count") String count, @Query("status") String status);

    @GET("T_wages_Insert_ChangServlet")
    Observable<BaseBean> checkout(@Query("only") String only, @Query("job_id") String job_id, @Query("json") String json);

//    @POST("PayWageServlet")
//    Observable<BaseBean> checkout(@Query("only") String only, @Query("job_id") String job_id, @Query("json") String json);
}
