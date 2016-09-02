package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.PublishUser;
import com.woniukeji.jianmerchant.partjob.EnrollOrRefuseClickListener;
import com.woniukeji.jianmerchant.partjob.FilterItemClickListener;
import com.woniukeji.jianmerchant.partjob.onChatClickListener;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Se7enGM on 2016/9/2.
 */
public class FilterItemViewHolder extends TopViewHolder<PublishUser.ListTUserInfoEntity> {


    @BindView(R.id.circleimg_head)
    CircleImageView circleimgHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.img_sex)
    TextView imgSex;
    @BindView(R.id.tv_school_name)
    TextView tvSchoolName;
    @BindView(R.id.user_gezi1)
    ImageView userGezi1;
    @BindView(R.id.user_gezi2)
    ImageView userGezi2;
    @BindView(R.id.user_gezi3)
    ImageView userGezi3;
    @BindView(R.id.user_gezi4)
    ImageView userGezi4;
    @BindView(R.id.user_gezi5)
    ImageView userGezi5;
    @BindView(R.id.tv_publish_date)
    TextView tvPublishDate;
    @BindView(R.id.user_phone)
    ImageView userPhone;
    @BindView(R.id.user_talk)
    ImageView userTalk;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    private PublishUser.ListTUserInfoEntity userInfo;
    private EnrollOrRefuseClickListener enrollOrRefuseClickListener;
    public void setEnrollOrRefuseClickListener(EnrollOrRefuseClickListener enrollOrRefuseClickListener) {
        this.enrollOrRefuseClickListener = enrollOrRefuseClickListener;
    }

    private FilterItemClickListener filterItemClickListener;
    public void setFilterItemClickListener(FilterItemClickListener filterItemClickListener) {
        this.filterItemClickListener = filterItemClickListener;
    }

    public FilterItemViewHolder(Context context, ViewGroup root) {
        super(context, R.layout.fragment_filter_item_new, root);
    }

    private onChatClickListener onChatClickListener;
    public void setOnChatClickListener(com.woniukeji.jianmerchant.partjob.onChatClickListener onChatClickListener) {
        this.onChatClickListener = onChatClickListener;
    }

    @Override
    public void bindData(PublishUser.ListTUserInfoEntity userInfo) {
        this.userInfo = userInfo;
        //设置报名者姓名
        if (userInfo.getName() != null && !userInfo.getName().equals("0")) {
            tvUserName.setText(userInfo.getName());
        } else {
            tvUserName.setText("未填写");
        }
        //性别
        if (userInfo.getSex_resume() == 1) {
            imgSex.setText("男");
            imgSex.setTextColor(Color.parseColor("#82dcd8"));
        } else {
            imgSex.setText("女");
            imgSex.setTextColor(Color.parseColor("#ef8db5"));
        }
        //学校
        if (tvSchoolName.equals("")) {
            tvSchoolName.setText("未设置学校");
        } else {
            tvSchoolName.setText(userInfo.getSchool());
        }
        //时间
        tvPublishDate.setText(userInfo.getTime_job());
        //根据用户的状态这是现实的button
        if (userInfo.getUser_status().equals("0")) {
            btnCancel.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
        } else if (userInfo.getUser_status().equals("2")) {
            btnCancel.setText("已取消");
            btnCancel.setClickable(false);
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        } else if (userInfo.getUser_status().equals("3")) {
            btnCancel.setText("取消录取");
            btnConfirm.setText("待确认");
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        } else if (userInfo.getUser_status().equals("4")) {
            btnCancel.setText("用户取消");
            btnCancel.setClickable(false);
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        } else if (userInfo.getUser_status().equals("5")) {
            btnCancel.setText("取消录取");
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        } else if (userInfo.getUser_status().equals("6")) {
            btnCancel.setText("用户取消");
            btnCancel.setClickable(false);
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        } else if (userInfo.getUser_status().equals("7")) {
            btnCancel.setText("已取消");
            btnCancel.setClickable(false);
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        } else if (userInfo.getUser_status().equals("8")) {
            btnCancel.setText("工作中");
            btnCancel.setClickable(false);
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        } else {
            btnCancel.setText("工作结束");
            btnCancel.setClickable(false);
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
        }
        //显示鸽子数量
        showPigeonCount(userInfo.getPigeon_count());
        //头像
        Picasso.with(getContext()).load(userInfo.getName_image())
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(circleimgHead);
    }

    private void showPigeonCount(int count) {
        if (count == 1) {
            userGezi1.setVisibility(View.VISIBLE);
            userGezi2.setVisibility(View.INVISIBLE);
            userGezi3.setVisibility(View.INVISIBLE);
            userGezi4.setVisibility(View.INVISIBLE);
            userGezi5.setVisibility(View.INVISIBLE);
        } else if (count == 2) {
            userGezi1.setVisibility(View.VISIBLE);
            userGezi2.setVisibility(View.VISIBLE);
            userGezi3.setVisibility(View.INVISIBLE);
            userGezi4.setVisibility(View.INVISIBLE);
            userGezi5.setVisibility(View.INVISIBLE);
        } else if (count == 3) {
            userGezi1.setVisibility(View.VISIBLE);
            userGezi2.setVisibility(View.VISIBLE);
            userGezi3.setVisibility(View.VISIBLE);
            userGezi4.setVisibility(View.INVISIBLE);
            userGezi5.setVisibility(View.INVISIBLE);
        } else if (count == 4) {
            userGezi1.setVisibility(View.VISIBLE);
            userGezi2.setVisibility(View.VISIBLE);
            userGezi3.setVisibility(View.VISIBLE);
            userGezi4.setVisibility(View.VISIBLE);
            userGezi5.setVisibility(View.INVISIBLE);
        } else if (count == 5) {
            userGezi1.setVisibility(View.VISIBLE);
            userGezi2.setVisibility(View.VISIBLE);
            userGezi3.setVisibility(View.VISIBLE);
            userGezi4.setVisibility(View.VISIBLE);
            userGezi5.setVisibility(View.INVISIBLE);
        } else if (count == 0) {
            userGezi1.setVisibility(View.INVISIBLE);
            userGezi2.setVisibility(View.INVISIBLE);
            userGezi3.setVisibility(View.INVISIBLE);
            userGezi4.setVisibility(View.INVISIBLE);
            userGezi5.setVisibility(View.INVISIBLE);
        } else {
            userGezi1.setVisibility(View.VISIBLE);
            userGezi2.setVisibility(View.VISIBLE);
            userGezi3.setVisibility(View.VISIBLE);
            userGezi4.setVisibility(View.VISIBLE);
            userGezi5.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.user_phone, R.id.user_talk, R.id.btn_confirm, R.id.btn_cancel})
    public void onClick(View view) {
        int type=0;
        switch (view.getId()) {
            case R.id.user_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+userInfo.getTel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.user_talk:
                onChatClickListener.onChat(getAdapterPosition(),userInfo.getLogin_id(),view);
                break;
            case R.id.btn_confirm:

                if (userInfo.getUser_status().equals("0")){
                    type=3;
                }else {
                    return;
                }
                enrollOrRefuseClickListener.onClick(getAdapterPosition(),userInfo.getLogin_id(),type,view);
                break;
            case R.id.btn_cancel:
                if (userInfo.getUser_status().equals("0")){
                    type=2;
                }else if(userInfo.getUser_status().equals("3")){
                    type=2;
                }
                else if(userInfo.getUser_status().equals("5")){
                    type=7;
                }
                else if(userInfo.getUser_status().equals("9")){
                    type=12;
                }
                else if(userInfo.getUser_status().equals("10")){
                    type=12;
                }
                else {
                    return;
                }
                enrollOrRefuseClickListener.onClick(getAdapterPosition(),userInfo.getLogin_id(),type,view);
                break;
        }
    }
}
