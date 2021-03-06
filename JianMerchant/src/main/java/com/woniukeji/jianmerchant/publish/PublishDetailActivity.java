package com.woniukeji.jianmerchant.publish;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.CityAndCategoryBean;
import com.woniukeji.jianmerchant.entity.CityCategory;
import com.woniukeji.jianmerchant.entity.JobDetails;
import com.woniukeji.jianmerchant.entity.Jobs;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.entity.PickType;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.BitmapUtils;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.MD5Coder;
import com.woniukeji.jianmerchant.utils.QiNiu;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.woniukeji.jianmerchant.widget.DatePickerPopuWin;
import com.woniukeji.jianmerchant.widget.TimePickerPopuWin;
import com.woniukeji.jianmerchant.widget.TypePickerPopuWin;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

public class PublishDetailActivity extends BaseActivity {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 9;
    @BindView(R.id.img_job)
    CircleImageView imgJob;
    @BindView(R.id.tv_loc)
    TextView tvLoc;
    @BindView(R.id.split)
    ImageView split;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv_publish_location)
    TextView tvPublishLocation;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    @BindView(R.id.tv_lev)
    TextView tvLev;
    @BindView(R.id.level_split)
    ImageView levelSplit;
    @BindView(R.id.level_img)
    ImageView levelImg;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.rl_part_level)
    RelativeLayout rlPartClass;
    @BindView(R.id.tv_cate)
    TextView tvCate;
    @BindView(R.id.categorysplit)
    ImageView categorysplit;
    @BindView(R.id.img_category)
    ImageView imgCategory;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.rl_category)
    RelativeLayout rlCategory;
    @BindView(R.id.tv_ti)
    TextView tvTi;
    @BindView(R.id.titlesplit)
    ImageView titlesplit;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.paysplit)
    ImageView paysplit;
    @BindView(R.id.img_title)
    ImageView imgTitle;
    @BindView(R.id.tv_pay_method)
    TextView tvPayMethod;
    @BindView(R.id.rl_pay_method)
    RelativeLayout rlPayMethod;
    @BindView(R.id.tv_wa)
    TextView tvWa;
    @BindView(R.id.wagesplit)
    ImageView wagesplit;
    @BindView(R.id.tv_wages_method)
    TextView tvWagesMethod;
    @BindView(R.id.et_wages)
    EditText etWages;
    @BindView(R.id.rl_wages)
    RelativeLayout rlWages;
    @BindView(R.id.tv_s)
    TextView tvS;
    @BindView(R.id.sex_split)
    ImageView sexSplit;
    @BindView(R.id.img_sex)
    ImageView imgSex;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tv_cou)
    TextView tvCou;
    @BindView(R.id.count_split)
    ImageView countSplit;
    @BindView(R.id.et_boy_count)
    EditText etBoyCount;
    @BindView(R.id.tv_boy_unit)
    TextView tvBoyUnit;
    @BindView(R.id.et_girl_count)
    EditText etGirlCount;
    @BindView(R.id.rl_limits)
    RelativeLayout rlLimits;
    @BindView(R.id.count_split1)
    ImageView countSplit1;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.rl_no_limits)
    RelativeLayout rlNoLimits;
    @BindView(R.id.rl_count)
    RelativeLayout rlCount;
    @BindView(R.id.tv_pos)
    TextView tvPos;
    @BindView(R.id.position_split)
    ImageView positionSplit;
    @BindView(R.id.img_loc)
    ImageView imgLoc;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.et_detail_position)
    EditText etDetailPosition;
    @BindView(R.id.rl_position)
    RelativeLayout rlPosition;
    @BindView(R.id.tv_da)
    TextView tvDa;
    @BindView(R.id.datesplit)
    ImageView datesplit;
    @BindView(R.id.tv_date_start)
    TextView tvDateStart;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_date_end)
    TextView tvDateEnd;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.tv_tim)
    TextView tvTim;
    @BindView(R.id.timesplit)
    ImageView timesplit;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_center)
    TextView tvTimeCenter;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.tv_coll)
    TextView tvColl;
    @BindView(R.id.collectionsplit)
    ImageView collectionsplit;
    @BindView(R.id.et_collection_position)
    EditText etCollectionPosition;
    @BindView(R.id.rl_collection_position)
    RelativeLayout rlCollectionPosition;
    @BindView(R.id.tv_collti)
    TextView tvCollti;
    @BindView(R.id.colltimesplit)
    ImageView colltimesplit;
    @BindView(R.id.img_collection_time)
    ImageView imgCollectionTime;
    @BindView(R.id.et_collection_time)
    EditText etCollectionTime;
    @BindView(R.id.rl_collection_time)
    RelativeLayout rlCollectionTime;
    @BindView(R.id.tv_t)
    TextView tvT;
    @BindView(R.id.telsplit)
    ImageView telsplit;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.rl_tel)
    RelativeLayout rlTel;
    @BindView(R.id.et_work_content)
    EditText etWorkContent;
    @BindView(R.id.et_work_require)
    EditText etWorkRequire;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_publish)
    Button btnPublish;
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.ll_change)
    LinearLayout llChange;
    @BindView(R.id.ll_publish)
    LinearLayout llPublish;
    @BindView(R.id.tag_flow_qualification)
    TagFlowLayout flow_qualification;
    @BindView(R.id.tag_flow_welfare)
    TagFlowLayout flow_welfare;
    @BindView(R.id.tag_flow_partjob_tab)
    TagFlowLayout flow_partjob;
    //顺序对应不能改变，否则id和服务器不同步
    private String[] partHot = new String[]{"短期", "长期", "实习生", "旅行"};
    private String[] payMethods = new String[]{"月结", "周结", "日结", "旅行","完工结"};
    private String[] wagesMethods = new String[]{"元/月", "元/周", "元/天", "元/小时", "元/次", "义工", "面议"};
    private String[] sexs = new String[]{"仅限女", "仅限男", "不限男女", "男女各需"};
    private String[] second = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    private JobDetails.TMerchantEntity merchantInfo = new JobDetails.TMerchantEntity();
    private JobDetails.TJobInfoEntity jobinfo = new JobDetails.TJobInfoEntity();
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = PublishDetailActivity.this;
    private String city_id = "";// 城市ID

    private String aera_id = "";//地区ID

    private String type_id = "";//兼职类型ID

    private String merchant_id = "";//    商家ID

    private String name = "";//      兼职名称

    private String name_image = "http://v3.jianguojob.com/logo.png";//    兼职图片

    private String start_date = "";//    开始日期

    private String stop_date = "";//  结束日期

    private String address = "";//        地址

    private String mode;//   结算方式（0=月结，1=周结，2=日结，3=旅行）

    private String money;//        钱

    private String term = "2";//    期限（0=月结，1=周结，2=日结，3=小时结，4=次，5=义工）给默认值

    private String limit_sex = "2";//         性别限制（0=只招女，1=只招男，2=不限男女，3=男女各需）

    private String sum;//        总人数
    private String girlsum;//
    private String hot;//        热门（0=普通，1=热门，2=精品，3=旅行）

    private String alike;//        相同（0=一条，时间戳=两条）

    private String tel;//         电话

    private String start_time;//        开始时间

    private String stop_time;//         结束时间

    private String set_place;//        集合地点

    private String set_time;//        集合时间

    private String other = "";//        其他

    private String work_content;//    工作内容

    private String work_require;//    工作要求
    private Context mContext = PublishDetailActivity.this;
    private int merchantid;
    private SweetAlertDialog sweetAlertDialog;
    private int MSG_GET_SUCCESS = 4;
    private int MSG_GET_FAIL = 5;
    private int MSG_GET_SINGLE_JOB_SUCESS = 6;
    private Model.ListTJobEntity listTJobEntity;
    /**
     * 限制条件
     */

    private String[] qualification;
    /**
     * 福利
     */
    private String[] welfare;
    /**
     * 兼职标签
     */
    private String[] partjob_tag;
    /**
     * 用户选择限制条件的标签
     */
    private String qualicationSelected;
    /**
     * 用户选择福利的标签
     */
    private String welfareSelected;
    /**
     * 用户选择兼职条件的标签
     */
    private String partjobSelected;
    /**
     * intent传递过来的cityAndCategoryBean对象，有工作限制，福利，兼职标签信息等
     */
    private CityAndCategoryBean cityAndCategoryBean;
    /**
     * 城市ID-->modify
     */
    private int region_id;
    /**
     * 类型ID-->modify 短期长期实习生寒暑假工
     */
    private int type_id1;
    /**
     * 岗位ID-->modify
     */
    private int category_id;
    private JsonArray partjobJsonArray;
    private JsonArray welfareJsonArray;
    private JsonArray qualificationJsonArray;
    /**
     * 模板名字
     */
    private String job_model;
    /**
     * 保存兼职信息，返回过来的jobs
     */
    private Jobs jobs;
    private JsonObject qualificationJsonObj;
    private JsonObject welfareJsonObj;
    private JsonObject labelJsonObj;
    private String region;
    private String type;
    private String category;
    private TagAdapter quaAdapter;
    private TagAdapter welAdapter;
    private TagAdapter partAdapter;
    private Model.ListTJobEntity modle;
    private boolean isFromItem = false;
    private JsonArray labelJsonArray;
    private Intent intent;
    private String[] qualificationSplit;
    private String[] welfareSplit;
    private String[] labelSplit;
    private StringBuffer qualificationOther;
    private StringBuffer welfareOther;
    private StringBuffer labelOther;
    private boolean isFromActivity;
    private String jobid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 设置限制，福利，兼职标签String[]
     *
     * @param cityAndCategoryBean
     * @param strings
     */
    private String[] getInfoForTag(CityAndCategoryBean cityAndCategoryBean, String strings) {
        ArrayList<String> al = null;
        if (strings.equals("qualification")) {
            al = new ArrayList<>();
            for (int i = 0; i < cityAndCategoryBean.getList_t_limit().size(); i++) {
                al.add(cityAndCategoryBean.getList_t_limit().get(i).getLimit_name());
            }
            return al.toArray(new String[al.size()]);
        } else if (strings.equals("welfare")) {
            al = new ArrayList<>();
            for (int i = 0; i < cityAndCategoryBean.getList_t_welfare().size(); i++) {
                al.add(cityAndCategoryBean.getList_t_welfare().get(i).getWelfare_name());
            }
            return al.toArray(new String[al.size()]);

        } else if (strings.equals("partjob_tag")) {
            al = new ArrayList<>();
            for (int i = 0; i < cityAndCategoryBean.getList_t_label().size(); i++) {
                al.add(cityAndCategoryBean.getList_t_label().get(i).getLabel_name());
            }
            return al.toArray(new String[al.size()]);
        }
        return null;
    }


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;


        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PublishDetailActivity activity = (PublishDetailActivity) reference.get();
            switch (msg.what) {
                case 0:
                    if (activity.sweetAlertDialog.isShowing()) {
                        activity.sweetAlertDialog.dismiss();
                    }
                    String sucessMessage = "操作成功！";
                    Toast.makeText(activity, sucessMessage, Toast.LENGTH_SHORT).show();
                    activity.finish();
                    break;
                case 1:
                    if (activity.sweetAlertDialog.isShowing()) {
                        activity.sweetAlertDialog.dismiss();
                    }
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;

//                case 4:
//                    activity.cityAndCategoryBean = (CityAndCategoryBean) msg.obj;
//                    break;
                case 5:
                    String Message = (String) msg.obj;
                    Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();
                    activity.finish();
                    break;
                case 6:
                    BaseBean<Model> obj = (BaseBean<Model>) msg.obj;
                    activity.listTJobEntity = obj.getData().getT_job();//这里有问题,由于返回的实体bean变化导致的
                    activity.initModleData(activity.listTJobEntity);
                    break;
                case 2:
                    //选择器返回字符
                    String size = (String) msg.obj;
                    int type = msg.arg1;
                    switch (type) {

                        case 7://开始日期
                            activity.tvDateStart.setText(size);//size -->xxxx-xx-xx
                            activity.start_date = String.valueOf(DateUtils.getLongTime(size));
                            break;
                        case 8:
                            activity.tvDateEnd.setText(size);
                            activity.stop_date = String.valueOf(DateUtils.getLongTime(size));
                            break;
                        case 9:
                            StringBuilder sb1 = new StringBuilder();
                            sb1.append(size).insert(2, ":");
                            activity.tvTimeStart.setText(sb1);
                            activity.start_time = size;
                            break;
                        case 10:
                            StringBuilder sb = new StringBuilder();
                            sb.append(size).insert(2, ":");
                            activity.tvTimeEnd.setText(sb);
                            activity.stop_time = size;
                            break;
                        case 11:
                            activity.etCollectionTime.setText(size);
                            activity.set_time = size;
                            break;

                    }
                    break;
                case 3:
                    //选择器返回字符
                    PickType pickType = (PickType) msg.obj;
                    int mType = msg.arg1;
                    switch (mType) {
                        case 0://区域
//                            activity.tvPublishLocation.setText(pickType.getPickName());
//                            activity.tvPublishLocation.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.tvPosition.setText("");//选择城市的时候区域设置为空
                            activity.tvPosition.setHint("请选择工作地点");
                            activity.tvPosition.setHintTextColor(activity.getResources().getColor(R.color.hint));
                            break;
//                        case 1://兼职级别 实习生什么东西的
//                            activity.tvHot.setText(pickType.getPickName());
//                            activity.tvHot.setTextColor(activity.getResources().getColor(R.color.black));
//                            activity.hot = pickType.getPickId();
//                            break;
//                        case 2://兼职种类
//                            activity.tvCategory.setText(pickType.getPickName());
//                            activity.tvCategory.setTextColor(activity.getResources().getColor(R.color.black));
//                            activity.type_id = pickType.getPickId();
//                            break;
                        case 3://结算方式
                            activity.tvPayMethod.setText(pickType.getPickName());
                            activity.tvPayMethod.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.mode = pickType.getPickId();
                            break;
                        case 4://薪酬
                            activity.tvWagesMethod.setText(pickType.getPickName());
                            activity.term = pickType.getPickId();
                            break;
                        case 5://性别选择
                            activity.tvSex.setText(pickType.getPickName());
                            activity.tvSex.setTextColor(activity.getResources().getColor(R.color.black));
                            if (pickType.getPickName().equals("男女各需")) {
                                activity.rlLimits.setVisibility(View.VISIBLE);
                                activity.rlNoLimits.setVisibility(View.GONE);
                            } else {
                                activity.rlLimits.setVisibility(View.GONE);
                                activity.rlNoLimits.setVisibility(View.VISIBLE);
                            }
                            activity.limit_sex = pickType.getPickId();
                            break;
                        case 6://具体位置
                            activity.tvPosition.setText(pickType.getPickName());
                            activity.tvPosition.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.aera_id = pickType.getPickId();
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.img_job, R.id.btn_change, R.id.rl_location, R.id.rl_part_level, R.id.rl_category, R.id.rl_pay_method, R.id.tv_wages_method, R.id.rl_sex, R.id.rl_position, R.id.tv_date_start, R.id.tv_date_end, R.id.tv_time_start, R.id.tv_time_end, R.id.rl_collection_time, R.id.btn_preview, R.id.btn_save, R.id.btn_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_job:
                //单选多选,requestCode,最多选择数，单选模式
                MultiImageSelectorActivity.startSelect(PublishDetailActivity.this, 0, 1, 0);
                break;

//            case R.id.rl_location:
//                List<PickType> listLoc = new ArrayList<>();
//                for (int i = 0; i < cityAndCategoryBean.getList_t_city2().size(); i++) {
//                    PickType pickType = new PickType();
//                    pickType.setPickId(String.valueOf(cityAndCategoryBean.getList_t_city2().get(i).getId()));
//                    pickType.setPickName(cityAndCategoryBean.getList_t_city2().get(i).getCity());
//                    listLoc.add(pickType);
//                }
//                TypePickerPopuWin pickerLoc = new TypePickerPopuWin(context, listLoc, mHandler, 0);
//                pickerLoc.showShareWindow();
//                pickerLoc.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                break;
//            case R.id.rl_part_level:
//                List<PickType> listTemp = new ArrayList<>();
//                for (int i = 0; i < partHot.length; i++) {
//                    PickType pickType = new PickType();
//                    pickType.setPickId(String.valueOf(i));
//                    pickType.setPickName(partHot[i]);
//                    listTemp.add(pickType);
//                }
//                TypePickerPopuWin pickerPopup = new TypePickerPopuWin(context, listTemp, mHandler, 1);
//                pickerPopup.showShareWindow();
//                pickerPopup.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                break;
//            case R.id.rl_category:
//                List<PickType> listCate = new ArrayList<>();
//                for (int i = 0; i < cityAndCategoryBean.getList_t_type().size(); i++) {
//                    PickType pickType = new PickType();
//                    pickType.setPickId(String.valueOf(cityAndCategoryBean.getList_t_type().get(i).getId()));
//                    pickType.setPickName(cityAndCategoryBean.getList_t_type().get(i).getType_name());
//                    listCate.add(pickType);
//                }
//                TypePickerPopuWin pickerCategory = new TypePickerPopuWin(context, listCate, mHandler, 2);
//                pickerCategory.showShareWindow();
//                pickerCategory.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                break;
            case R.id.rl_pay_method:
                List<PickType> listpay = new ArrayList<>();
                for (int i = 0; i < payMethods.length; i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(i));
                    pickType.setPickName(payMethods[i]);
                    listpay.add(pickType);
                }
                TypePickerPopuWin pickerPayMethod = new TypePickerPopuWin(context, listpay, mHandler, 3);
                pickerPayMethod.showShareWindow();
                pickerPayMethod.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_wages_method:
                List<PickType> listwage = new ArrayList<>();
                for (int i = 0; i < wagesMethods.length; i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(i));
                    pickType.setPickName(wagesMethods[i]);
                    listwage.add(pickType);
                }
                TypePickerPopuWin pickerWagesMethod = new TypePickerPopuWin(context, listwage, mHandler, 4);
                pickerWagesMethod.showShareWindow();
                pickerWagesMethod.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_sex:
                List<PickType> listSex = new ArrayList<>();
                for (int i = 0; i < sexs.length; i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(i));
                    pickType.setPickName(sexs[i]);
                    listSex.add(pickType);
                }
                TypePickerPopuWin pickerSex = new TypePickerPopuWin(context, listSex, mHandler, 5);
                pickerSex.showShareWindow();
                pickerSex.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.rl_position:
                //城市id选择后才能选择区域
                if (String.valueOf(region_id) != null && !String.valueOf(region_id).equals("")) {
                    List<PickType> listAre = new ArrayList<>();
                    for (int i = 0; i < cityAndCategoryBean.getList_t_city2().get(region_id - 1).getList_t_area().size(); i++) {
                        PickType pickType = new PickType();
                        pickType.setPickId(String.valueOf(cityAndCategoryBean.getList_t_city2().get(region_id - 1).getList_t_area().get(i).getId()));
                        pickType.setPickName(cityAndCategoryBean.getList_t_city2().get(region_id - 1).getList_t_area().get(i).getArea_name());
                        listAre.add(pickType);
                    }
                    TypePickerPopuWin pickerAre = new TypePickerPopuWin(context, listAre, mHandler, 6);
                    pickerAre.showShareWindow();
                    pickerAre.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                } else
                    showShortToast("请先选择发布区域");
                break;
            case R.id.tv_date_start:
                DatePickerPopuWin pickerPopup1 = new DatePickerPopuWin(context, mHandler, 7);
                pickerPopup1.showShareWindow();
                pickerPopup1.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_date_end:
                DatePickerPopuWin pickerPopup2 = new DatePickerPopuWin(context, mHandler, 8);
                pickerPopup2.showShareWindow();
                pickerPopup2.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_time_start:
                List<String> listSecond = Arrays.asList(second);
                List<String> secondlist = new ArrayList<String>(listSecond);
                List<String> hourlist = new ArrayList<String>();
                for (int i = 1; i < 25; i++) {
                    if (i < 10) {
                        hourlist.add("0" + String.valueOf(i));
                    } else
                        hourlist.add(String.valueOf(i));
                }
                TimePickerPopuWin pickerTimeS = new TimePickerPopuWin(context, hourlist, secondlist, mHandler, 9);
                pickerTimeS.showShareWindow();
                pickerTimeS.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_time_end:
                List<String> listSecondE = Arrays.asList(second);
                List<String> secondlistE = new ArrayList<String>(listSecondE);
                List<String> hourlistE = new ArrayList<String>();
                for (int i = 1; i < 25; i++) {
                    if (i < 10) {
                        hourlistE.add("0" + String.valueOf(i));
                    } else
                        hourlistE.add(String.valueOf(i));
                }
                TimePickerPopuWin pickerTimeE = new TimePickerPopuWin(context, hourlistE, secondlistE, mHandler, 10);
                pickerTimeE.showShareWindow();
                pickerTimeE.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_collection_time:
//                DatePickerPopuWin pickerPopup5=new DatePickerPopuWin(context,mHandler,11);
//                pickerPopup5.showShareWindow();
//                pickerPopup5.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_preview:
                String city_name="";
                if (CheckStatus()) {
                    jobinfo.setTitle(etTitle.getText().toString());
                    if (region != null && !region.equals("")) {
                        city_name = region;
                    } else {
                        city_name = modle.getCity_id_name();
                    }
                    jobinfo.setAddress(city_name + tvPosition.getText().toString() + etDetailPosition.getText().toString());
                    long startDate = DateUtils.getLongTime(tvDateStart.getText().toString());
                    long stopDate = DateUtils.getLongTime(tvDateEnd.getText().toString());
                    jobinfo.setStart_date(startDate);
                    jobinfo.setStop_date(stopDate);
                    jobinfo.setLimit_sex(Integer.valueOf(limit_sex));
                    jobinfo.setSet_place(etCollectionPosition.getText().toString());
                    jobinfo.setTerm(Integer.valueOf(term));
                    jobinfo.setStart_time(start_time);
                    jobinfo.setStop_time(stop_time);
                    jobinfo.setSet_time(set_time);
                    jobinfo.setWork_content(etWorkContent.getText().toString());
                    jobinfo.setWork_require(etWorkRequire.getText().toString());
                    jobinfo.setWages(money + tvWagesMethod.getText().toString());
                    //其他信息
                    qualificationOther = new StringBuffer();
                    welfareOther = new StringBuffer();
                    labelOther = new StringBuffer();
                    if (qualicationSelected != null&&intent.getAction().equals("fromFragment")) {
                        qualificationOther.append(qualicationSelected);
                    } else {
                        for (int i = 0; i < modle.getLimit_name().size(); i++) {
                            qualificationOther.append(qualification[Integer.valueOf(modle.getLimit_name().get(i))-1]);
                        }
                    }
                    if (welfareSelected != null&&intent.getAction().equals("fromFragment")) {
                        labelOther.append(welfareSelected);
                    } else {
                        for (int i = 0; i < modle.getWelfare_name().size(); i++) {
                            welfareOther.append(welfare[Integer.valueOf(modle.getWelfare_name().get(i))-1]);
                        }
                    }
                    if (partjobSelected != null&&intent.getAction().equals("fromFragment")) {
                        labelOther.append(partjobSelected);
                    } else {
                        for (int i = 0; i < modle.getLabel_name().size(); i++) {
                            labelOther.append(partjob_tag[Integer.valueOf(modle.getLabel_name().get(i))-1]);
                        }
                    }
                    jobinfo.setOther(qualificationOther.toString()+"-"+welfareOther.toString()+"-"+labelOther.toString());
                    if (limit_sex.equals("3")) {
                        int sum = Integer.valueOf(etGirlCount.getText().toString()) + Integer.valueOf(etBoyCount.getText().toString());
                        jobinfo.setSum(sum + "人");
                    } else {
                        jobinfo.setSum(etCount.getText().toString() + "人");
                    }


                    String merchantName = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_NAME, "");//王五
                    merchantInfo.setName(merchantName);
                    merchantInfo.setName_image(name_image);
                    Intent intent = new Intent(PublishDetailActivity.this, PreviewJobActivity.class);
                    intent.putExtra("jobinfo", jobinfo);
                    intent.putExtra("merchantinfo", merchantInfo);
                    startActivity(intent);
                }

                break;
            case R.id.btn_save:
                if (CheckStatus()) {
                    new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("保存为模板")
                            .setContentEdit("请输入您的模板标题")
                            .setCancelText("取消")
                            .setConfirmText("保存")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                    job_model = sweetAlertDialog.getEditContent();
                                    alike = "0";
                                    if (limit_sex.equals("3")) {//男女各需
                                        alike = "" + SystemClock.currentThreadTimeMillis();
                                        // TODO: 2016/7/26 男女各需的情况
                                        SubscriberOnNextListener<Jobs> nextListenner = new SubscriberOnNextListener<Jobs>() {

                                            @Override
                                            public void onNext(Jobs jobs) {
                                                PublishDetailActivity.this.jobs = jobs;
                                                LogUtils.i("jobs", jobs.toString());
                                                String sucessMessage = "操作成功！";
                                                Toast.makeText(PublishDetailActivity.this, sucessMessage, Toast.LENGTH_SHORT).show();
//                                                PublishDetailActivity.this.finish();
                                            }
                                        };


                                        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
                                        HttpMethods.getInstance().saveJobInfoToServer(new ProgressSubscriber<Jobs>(nextListenner, PublishDetailActivity.this),
                                                only, String.valueOf(region_id), aera_id, String.valueOf(category_id), String.valueOf(merchantid), name, name_image, start_date, stop_date,
                                                address, mode, money, term, limit_sex, etBoyCount.getText().toString(),etGirlCount.getText().toString(), String.valueOf(type_id1), alike, "0", "0", tel, start_time, stop_time, set_place, set_time, other, work_content, work_require,
                                                job_model, qualificationJsonObj!=null?qualificationJsonObj.toString():"",welfareJsonObj!=null?welfareJsonObj.toString():"", labelJsonObj!=null?labelJsonObj.toString():"");
                                    } else {
                                        alike = "0";
                                        SubscriberOnNextListener<Jobs> nextListenner = new SubscriberOnNextListener<Jobs>() {

                                            @Override
                                            public void onNext(Jobs jobs) {
                                                PublishDetailActivity.this.jobs = jobs;
                                                LogUtils.i("jobs", jobs.toString());
                                                String sucessMessage = "操作成功！";
                                                Toast.makeText(PublishDetailActivity.this, sucessMessage, Toast.LENGTH_SHORT).show();
//                                                PublishDetailActivity.this.finish();
                                            }
                                        };

                                        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
                                        HttpMethods.getInstance().saveJobInfoToServer(new ProgressSubscriber<Jobs>(nextListenner, PublishDetailActivity.this),
                                                only, String.valueOf(region_id), aera_id, String.valueOf(category_id), String.valueOf(merchantid), name, name_image, start_date, stop_date,
                                                address, mode, money, term, limit_sex, etCount.getText().toString(),"0", String.valueOf(type_id1), alike, "0", "0", tel, start_time, stop_time, set_place, set_time, other, work_content, work_require,
                                                job_model, qualificationJsonObj!=null?qualificationJsonObj.toString():"",welfareJsonObj!=null?welfareJsonObj.toString():"", labelJsonObj!=null?labelJsonObj.toString():"");
                                    }
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                }
                            }).show();
                }

                break;
            case R.id.btn_publish:

                if (CheckStatus()) {
                    if (limit_sex.equals("3")) {
                        alike = String.valueOf(System.currentTimeMillis());
                        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
                        SubscriberOnNextListener<Jobs> nextListenner = new SubscriberOnNextListener<Jobs>() {

                            @Override
                            public void onNext(Jobs jobs) {
                                PublishDetailActivity.this.jobs = jobs;
                                //将刚发布的兼职信息写入到本地
                                LogUtils.i("jobs", new Gson().toJson(jobs).toString());
                                String sucessMessage = "发布成功！";
                                Toast.makeText(PublishDetailActivity.this, sucessMessage, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        };
                        //job_model =0  不是模板
                        HttpMethods.getInstance().saveJobInfoToServer(new ProgressSubscriber<Jobs>(nextListenner, PublishDetailActivity.this),
                                only, String.valueOf(region_id), aera_id, String.valueOf(category_id), String.valueOf(merchantid), name, name_image, start_date, stop_date,
                                address, mode, money, term, limit_sex, etBoyCount.getText().toString(),etGirlCount.getText().toString(), String.valueOf(type_id1), alike, "0", "0", tel, start_time, stop_time, set_place, set_time, other, work_content, work_require,
                                "0", qualificationJsonObj!=null?qualificationJsonObj.toString():"",welfareJsonObj!=null?welfareJsonObj.toString():"", labelJsonObj!=null?labelJsonObj.toString():"");

                    } else {
                        alike = "0";
                        SubscriberOnNextListener<Jobs> nextListenner = new SubscriberOnNextListener<Jobs>() {

                            @Override
                            public void onNext(Jobs jobs) {
                                PublishDetailActivity.this.jobs = jobs;
                                //将刚发布的兼职信息写入到本地
                                LogUtils.i("jobs", new Gson().toJson(jobs).toString());
                                String sucessMessage = "发布成功！";
                                Toast.makeText(PublishDetailActivity.this, sucessMessage, Toast.LENGTH_SHORT).show();
                                PublishDetailActivity.this.finish();
                            }
                        };
                        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
                        //job_model =0  不是模板
                        HttpMethods.getInstance().saveJobInfoToServer(new ProgressSubscriber<Jobs>(nextListenner, PublishDetailActivity.this),
                                only, String.valueOf(region_id), aera_id, String.valueOf(category_id), String.valueOf(merchantid), name, name_image, start_date, stop_date,
                                address, mode, money, term, limit_sex, etCount.getText().toString(),"0", String.valueOf(type_id1), alike, "0", "0", tel, start_time, stop_time, set_place, set_time, other, work_content, work_require,
                                "0", qualificationJsonObj!=null?qualificationJsonObj.toString():"",welfareJsonObj!=null?welfareJsonObj.toString():"", labelJsonObj!=null?labelJsonObj.toString():"");

                    }
                }
                break;
            case R.id.btn_change:
                //修改
                if (CheckStatus()) {
                    if (limit_sex.equals("3")) {
                        String jobid = intent.getStringExtra("jobid");
                        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
                        alike = String.valueOf(System.currentTimeMillis());
                        SubscriberOnNextListener<BaseBean> nextListenner = new SubscriberOnNextListener<BaseBean>() {
                            @Override
                            public void onNext(BaseBean baseBean) {
                                showShortToast(baseBean.getMessage());
                            }
                        };
                        //job_model =0  不是模板
                        HttpMethods.getInstance().updateJob(new ProgressSubscriber<BaseBean>(nextListenner, PublishDetailActivity.this),only,jobid,
                                String.valueOf(region_id), aera_id, String.valueOf(category_id), String.valueOf(merchantid), name, name_image, start_date, stop_date,
                                address, mode, money, term, limit_sex, etBoyCount.getText().toString(),etGirlCount.getText().toString(), String.valueOf(type_id1), alike, "0", "0", tel, start_time, stop_time, set_place, set_time, other, work_content, work_require,
                                "0", qualificationJsonObj!=null?qualificationJsonObj.toString():"",welfareJsonObj!=null?welfareJsonObj.toString():"", labelJsonObj!=null?labelJsonObj.toString():"");

                    } else {
                        alike = "0";
                        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
                        String jobid = intent.getStringExtra("jobid");
                        SubscriberOnNextListener<BaseBean> nextListenner = new SubscriberOnNextListener<BaseBean>() {
                            @Override
                            public void onNext(BaseBean baseBean) {
                                showShortToast(baseBean.getMessage());
                            }
                        };
                        //job_model =0  不是模板
                        HttpMethods.getInstance().updateJob(new ProgressSubscriber<BaseBean>(nextListenner, PublishDetailActivity.this),only,jobid,
                                String.valueOf(region_id), aera_id, String.valueOf(category_id), String.valueOf(merchantid), name, name_image, start_date, stop_date,
                                address, mode, money, term, limit_sex, etCount.getText().toString(),"0", String.valueOf(type_id1), alike, "0", "0", tel, start_time, stop_time, set_place, set_time, other, work_content, work_require,
                                "0", qualificationJsonObj!=null?qualificationJsonObj.toString():"",welfareJsonObj!=null?welfareJsonObj.toString():"", labelJsonObj!=null?labelJsonObj.toString():"");
                    }
                }
                break;
        }
    }

    private boolean CheckStatus() {
        if (etTitle.getText().toString() == null || etTitle.getText().toString().equals("")) {
            showShortToast("请输入兼职标题");
            return false;
        } else if (etTitle.getText().toString().length() > 15) {
            showShortToast("兼职标题请控制在15字以内");
            return false;
        } else if (etDetailPosition.getText().toString() == null || etDetailPosition.getText().toString().equals("")) {
            showShortToast("请输入详细工作地址");
            return false;
        } else if (etCollectionPosition.getText().toString() == null || etCollectionPosition.getText().toString().equals("")) {
            showShortToast("请输入集合地点");
            return false;
        } else if (etCollectionTime.getText().toString() == null || etCollectionTime.getText().toString().equals("")) {
            showShortToast("请输入集合时间");
            return false;
        } else if (etTel.getText().toString() == null || etTel.getText().toString().equals("")) {
            showShortToast("请输入联系电话");
            return false;
        } else if (etWorkContent.getText().toString() == null || etWorkContent.getText().toString().equals("")) {
            showShortToast("请输入工作内容");
            return false;
        } else if (etWorkRequire.getText().toString() == null || etWorkRequire.getText().toString().equals("")) {
            showShortToast("请输入工作要求");
            return false;
        } else if (name_image == null || name_image.equals("")) {
            showShortToast("请设置兼职图片");
            return false;
        } else if (String.valueOf(region_id) == null || String.valueOf(region_id).equals("")) {
            showShortToast("请选择发布城市");
            return false;
        } else if (etWages.getText().toString() == null || etWages.getText().toString().equals("")) {
            showShortToast("请填写薪资");
            return false;
        } else if (mode == null || mode.equals("")) {
            showShortToast("请选择结算方式");
            return false;
        } else if (limit_sex == null || limit_sex.equals("")) {
            showShortToast("请选择录取性别");
            return false;
        } else if (start_date == null || start_date.equals("")) {
            showShortToast("请选择工作开始日期");
            return false;
        } else if (stop_date == null || stop_date.equals("")) {
            showShortToast("请选择工作结束日期");
            return false;
        } else if (start_time == null || start_time.equals("")) {
            showShortToast("请选择工作开始时间");
            return false;
        } else if (stop_time == null || stop_time.equals("")) {
            showShortToast("请选择工作结束时间");
            return false;
        }  else if (limit_sex.equals("3")) {//男女分别招收
            if (etBoyCount.getText().toString().equals("") || etBoyCount.getText().toString() == null || etBoyCount.getText().toString().equals("0")) {
                showShortToast("请输入男生人数");
                return false;
            }
            if (etGirlCount.getText().toString().equals("") || etGirlCount.getText().toString() == null || etGirlCount.getText().toString().equals("0")) {
                showShortToast("请输入女生人数");
                return false;
            }
        } else if (!limit_sex.equals("3")) {
            if (etCount.getText().toString().equals("") || etCount.getText().toString() == null || etCount.getText().toString().equals("0")) {
                showShortToast("请输入需要录取的人数");
                return false;
            }
        }


        start_time = String.valueOf(DateUtils.getLongTime(tvDateStart.getText().toString() + " " + tvTimeStart.getText().toString(), "yyyy-MM-dd HH:mm"));
        stop_time = String.valueOf(DateUtils.getLongTime(tvDateEnd.getText().toString() + " " + tvTimeEnd.getText().toString(), "yyyy-MM-dd HH:mm"));
        if (DateUtils.getLongTime(tvDateStart.getText().toString() + " " + tvTimeStart.getText().toString(), "yyyy-MM-dd HH:mm") < System.currentTimeMillis()) {
            showShortToast("开始时间早于当前时间，请重新选择");
            return false;
        } else if (DateUtils.getLongTime(tvDateEnd.getText().toString() + " " + tvTimeEnd.getText().toString(), "yyyy-MM-dd HH:mm") < DateUtils.getLongTime(tvDateStart.getText().toString() + " " + tvTimeStart.getText().toString(), "yyyy-MM-dd HH:mm")) {
            showShortToast("结束时间早于开始时间，请重新选择");
            return false;
        }
        name = etTitle.getText().toString();
        money = etWages.getText().toString();
        address = etDetailPosition.getText().toString();
        set_place = etCollectionPosition.getText().toString();
        set_time = etCollectionTime.getText().toString();
        tel = etTel.getText().toString();
        work_content = etWorkContent.getText().toString();
        work_require = etWorkRequire.getText().toString();
        return true;
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_publish_detail);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void initViews() {


        intent = getIntent();
        if (intent.getStringExtra("type").equals("change")) {
            jobid = intent.getStringExtra("jobid");
            //从兼职详情过来，需要修改
            isFromItem = true;//目的是不走下面的判断
            isFromActivity = true;
            getCategoryToBeanNew();
        }else if (intent.getAction().equals("fromFragment")) {
            Bundle bundle = intent.getExtras();
            region = bundle.getString("region");
            region_id = bundle.getInt("region_id");//地区id
            type = bundle.getString("type");
            type_id1 = bundle.getInt("type_id");//兼职类型id
            category = bundle.getString("category");
            category_id = bundle.getInt("category_id");//职位id
            cityAndCategoryBean = bundle.getParcelable("CityAndCategoryBean");
            qualification = getInfoForTag(cityAndCategoryBean, "qualification");
            welfare = getInfoForTag(cityAndCategoryBean, "welfare");
            partjob_tag = getInfoForTag(cityAndCategoryBean, "partjob_tag");
        } else if (intent.getAction().equals("fromItem")) {
            //获取从历史纪录里传递过来的intent
            modle = (Model.ListTJobEntity) intent.getSerializableExtra("job");
            getCategoryToBean();
            isFromItem = true;
        }
        if (!isFromItem) {
            //这以下设置流布局标签
            quaAdapter = new TagAdapter(qualification) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_qualification, false);
                    textView.setText((String) o);
                    return textView;
                }
            };
            flow_qualification.setAdapter(quaAdapter);
            welAdapter = new TagAdapter(welfare) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_welfare, false);
                    textView.setText((String) o);
                    return textView;
                }
            };
            flow_welfare.setAdapter(welAdapter);
            partAdapter = new TagAdapter(partjob_tag) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_partjob, false);
                    textView.setText((String) o);
                    return textView;
                }
            };
            flow_partjob.setAdapter(partAdapter);
            //这以上设置流布局标签
        }


        //获取限制，福利，标签String数组--
        // TODO: 2016/7/26 需要移动到历史或者模板页面

//        } else if (mType.equals("change")) {//修改兼职传过来
//            String jobid = intent.getStringExtra("jobid");
//            llPublish.setVisibility(View.GONE);
//            llChange.setVisibility(View.VISIBLE);
//            GetSingleJobTask getSingleJobTask = new GetSingleJobTask(jobid);
//            getSingleJobTask.execute();
//        }
//        sweetAlertDialog = new SweetAlertDialog(PublishDetailActivity.this, SweetAlertDialog.PROGRESS_TYPE);

    }

    private void getCategoryToBeanNew() {
        SubscriberOnNextListener<CityAndCategoryBean> onNextListenner = new SubscriberOnNextListener<CityAndCategoryBean>() {

            @Override
            public void onNext(CityAndCategoryBean cityAndCategoryBean) {
                PublishDetailActivity.this.cityAndCategoryBean = cityAndCategoryBean;
                qualification = getInfoForTag(cityAndCategoryBean, "qualification");
                welfare = getInfoForTag(cityAndCategoryBean, "welfare");
                partjob_tag = getInfoForTag(cityAndCategoryBean, "partjob_tag");
                //这以下设置流布局标签
                quaAdapter = new TagAdapter(qualification) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_qualification, false);
                        textView.setText((String) o);
                        return textView;
                    }
                };
                flow_qualification.setAdapter(quaAdapter);
                welAdapter = new TagAdapter(welfare) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_welfare, false);
                        textView.setText((String) o);
                        return textView;
                    }
                };
                flow_welfare.setAdapter(welAdapter);
                partAdapter = new TagAdapter(partjob_tag) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_partjob, false);
                        textView.setText((String) o);
                        return textView;
                    }
                };
                flow_partjob.setAdapter(partAdapter);
//                EventBus.getDefault().post(new ChangeJobEvent());
                changeJob();
                //这以上设置流布局标签
//                if (isFromItem) {
//                    //如果是从item过来的走下面
//                    initModleData(modle);
//                }

            }
        };

        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        int loginId = (int) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        HttpMethods.getInstance().getCityAndCategory(new ProgressSubscriber<CityAndCategoryBean>(onNextListenner, this), only, String.valueOf(loginId));
    }

    private void changeJob() {
        llPublish.setVisibility(View.GONE);
        llChange.setVisibility(View.VISIBLE);

        //获取单个id的兼职信息
        BackgroundSubscriber<Model> subscriber = new BackgroundSubscriber<Model>(new SubscriberOnNextListener<Model>() {
            @Override
            public void onNext(Model model) {
                modle = model.getT_job();
                //最后在去初始化界面
                initModleData(modle);
            }
        },mContext);
        HttpMethods.getInstance().singleJobDetail(subscriber, intent.getStringExtra("jobid"));
    }

    /**
     * 访问网络获取兼职类别
     */
    private void getCategoryToBean() {
        SubscriberOnNextListener<CityAndCategoryBean> onNextListenner = new SubscriberOnNextListener<CityAndCategoryBean>() {

            @Override
            public void onNext(CityAndCategoryBean cityAndCategoryBean) {
                PublishDetailActivity.this.cityAndCategoryBean = cityAndCategoryBean;
                qualification = getInfoForTag(cityAndCategoryBean, "qualification");
                welfare = getInfoForTag(cityAndCategoryBean, "welfare");
                partjob_tag = getInfoForTag(cityAndCategoryBean, "partjob_tag");
                //这以下设置流布局标签
                quaAdapter = new TagAdapter(qualification) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_qualification, false);
                        textView.setText((String) o);
                        return textView;
                    }
                };
                flow_qualification.setAdapter(quaAdapter);
                welAdapter = new TagAdapter(welfare) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_welfare, false);
                        textView.setText((String) o);
                        return textView;
                    }
                };
                flow_welfare.setAdapter(welAdapter);
                partAdapter = new TagAdapter(partjob_tag) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_detail_tv, flow_partjob, false);
                        textView.setText((String) o);
                        return textView;
                    }
                };
                flow_partjob.setAdapter(partAdapter);
                //这以上设置流布局标签
                if (isFromItem) {
                    //如果是从item过来的走下面
                    initModleData(modle);
                }

            }
        };

        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        int loginId = (int) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        HttpMethods.getInstance().getCityAndCategory(new ProgressSubscriber<CityAndCategoryBean>(onNextListenner, this), only, String.valueOf(loginId));
    }

    private void initModleData(Model.ListTJobEntity modle) {
//        tvPublishLocation.setText(modle.getCity_id_name());
//        tvPublishLocation.setTextColor(getResources().getColor(R.color.black));
        region_id = modle.getCity_id();
        type_id1 = modle.getType_id();
        aera_id = String.valueOf(modle.getArea_id());
        tvPosition.setText(modle.getArea_id_name());
        tvPosition.setTextColor(getResources().getColor(R.color.black));
//        tvCategory.setText(modle.getType_id_name());
//        tvCategory.setTextColor(getResources().getColor(R.color.black));
        etTitle.setText(modle.getName());
        name_image = modle.getName_image();
        Picasso.with(mContext).load(name_image)
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(imgJob);
        tvDateStart.setText(DateUtils.getTime(Long.parseLong(modle.getStart_date()), "yyyy-MM-dd"));
        tvDateEnd.setText(DateUtils.getTime(Long.parseLong(modle.getStop_date()), "yyyy-MM-dd"));
        start_date = modle.getStart_date();
        stop_date = modle.getStop_date();
        tvTimeStart.setText(DateUtils.getHm(Long.parseLong(modle.getInfo_start_time())));
        tvTimeEnd.setText(DateUtils.getHm(Long.parseLong(modle.getInfo_stop_time())));
        start_time = modle.getInfo_start_time();
        stop_time = modle.getInfo_stop_time();
        etDetailPosition.setText(modle.getAddress());
        tvPayMethod.setText(payMethods[modle.getMode()]);
        tvPayMethod.setTextColor(getResources().getColor(R.color.black));
        mode = String.valueOf(modle.getMode());
        etWages.setText(String.valueOf(modle.getMoney()));
        etWages.setTextColor(getResources().getColor(R.color.black));
        tvWagesMethod.setText(wagesMethods[modle.getTerm()]);
        term = String.valueOf(modle.getTerm());
        sum = String.valueOf(modle.getSum());
        limit_sex = String.valueOf(modle.getLimit_sex());
//        if (limit_sex.equals("30") || limit_sex.equals("31")) {
        if (limit_sex.equals("3") && limit_sex != null) {
            tvSex.setText("男女各需");
            rlLimits.setVisibility(View.VISIBLE);
            rlNoLimits.setVisibility(View.GONE);
            etGirlCount.setText(modle.getGirl_sum()+"");
//            etBoyCount.setText(sum);
            etBoyCount.setText(modle.getBoySum());
        } else {
            tvSex.setText(sexs[modle.getLimit_sex()]);
            rlLimits.setVisibility(View.GONE);
            rlNoLimits.setVisibility(View.VISIBLE);
        }
        tvSex.setTextColor(getResources().getColor(R.color.black));

//        if (limit_sex.equals("30")) {
//
//        } else if (limit_sex.equals("31")) {
//
//        } else {
//            etCount.setText(sum);
//        }
        if (!limit_sex.equals("3") && limit_sex != null) {
            etCount.setText(sum);
        }

//        tvHot.setText(partHot[modle.getMax()]);
        hot = String.valueOf(modle.getMax());
//        tvHot.setTextColor(getResources().getColor(R.color.black));
        etTel.setText(modle.getInfo_tel());

        etWorkContent.setText(modle.getInfo_work_content());
        etWorkRequire.setText(modle.getInfo_work_require());
        etCollectionPosition.setText(modle.getInfo_set_place());
        etCollectionTime.setText(modle.getInfo_set_time());

        //根据model中的数据设置，写一个假的例子

        quaAdapter.setSelectedList(getCurrentSet(modle,"qualification"));
        welAdapter.setSelectedList(getCurrentSet(modle,"welfare"));
        partAdapter.setSelectedList(getCurrentSet(modle,"label"));
        if (flow_qualification.getSelectedList().size()>0) {
            Iterator<Integer> qualificationIterator = flow_qualification.getSelectedList().iterator();
            qualificationJsonObj = new JsonObject();
            qualificationJsonArray = new JsonArray();
            while (qualificationIterator.hasNext()) {
                qualificationJsonArray.add(1+qualificationIterator.next()+"");
            }
            qualificationJsonObj.add("list_t_limit", qualificationJsonArray);
        }

        if (flow_welfare.getSelectedList().size() > 0) {
            Iterator<Integer> welfareIterator = flow_welfare.getSelectedList().iterator();
            welfareJsonObj = new JsonObject();
            welfareJsonArray = new JsonArray();
            while (welfareIterator.hasNext()) {
                welfareJsonArray.add(1+welfareIterator.next()+"");
            }
            welfareJsonObj.add("list_t_welfare", welfareJsonArray);
        }

        if (flow_partjob.getSelectedList().size() > 0) {
            Iterator<Integer> labelIterator = flow_partjob.getSelectedList().iterator();
            labelJsonObj = new JsonObject();
            partjobJsonArray = new JsonArray();
            while (labelIterator.hasNext()) {
                partjobJsonArray.add(1+labelIterator.next()+"");
            }
            labelJsonObj.add("list_t_label", partjobJsonArray);
        }
    }

//    public void onEventMainThread(ChangeJobEvent event) {
//        //限制只能修改不能发布22
//
//    }

    private Set<Integer> getCurrentSet(Model.ListTJobEntity modle,String name) {
        HashSet<Integer> set = new HashSet<>();
        if (name.equals("qualification")) {
            if (modle.getLimit_name().size() != 0 && !modle.getLimit_name().get(0).equals("")) {
                for (int i = 0; i < modle.getLimit_name().size(); i++) {
                    set.add(Integer.valueOf(modle.getLimit_name().get(i)) - 1);
                }
            } else {
                return null;
            }
        } else if (name.equals("welfare")) {
            if (modle.getWelfare_name().size() != 0 && !modle.getWelfare_name().get(0).equals("")) {
                for (int i = 0; i < modle.getWelfare_name().size(); i++) {
                    set.add(Integer.valueOf(modle.getWelfare_name().get(i)) - 1);
                }
            } else {
                return null;
            }

        } else if (name.equals("label")) {
            if (modle.getLabel_name().size() != 0 && !modle.getLabel_name().get(0).equals("")) {
                for (int i = 0; i < modle.getLabel_name().size(); i++) {
                    set.add(Integer.valueOf(modle.getLabel_name().get(i)) - 1);
                }
            } else {
                return null;
            }
        }
        return set;
    }

    @Override
    public void initListeners() {
        etWorkContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.et_work_content) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        etWorkRequire.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.et_work_require) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        int loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
//        GetTask getTask = new GetTask(String.valueOf(loginId));
//        getTask.execute();

        //给标签添加监听
        flow_qualification.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                    qualicationSelected = getSelectedTagFromSet(selectPosSet,qualification);
                if (qualicationSelected != null) {
                    qualificationSplit = qualicationSelected.split(",");
                }
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    qualificationJsonObj = new JsonObject();
                    qualificationJsonArray = new JsonArray();
                    while (iterator.hasNext()) {
                        Integer next = iterator.next();
                        qualificationJsonArray.add(1+next + "");
                    }
                    qualificationJsonObj.add("list_t_limit", qualificationJsonArray);


            }
        });
        flow_welfare.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                    welfareSelected =getSelectedTagFromSet(selectPosSet,welfare);
                    if (welfareSelected != null) {
                        welfareSplit = welfareSelected.split(",");
                    }
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    welfareJsonObj = new JsonObject();
                    welfareJsonArray = new JsonArray();
                    while (iterator.hasNext()) {
                        Integer next = iterator.next();
                        welfareJsonArray.add(1+next + "");
                    }
                    welfareJsonObj.add("list_t_welfare", welfareJsonArray);
            }
        });
        flow_partjob.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                    partjobSelected =getSelectedTagFromSet(selectPosSet,partjob_tag);
                    if (partjobSelected!=null) {
                        labelSplit = partjobSelected.split(",");
                        }
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    labelJsonObj = new JsonObject();
                    labelJsonArray = new JsonArray();
                    while (iterator.hasNext()) {
                        Integer next = iterator.next();
                        labelJsonArray.add(1+next + "");
                    }
                    labelJsonObj.add("list_t_label", labelJsonArray);
            }
        });
    }

    /**
     * 从set集合中找出选择的标签，并且赋值给目标字符串
     *
     * @param selectPosSet
     * @param contents
     */
    private String getSelectedTagFromSet(Set<Integer> selectPosSet, String[] contents) {
        Iterator<Integer> iterator = selectPosSet.iterator();
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            Integer pos = iterator.next();
            sb.append(contents[pos]);
            sb.append(",");
        }
        if (selectPosSet.size() == 0) {
            return null;
        }
        return sb.toString().substring(0, sb.toString().lastIndexOf(","));


    }

    @Override
    public void initData() {
        merchantid = (int) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_MERCHANT_ID, 0);
    }

    @Override
    public void addActivity() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                File imgFile = new File(path.get(0));
                String choosePic = path.get(0).substring(path.get(0).lastIndexOf("."));
                String fileName = Constants.IMG_PATH + CommonUtils.generateFileName() + choosePic;
                Uri imgSource = Uri.fromFile(imgFile);
                imgJob.setImageURI(imgSource);
                File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName + ".png");
                CommonUtils.copyfile(imgFile, file, true);
                BitmapUtils.compressBitmap(file.getAbsolutePath(), 300, 300);
                QiNiu.upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName), file);
                name_image = "http://7xlell.com2.z0.glb.qiniucdn.com/" + MD5Coder.getQiNiuName(fileName);
            }
        } else if (requestCode == 1) {
//            tvBirthday.setText(data.getStringExtra("date"));
        } else if (requestCode == 2) {
//            tvDate.setText(data.getStringExtra("date"));
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
//                tvSchool.setText(data.getStringExtra("school"));
            }

        }


    }

    public class GetSingleJobTask extends AsyncTask<Void, Void, Void> {
        private final String jobId;

        GetSingleJobTask(String jobId) {
            this.jobId = jobId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getSingleJobInfo();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 获取单个兼职信息用于修改
         */
        public void getSingleJobInfo() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_SINGLE_JOB_INFO)
                    .addParams("only", only)
                    .addParams("job_id", jobId)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Model>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Model>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean, int id) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = MSG_GET_SINGLE_JOB_SUCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String loginId;

        GetTask(String loginId) {
            this.loginId = loginId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getCityCategory();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 获取城市列表和兼职种类
         */
        public void getCityCategory() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_CITY_CATEGORY)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<CityCategory>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityCategory>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean, int id) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = MSG_GET_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    public class PostPartInfoTask extends AsyncTask<Void, Void, Void> {
        private String mSum;
        private String mSex_limit;
        private String mjob_model = "0";

        PostPartInfoTask(String sum, String Sex_limit) {
            this.mSum = sum;
            this.mSex_limit = Sex_limit;
        }

        PostPartInfoTask(String sum, String Sex_limit, String job_model) {
            this.mSum = sum;
            this.mSex_limit = Sex_limit;
            this.mjob_model = job_model;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                postPartInfo();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (!sweetAlertDialog.isShowing()) {
                sweetAlertDialog.setTitleText("提交中");
                sweetAlertDialog.getProgressHelper().setBarColor(R.color.app_bg);
                sweetAlertDialog.show();
            }

            super.onPreExecute();
        }

        /**
         * 提交兼职信息
         */
        public void postPartInfo() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .post()
                    .url(Constants.POST_PART_INFO)
                    .addParams("only", only)
                    .addParams("city_id", String.valueOf(region_id))
                    .addParams("aera_id", String.valueOf(type_id1))
                    .addParams("type_id", String.valueOf(category_id))
                    .addParams("merchant_id", String.valueOf(merchantid))
                    .addParams("name", name)
                    .addParams("name_image", name_image)
                    .addParams("start_date", start_date)
                    .addParams("stop_date", stop_date)
                    .addParams("address", address)
                    .addParams("mode", mode)
                    .addParams("money", money)
                    .addParams("term", term)
                    .addParams("limit_sex", mSex_limit)
                    .addParams("sum", mSum)
                    .addParams("max", hot)
                    .addParams("alike", alike)
                    .addParams("lon", "0")
                    .addParams("lat", "0")
                    .addParams("tel", tel)
                    .addParams("start_time", start_time)
                    .addParams("stop_time", stop_time)
                    .addParams("set_place", set_place)
                    .addParams("set_time", set_time)
                    .addParams("work_content", work_content)
                    .addParams("work_require", work_require)
                    .addParams("job_model", mjob_model)
//                    .addParams("json_limit",qualificationJsonArray.toString())
//                    .addParams("json_welfare",welfareJsonArray.toString())
//                    .addParams("json_label",partjobJsonArray.toString())
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Jobs>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Jobs>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean, int id) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    /**
     * 修改兼职信息
     */
    public void postChangeInfo(String count, String mSex_limit, String job_id) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.POST_JOB_INFO)
                .addParams("only", only)
                .addParams("city_id", city_id)
                .addParams("aera_id", aera_id)
                .addParams("type_id", type_id)
                .addParams("merchant_id", String.valueOf(merchantid))
                .addParams("name", name)
                .addParams("name_image", name_image)
                .addParams("start_date", start_date)
                .addParams("stop_date", stop_date)
                .addParams("address", address)
                .addParams("mode", mode)
                .addParams("money", money)
                .addParams("term", term)
                .addParams("limit_sex", mSex_limit)
                .addParams("sum", count)
                .addParams("max", hot)
                .addParams("alike", alike)
                .addParams("lon", "0")
                .addParams("lat", "0")
                .addParams("tel", tel)
                .addParams("start_time", start_time)
                .addParams("stop_time", stop_time)
                .addParams("set_place", set_place)
                .addParams("set_time", set_time)
                .addParams("work_content", work_content)
                .addParams("work_require", work_require)
                .addParams("job_id", job_id)
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean>() {
                        }.getType());
                        return baseBean;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_POST_FAIL;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(BaseBean baseBean, int id) {
                        if (baseBean.getCode().equals("200")) {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                            message.what = MSG_POST_SUCCESS;
                            mHandler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = baseBean.getMessage();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }
                    }

                });
    }

    //自动收起键盘的操作 通过判断当前的view实例是不是edittext来决定
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
