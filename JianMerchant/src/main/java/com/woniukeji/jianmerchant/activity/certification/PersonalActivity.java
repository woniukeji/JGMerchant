package com.woniukeji.jianmerchant.activity.certification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.widget.city.CityBean;
import com.woniukeji.jianmerchant.widget.city.RegionInfo;
import com.woniukeji.jianmerchant.widget.city.dao.RegionDAO;
import com.woniukeji.jianmerchant.widget.city.view.OptionsPickerView;
import com.woniukeji.jianmerchant.widget.city.view.TimePickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalActivity extends BaseActivity {
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_group_name) EditText etGroupName;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.tv_city) TextView tvCity;
    @BindView(R.id.et_address) EditText etAddress;
    @BindView(R.id.gd) EditText gd;
    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.activity_personal) LinearLayout activityPersonal;
    private TextView tvTime, tvOptions;
    TimePickerView pvTime;
    OptionsPickerView pvOptions;
    private MerchantBean merchantBean=new MerchantBean();

    static ArrayList<RegionInfo> item1;

    static ArrayList<ArrayList<CityBean>> item2 = new ArrayList<ArrayList<CityBean>>();

//	static ArrayList<ArrayList<ArrayList<RegionInfo>>> item3 = new ArrayList<ArrayList<ArrayList<RegionInfo>>>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            System.out.println(System.currentTimeMillis());
            // 三级联动效果

            pvOptions.setPicker(item1, item2, true);
            pvOptions.setCyclic(true, false, true);
            pvOptions.setSelectOptions(0, 0, 0);
            tvOptions.setClickable(true);
        }

        ;
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        tvOptions = (TextView) findViewById(R.id.tv_city);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        // 选项选择器
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
                tvOptions.setText(tx);
                merchantBean.setProvince(item1.get(options1).getPickerViewId());
                merchantBean.setCity(item2.get(options1).get(option2).getPickerViewCityCode());

            }
        });
        // 点击弹出选项选择器
        tvOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });

        tvOptions.setClickable(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }

    @Override
    public void onClick(View v) {

    }


    @OnClick(R.id.btn_next)
    public void onClick() {
        if (checkInfo()) {
            Intent intent=new Intent(PersonalActivity.this,PersonalDetailActivity.class);
            intent.putExtra("merchant",merchantBean);
            startActivity(intent);
        }

    }

    private boolean checkInfo() {
        String nickName=etName.getText().toString();
        String groupName=etGroupName.getText().toString();
        String emailStr=email.getText().toString();
        String address=etAddress.getText().toString();
        String jdStr=gd.getText().toString();
        if (null==nickName||nickName.equals("")) {
            TastyToast.makeText(this, "请填写昵称", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }else if (null==groupName||groupName.equals("")){
            TastyToast.makeText(this, "请填写团队名称", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }else if (null==emailStr||emailStr.equals("")){
            TastyToast.makeText(this, "请填写邮箱", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }
        else if (null==merchantBean.getProvince()||merchantBean.getProvince().equals("")){
            TastyToast.makeText(this, "请填写所在省市", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }else if (null==address||address.equals("")){
            TastyToast.makeText(this, "请填写详细地址", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }
        merchantBean.setName(nickName);
        merchantBean.setEmail(emailStr);
        merchantBean.setCompanyAddress(address);
        merchantBean.setAbout(jdStr==null?"":jdStr);
        return true;
    }
}
