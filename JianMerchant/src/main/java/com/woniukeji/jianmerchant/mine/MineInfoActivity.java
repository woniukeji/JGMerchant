package com.woniukeji.jianmerchant.mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseInfo;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.city.CityBean;
import com.woniukeji.jianmerchant.widget.city.RegionInfo;
import com.woniukeji.jianmerchant.widget.city.dao.RegionDAO;
import com.woniukeji.jianmerchant.widget.city.view.OptionsPickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/25.
 */

public class MineInfoActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.wallet_back)
    ImageView walletBack;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.racharge_title)
    RelativeLayout rachargeTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_companyname)
    EditText tvCompanyname;
    @BindView(R.id.tv_contacts)
    EditText tvContacts;
    @BindView(R.id.tv_email)
    EditText tvEmail;
//    @BindView(R.id.tv_address)
//    TextView tvAddress;
    @BindView(R.id.tv_introduce)
    EditText tvIntroduce;
    @BindView(R.id.tv_idnumber)
    TextView tvIdnumber;
    @BindView(R.id.et_organization_number)
    TextView etOrganizationNumber;
    private String companyname;
    private String phone;
    private String email;
    private String address;
    private String introduce;
    OptionsPickerView pvOptions;
    private TextView tvAddress;
    private MerchantBean merchantBean = new MerchantBean();

    static ArrayList<RegionInfo> item1;

    static ArrayList<ArrayList<CityBean>> item2 = new ArrayList<ArrayList<CityBean>>();

    private SubscriberOnNextListener<BaseInfo> infoSubscriber = new SubscriberOnNextListener<BaseInfo>() {
        @Override
        public void onNext(BaseInfo baseInfo) {
            tvAccount.setText(baseInfo.getTel());
            if(baseInfo.getBusiness_type() == 1){
                tvName.setText(baseInfo.getNickname());

            }else if(baseInfo.getBusiness_type() == 2 || baseInfo.getBusiness_type() == 3){
                tvName.setText(baseInfo.getCompanyName());
            }
            if(baseInfo.getContact_name() != null){
                tvCompanyname.setText(baseInfo.getContact_name());
            }
            if(baseInfo.getContact_phone() != null){
                tvContacts.setText(baseInfo.getContact_phone());
            }
            if(baseInfo.getEmail() != null){
                tvEmail.setText(baseInfo.getEmail());
            }
            if(baseInfo.getCompanyAdress() != null){
                tvAddress.setText(baseInfo.getCompanyAdress());
            }
            if(baseInfo.getIntroduce() != null){
                tvIntroduce.setText(baseInfo.getIntroduce());
            }

            if(baseInfo.getId_number() != null){
                tvIdnumber.setText(baseInfo.getId_number());
            }
            if(baseInfo.getBus_licence_num() != null){
                etOrganizationNumber.setText(baseInfo.getBus_licence_num());
            }


        }
    };

    private SubscriberOnNextListener<BaseInfo> backSubscriber = new SubscriberOnNextListener<BaseInfo>() {
        @Override
        public void onNext(BaseInfo baseInfo) {
            showLongToast("信息保存成功");
        }
    };
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            System.out.println(System.currentTimeMillis());
            // 三级联动效果

            pvOptions.setPicker(item1, item2, true);
            pvOptions.setCyclic(true, false, true);
            pvOptions.setSelectOptions(0, 0, 0);
            tvAddress.setClickable(true);
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvAddress = (TextView) findViewById(R.id.tv_address);
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mineinfo);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        pvOptions = new OptionsPickerView(this);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println(System.currentTimeMillis());
                if (item1 != null && item2 != null) {
                    handler.sendEmptyMessage(0x123);
                    return;
                }
                item1 = (ArrayList<RegionInfo>) RegionDAO.getProvences();
                for (RegionInfo regionInfo : item1) {
                    item2.add((ArrayList<CityBean>) RegionDAO.getCityOnParent(regionInfo.getAdcode()));
                }
                handler.sendEmptyMessage(0x123);

            }
        }).start();
        // 设置选择的三级单位
//         pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");

        // 设置默认选中的三级项目
        // 监听确定选择按钮

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                // 返回的分别是三个级别的选中位置
                String tx = item1.get(options1).getPickerViewText() + item2.get(options1).get(option2).getPickerViewText();
                tvAddress.setText(tx);
                merchantBean.setProvince(item1.get(options1).getPickerViewId());
                merchantBean.setCity(item2.get(options1).get(option2).getPickerViewCityCode());

            }
        });
        // 点击弹出选项选择器
        tvAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });

        tvAddress.setClickable(false);
    }

    @Override
    public void initData() {



        HttpMethods.getInstance().getInfo(MineInfoActivity.this, new ProgressSubscriber<BaseInfo>(infoSubscriber, MineInfoActivity.this));


    }

    @Override
    public void addActivity() {

    }

    @Override
    public void onClick(View v) {

    }



    @OnClick({R.id.wallet_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_back:
                finish();
                break;
            case R.id.tv_save:
                if(checkd()){
                    HttpMethods.getInstance().postInfo(MineInfoActivity.this,new ProgressSubscriber<BaseInfo>(backSubscriber,MineInfoActivity.this),companyname,phone,email,address,introduce);
                }
                break;
        }
    }

    private boolean checkd() {
        if(tvCompanyname.getText() == null || tvCompanyname.getText().equals("")){
            showShortToast("请填写团队/企业名称");
            return false;
        }else{
            companyname = tvCompanyname.getText().toString().trim();
        }
        if(tvContacts.getText() == null || tvContacts.getText().equals("")){
            showShortToast("请填写联系电话");
            return false;
        }else{
            phone = tvContacts.getText().toString().trim();
        }
        if(tvEmail.getText() == null || tvEmail.getText().equals("")){
            showShortToast("请填写联系邮箱");
            return false;
        }else{
            email = tvEmail.getText().toString().trim();
        }
        if( !tvEmail.getText().toString().trim().matches("\\w+@\\w+[.]((com))")){
            TastyToast.makeText(this, "邮箱格式错误", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }
        if(tvAddress.getText() == null || tvAddress.getText().equals("")){
            showShortToast("请填写联系地址");
            return false;
        }else{
            address = tvAddress.getText().toString().trim();
        }
        if(tvAddress.getText() == null || tvAddress.getText().equals("")){
            showShortToast("请填写团队/企业简介");
            return false;
        }else{
            introduce = tvAddress.getText().toString().trim();
        }

        return true;
    }
    //如果城市选择器弹出式 按返回键则退出选择器 否则退出界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if (pvOptions.isShowing()){
                pvOptions.dismiss();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
