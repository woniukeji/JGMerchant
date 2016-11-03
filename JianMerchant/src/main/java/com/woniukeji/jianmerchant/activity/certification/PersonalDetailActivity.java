package com.woniukeji.jianmerchant.activity.certification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.MerchantBean;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.ProgressSubscriber;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.BitmapUtils;
import com.woniukeji.jianmerchant.utils.EditCheckUtil;
import com.woniukeji.jianmerchant.utils.FileUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.MD5Coder;
import com.woniukeji.jianmerchant.utils.SPUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class PersonalDetailActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_add_zheng) ImageView imgAddZheng;
    @BindView(R.id.tv_add_zheng) TextView tvAddZheng;
    @BindView(R.id.imageView2) ImageView imgExsample1;
    @BindView(R.id.img_add_hand) ImageView imgAddHand;
    @BindView(R.id.tv_add_hand) TextView tvAddHand;
    @BindView(R.id.imageView3) ImageView imgExsample2;
    @BindView(R.id.et_contact_name) EditText etContactName;
    @BindView(R.id.et_contact_phone) EditText etContactPhone;
    @BindView(R.id.et_real_name) EditText etRealName;
    @BindView(R.id.et_id_num) EditText etIdNum;
    @BindView(R.id.btn_next) Button btnReview;
    @BindView(R.id.activity_personal_detail) LinearLayout activityPersonalDetail;
    private String filePath;
    private String filepathHand;
    private String url1;
    private String url2;
    private int merchantId;
    private MerchantBean merchantBean;
    private SubscriberOnNextListener<String> baseBeanSubscriberOnNextListener;
    private String token;
    private int loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        baseBeanSubscriberOnNextListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                TastyToast.makeText(PersonalDetailActivity.this,"审核信息提交成功",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                Intent intent1 = new Intent(PersonalDetailActivity.this, StatusActivity.class);
                intent1.putExtra("type",1);
                startActivity(intent1);
                finish();
            }
        };
    }

    @Override
    public void initData() {
       merchantId = (int) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_MERCHANT_ID, 0);
        loginId = (int) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        token = (String) SPUtils.getParam(this, Constants.LOGIN_INFO, Constants.SP_WQTOKEN, "");
        merchantBean = (MerchantBean) getIntent().getSerializableExtra("merchant");
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);
    }


    @OnClick({R.id.img_back, R.id.img_add_zheng, R.id.imageView2, R.id.img_add_hand,R.id.btn_next, R.id.imageView3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_add_zheng:
                MultiImageSelectorActivity.startSelect(PersonalDetailActivity.this, 0, 1, 0);
                break;
            case R.id.imageView2:
                Intent intent=new Intent(PersonalDetailActivity.this,TipExampleActivity.class);
                intent.putExtra("type",0);//身份证正面
                startActivity(intent);
                break;
            case R.id.img_add_hand:
                MultiImageSelectorActivity.startSelect(PersonalDetailActivity.this, 1, 1, 0);
                break;
            case R.id.imageView3:
                Intent intent2=new Intent(PersonalDetailActivity.this,TipExampleActivity.class);
                intent2.putExtra("type",1);//身份证正面
                startActivity(intent2);
                break;
            case R.id.btn_next:
                String key=MD5Coder.getQiNiuName("0"+String.valueOf(merchantId));
                url1="http://7xlell.com2.z0.glb.qiniucdn.com/"+key;
                String key2=MD5Coder.getQiNiuName("1"+String.valueOf(merchantId));
                url2="http://7xlell.com2.z0.glb.qiniucdn.com/"+key2;
                if (checkInfo()){
                    Gson gson=new Gson();
                    String merJson = gson.toJson(merchantBean);
                    upLoadQiNiu(this,key , filePath,1);
                    upLoadQiNiu(this,key2 , filepathHand,2);
                    HttpMethods.getInstance().cerfication(new ProgressSubscriber<String>(baseBeanSubscriberOnNextListener,this), String.valueOf(loginId),String.valueOf(merchantId),token,merJson);
                }
                break;
        }
    }
    private boolean checkInfo() {
        String contactName=etContactName.getText().toString();
        String contactPhone=etContactPhone.getText().toString();
        String realName=etRealName.getText().toString();
        String IDNum=etIdNum.getText().toString();
        if (null==filePath||filePath.equals("")) {
            TastyToast.makeText(this, "请上传身份证照片", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }else if (null==filepathHand||filepathHand.equals("")){
            TastyToast.makeText(this, "请上传手持身份证照片", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }else if (null==contactName||contactName.equals("")){
            TastyToast.makeText(this, "请填写联系人姓名", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }
        else if (null==contactPhone||contactPhone.equals("")){
            TastyToast.makeText(this, "请填写联系人电话", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        } else if (contactPhone.length()!=11){
            TastyToast.makeText(this, "电话格式不正确", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }
        else if (null==realName||realName.equals("")){
            TastyToast.makeText(this, "请填写真实姓名", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }else if (null== IDNum||IDNum.equals("")){
            TastyToast.makeText(this, "请填写身份证号码", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }else if(!EditCheckUtil.IDCardValidate(IDNum)){
            TastyToast.makeText(this, "身份证号码不正确", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            return false;
        }
        merchantBean.setCardImg(url1);
        merchantBean.setHandImg(url2);
        merchantBean.setContactName(contactName);
        merchantBean.setContactPhone(contactPhone);
        merchantBean.setRealName(realName);
        merchantBean.setCardNum(IDNum);
        merchantBean.setPermissions(2); //1是外部商家，2是个人商户，0是内部
        return true;
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
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                File file = new File(path.get(0));
                Uri imgSource = Uri.fromFile(file);
                startCropImageActivity(imgSource,CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE2);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE1) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = FileUtils.getRealFilePath(this, result.getUri());
                Bitmap bitmap= BitmapUtils.compressBitmap(filePath,1080, 720);
                imgAddZheng.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE2) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filepathHand = FileUtils.getRealFilePath(this, result.getUri());
                Bitmap bitmap=BitmapUtils.compressBitmap(filepathHand,1080, 720);
                imgAddHand.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri,int requestCode) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this,requestCode,false);
    }

    public  void upLoadQiNiu(final Context context, final String key, final String filePath, final int position) {
        String commonUploadToken = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, "");
              // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(filePath, key, commonUploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.error!=null) {
                    LogUtils.e("key","错误："+info.error);
                }else {
                    LogUtils.e("key","错误："+info.isOK());
                }
            }
        }, new UploadOptions(null, null, false,
                new UpProgressHandler(){
                    public void progress(String key, double percent){
                        LogUtils.e("key","进度"+percent);
                    }
                }, null));

    }
}
