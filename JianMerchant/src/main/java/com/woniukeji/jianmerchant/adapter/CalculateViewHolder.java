package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.eventbus.UserWagesEvent;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Se7enGM on 2016/9/6.
 */
public class CalculateViewHolder extends TopViewHolder<AffordUser.ListBean> {
    private String name;
    @BindView(R.id.cb_user)
    CheckBox cbUser;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_user_name)
    TextView userName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_publish_time)
    LinearLayout llPublishTime;
    @BindView(R.id.tv_wages)
    TextView tvWages;
    @BindView(R.id.ll_wages)
    LinearLayout llWages;
    @BindView(R.id.btn_change_item)
    Button button;

    private ChangeMoney changeMoney;
    private int position;
    private AffordUser.ListBean user;
    private List<Boolean> isSelected;
    private String money;

    public void setChangeMoney(ChangeMoney changeMoney) {
        this.changeMoney = changeMoney;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public interface ChangeMoney {
        void onChangeMoney(String money, AffordUser.ListBean user, int position);
    }


    public CalculateViewHolder(Context context, ViewGroup root,List<Boolean> list, String money) {
        super(context, R.layout.activity_pay_wages_item, root);
        this.isSelected = list;
        name = (String) SPUtils.getParam(getContext(), Constants.USER_INFO, Constants.USER_NAME, "");
        this.money = money;
    }

    @Override
    public void bindData(AffordUser.ListBean listTUserInfoEntity) {
        user = listTUserInfoEntity;
        if (user.getName() != null) {
            userName.setText(user.getName());
        } else {
            userName.setText(user.getName() + "（未实名）");
        }

        tvPhone.setText(user.getTel());
        tvWages.setText(user.getMoney() + "元");
        Picasso.with(getContext()).load(user.getHead_img_url()).transform(new CropCircleTransfermation()).error(R.drawable.default_head).into(imgHead);
        cbUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isSelected.set(position, true);
                } else {
                    isSelected.set(position, false);
                }
                //每一次点击checkbox post到calculateactivity中的onEvent,进行遍历计算求和
                EventBus.getDefault().post(new UserWagesEvent());
            }
        });

        if (isSelected.get(position)) {
            cbUser.setChecked(true);
        } else {
            cbUser.setChecked(false);
        }
    }


    @OnClick({R.id.cb_user, R.id.btn_change_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_user:
                break;
            case R.id.btn_change_item:
                changeMoney.onChangeMoney(money, user, position);
                break;
        }
    }
}
