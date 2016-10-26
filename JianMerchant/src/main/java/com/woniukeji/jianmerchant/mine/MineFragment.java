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

import com.facebook.drawee.view.SimpleDraweeView;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.activity.DemoActivity;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.login.LoginActivity;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.lang.ref.WeakReference;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MineFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {


    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(getActivity());
    private Context context = getActivity();
    private View view;
    private Button btnLogout;
    private RelativeLayout rlLogout;
    private String avatarUrl;
    private SimpleDraweeView avatar;
    private TextView userName ;
    private TextView department;
    private RelativeLayout rlShift;
    private Spinner shift;
    private ArrayAdapter<CharSequence> adapter;
    private ImageView mine_shezhi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.activity_mine, container, false);
        view = inflater.inflate(R.layout.activity_mine_new, container, false);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
//        btnLogout.setOnClickListener(this);
        rlLogout.setOnClickListener(this);

    }

    private void initView() {
//        btnLogout = (Button) view.findViewById(R.id.btn_logout);
        rlShift = (RelativeLayout) view.findViewById(R.id.rl_shift);
        rlLogout = (RelativeLayout) view.findViewById(R.id.rl_logout);
        userName = (TextView) view.findViewById(R.id.user_name);
        mine_shezhi= (ImageView) view.findViewById(R.id.mine_shezhi);
        userName.setText((String)SPUtils.getParam(getActivity(), Constants.USER_INFO, "nickname", ""));
        department = (TextView) view.findViewById(R.id.department);
        //暂无数据
        avatarUrl = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, "name_image", "");
        avatar = (SimpleDraweeView) view.findViewById(R.id.avatar);
        Uri uri = Uri.parse(avatarUrl);
        avatar.setImageURI(uri);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                SPUtils.deleteParams(getActivity());
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.rl_logout:
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity());
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog
                        .setTitleText("确定要退出吗？")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                SPUtils.deleteParams(getActivity());
                                startActivity(new Intent(getActivity(),LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .show();

                break;
            case R.id.rl_shift:

                break;
        }
    }


    public void  performExit() {
//        SPUtils.deleteParams(getActivity());
//        getActivity().finish();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
        SPUtils.deleteParams(getActivity());
        startActivity(new Intent(getActivity(),LoginActivity.class));
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




}
