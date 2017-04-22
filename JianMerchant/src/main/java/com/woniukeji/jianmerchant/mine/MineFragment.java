package com.woniukeji.jianmerchant.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.activity.certification.ChooseActivity;
import com.woniukeji.jianmerchant.activity.certification.StatusActivity;
import com.woniukeji.jianmerchant.activity.login.LoginNewActivity;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.entity.Version;
import com.woniukeji.jianmerchant.eventbus.AvatarEvent;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.woniukeji.jianmerchant.widget.Mdialog;
import com.woniukeji.jianmerchant.widget.UpDialog;
import com.woniukeji.jianmerchant.widget.WebViewActivity;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MineFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {


    @BindView(R.id.iv_header_bg)
    ImageView ivHeaderBg;
    @BindView(R.id.mine_shezhi)
    ImageView mineShezhi;
    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.total_wage_number)
    TextView totalWageNumber;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.total_people_number)
    TextView totalPeopleNumber;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.iv_past)
    ImageView ivPast;
    @BindView(R.id.iv_past_right)
    ImageView ivPastRight;
    @BindView(R.id.rl_custom)
    RelativeLayout rlCustom;
    @BindView(R.id.iv_feedback)
    ImageView ivFeedback;
    @BindView(R.id.iv_rck_right)
    ImageView ivRckRight;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.iv_shift)
    ImageView ivShift;
    @BindView(R.id.spinner_shift)
    Spinner spinnerShift;
    @BindView(R.id.rl_shift)
    RelativeLayout rlShift;
    @BindView(R.id.iv_logout)
    ImageView ivLogout;
    @BindView(R.id.iv_logout_right)
    ImageView ivLogoutRight;
    @BindView(R.id.rl_down_url)
    RelativeLayout rlDownUrl;
    @BindView(R.id.iv_ing)
    ImageView ivIng;
    @BindView(R.id.iv_ing_right)
    ImageView ivIngRight;
    @BindView(R.id.about)
    RelativeLayout about;
    @BindView(R.id.rl_check)
    RelativeLayout reCheck;
    @BindView(R.id.rl_logout)
    Button rlLogout;
    @BindView(R.id.rl_question)
    RelativeLayout rlQuestion;
    @BindView(R.id.iv_auth_status)
    ImageView ivAuthStatus;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.rl_mineinfo)
    RelativeLayout rlMineinfo;

    private SubscriberOnNextListener<String> baseBeanSubscriberOnNextListener;
    private Handler mHandler = new Myhandler(getActivity());
    private View view;
    private Button btnLogout;
    private String avatarUrl;
    private Spinner shift;
    private ArrayAdapter<CharSequence> adapter;
    private ImageView mine_shezhi;
    private String filePath;
    private int merchantId;
    private long loginId;
    private String token;
    private SubscriberOnNextListener<Version> versionSubscriberOnNextListener;
    private Handler handler = new Handler() {
        // 处理子线程给我们发送的消息。
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    final String url = (String) msg.obj;
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("检测到新版本，是否更新？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    UpDialog upDataDialog = new UpDialog(getActivity(), url);
                                    upDataDialog.setCanceledOnTouchOutside(false);
                                    upDataDialog.setCanceledOnTouchOutside(false);
                                    upDataDialog.show();
                                }
                            }).show();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }

    };
    private int authstatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.activity_mine, container, false);
        view = inflater.inflate(R.layout.activity_mine_new, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        initListener();
        return view;
    }

    private void initListener() {

//        merchantId = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, 0);
        loginId = (long) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0L);
        token = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_WQTOKEN, "");
        baseBeanSubscriberOnNextListener = new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {

            }
        };

        versionSubscriberOnNextListener = new SubscriberOnNextListener<Version>() {
            @Override
            public void onNext(final Version version) {
                int version1 = CommonUtils.getVersion(getActivity());
                if (Integer.valueOf(version.getAndroid_business_version()) > version1) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("检测到新版本，是否更新？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    UpDialog upDataDialog = new UpDialog(getActivity(), version.getAndroid_business_url());
                                    upDataDialog.setCanceledOnTouchOutside(false);
                                    upDataDialog.setCanceledOnTouchOutside(false);
                                    upDataDialog.show();
                                }
                            }).show();
                } else
                    TastyToast.makeText(getActivity(), "已经是最新版本！", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

            }
        };

    }

    private void initView() {
//        btnLogout = (Button) view.findViewById(R.id.btn_logout);
        mine_shezhi = (ImageView) view.findViewById(R.id.mine_shezhi);
        userName.setText((String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_GROUP_NAME, ""));
        //个人界面头像数据
        avatarUrl = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_GROUP_IMG, "");
        authstatus = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_MERCHANT_STATUS, 0);
        if(authstatus == 0){
            startActivity(new Intent(getActivity(), ChooseActivity.class));
        }else if(authstatus == 1||authstatus == 2){
            Intent intent1 = new Intent(getActivity(), StatusActivity.class);
            intent1.putExtra("type",authstatus);
            startActivity(intent1);
        }
//        Picasso.with(getActivity()).load(avatarUrl)
//                .placeholder(R.mipmap.icon_head_defult)
//                .error(R.mipmap.icon_head_defult)
//                .into(avatar);
//        shift = (Spinner) view.findViewById(R.id.spinner_shift);
//        adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.shift_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        shift.setAdapter(adapter);
//        shift.setOnItemSelectedListener(this);
        mine_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), DemoActivity.class));
            }
        });
    }

    @Override
    protected void visiableToUser() {

    }

    @Override
    protected void firstVisiableToUser() {

    }

    @Override
    protected void onSaveInfoToBundle(Bundle outState) {

    }

    @Override
    protected void onRestoreInfoFromBundle(Bundle savedInstanceState) {

    }

    /**
     * 当确认结算并且密码输入正确之后会走这里
     */
    public void onEventMainThread(AvatarEvent event) {
        if (event.getBitmap() != null) {
            avatar.setImageBitmap(event.getBitmap());
            HttpMethods.getInstance().UpLoadLogo(new ProgressSubscriber<String>(baseBeanSubscriberOnNextListener, getActivity()), String.valueOf(loginId), String.valueOf(merchantId), token, event.getUrl());

        }
    }


    public void performExit() {
//        SPUtils.deleteParams(getActivity());
//        getActivity().finish();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
        SPUtils.deleteParams(getActivity());
        startActivity(new Intent(getActivity(), LoginNewActivity.class));
        getActivity().finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) id) {
            case 0:

                break;
            case 1:
//                Constants.JIANGUO_USING = Constants.JIANGUO_xiao;
                performExit();
                break;
            case 2:
//                Constants.JIANGUO_USING = Constants.JIANGUO_TEST;
                performExit();
                break;
            case 3:
//                Constants.JIANGUO_USING = Constants.JIANGUO_TEST2;
                performExit();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick({R.id.avatar, R.id.rl_question, R.id.rl_check, R.id.rl_down_url, R.id.rl_custom, R.id.iv_feedback, R.id.iv_rck_right, R.id.rl_feedback, R.id.about, R.id.rl_logout,R.id.rl_mineinfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mineinfo:

                break;
//            case R.id.avatar:
//                MultiImageSelectorActivity.startSelect(getActivity(), 0, 1, 0);
//                break;
            case R.id.rl_custom:
                Mdialog mdialog = new Mdialog(getActivity(), "010-53350021");
                mdialog.show();
                break;
            case R.id.iv_feedback:

                break;
            case R.id.rl_question:
                Intent intent1 = new Intent(getActivity(), WebViewActivity.class);
                intent1.putExtra("url", "http://101.200.205.243:8080/problem.html");
                getActivity().startActivity(intent1);
                break;
            case R.id.rl_check:
                HttpMethods.getInstance().getVersion(new ProgressSubscriber<Version>(versionSubscriberOnNextListener, getActivity()));
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.rl_down_url:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.woniukeji.jianguo");
                intent.setData(content_url);
                startActivity(intent);

                break;
            case R.id.about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.rl_logout:
                SweetAlertDialog sweetAlertDialog1 = new SweetAlertDialog(getActivity());
                sweetAlertDialog1.setCancelable(false);
                sweetAlertDialog1
                        .setTitleText("确定要退出吗？")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                SPUtils.deleteParams(getActivity());
                                startActivity(new Intent(getActivity(), LoginNewActivity.class));
                                sweetAlertDialog.dismiss();
                                getActivity().finish();
                            }
                        })
                        .show();

                break;
        }
    }




    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(activity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    public void upLoadQiNiu(final Context context, final String key, final String filePath) {
        String commonUploadToken = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, "");
        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(filePath, key, commonUploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
            }
        }, new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    public void progress(String key, double percent) {

                    }
                }, null));

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
