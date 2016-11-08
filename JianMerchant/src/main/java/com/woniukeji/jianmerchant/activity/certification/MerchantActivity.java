package com.woniukeji.jianmerchant.activity.certification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.sdsmdg.tastytoast.TastyToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.BitmapUtils;
import com.woniukeji.jianmerchant.utils.FileUtils;
import com.woniukeji.jianmerchant.utils.MD5Coder;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.woniukeji.jianmerchant.widget.city.CityBean;
import com.woniukeji.jianmerchant.widget.city.RegionInfo;
import com.woniukeji.jianmerchant.widget.city.dao.RegionDAO;
import com.woniukeji.jianmerchant.widget.city.view.OptionsPickerView;
import com.woniukeji.jianmerchant.widget.city.view.TimePickerView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MerchantActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.add_head) CircleImageView addHead;
    @BindView(R.id.et_company_name) EditText etCompanyName;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.tv_city) TextView tvCity;
    @BindView(R.id.et_address) EditText etAddress;
    @BindView(R.id.gd) EditText gd;
    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.activity_personal) LinearLayout activityPersonal;
    private TextView tvTime, tvOptions;
    TimePickerView pvTime;
    OptionsPickerView pvOptions;
    private MerchantBean merchantBean = new MerchantBean();

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
    };
    private int merchantId;
    private String filePath = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_merchant);
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
        merchantId = (int) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, 0);
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);
    }


    @OnClick({R.id.add_head,R.id.img_back, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_head:
                MultiImageSelectorActivity.startSelect(MerchantActivity.this, 0, 1, 0);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                if (checkInfo()) {
                    String key = MD5Coder.getQiNiuName(String.valueOf(merchantId));
                    String url1 = "http://7xlell.com2.z0.glb.qiniucdn.com/" + key;
                    merchantBean.setUserImage(url1);
                    upLoadQiNiu(this, key, filePath);
                    Intent intent = new Intent(MerchantActivity.this, MerchantDetailActivity.class);
                    intent.putExtra("merchant", merchantBean);
                    startActivity(intent);
                }
                break;
        }
    }


    private boolean checkInfo() {
        String groupName = etCompanyName.getText().toString();
        String emailStr = email.getText().toString();
        String address = etAddress.getText().toString();
        String jdStr = gd.getText().toString();
         if (null == groupName || groupName.equals("")) {
            TastyToast.makeText(this, "请填写企业名称", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        } else if (null == emailStr || emailStr.equals("")) {
            TastyToast.makeText(this, "请填写邮箱", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        } else if( !emailStr.matches("\\w+@\\w+[.]((com))")){
             TastyToast.makeText(this, "邮箱格式错误", TastyToast.LENGTH_LONG, TastyToast.WARNING);
             return false;
         }
        else if (null == merchantBean.getProvince() || merchantBean.getProvince().equals("")) {
            TastyToast.makeText(this, "请填写所在省市", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        } else if (null == address || address.equals("")) {
            TastyToast.makeText(this, "请填写详细地址", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        } else if (filePath.equals("")) {
            TastyToast.makeText(this, "请上传企业logo", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }
        merchantBean.setCompanyName(groupName);
        merchantBean.setEmail(emailStr);
        merchantBean.setCompanyAddress(address);
        merchantBean.setAbout(jdStr == null ? "" : jdStr);
        return true;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                File file = new File(path.get(0));
                Uri imgSource = Uri.fromFile(file);
                startCropImageActivity(imgSource, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE1);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE1) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = FileUtils.getRealFilePath(this, result.getUri());
                Bitmap bitmap = BitmapUtils.compressBitmap(filePath, 1080, 720);
                addHead.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri, int requestCode) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this, requestCode, false);
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
