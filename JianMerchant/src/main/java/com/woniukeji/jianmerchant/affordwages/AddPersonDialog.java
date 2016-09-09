package com.woniukeji.jianmerchant.affordwages;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.entity.AddPerson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddPersonDialog extends BaseActivity {

    @BindView(R.id.add_name)
    EditText addName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.add_tel)
    EditText addTel;
    @BindView(R.id.rl_bg)
    RelativeLayout rlBg;
    @BindView(R.id.exit)
    ImageView exit;
    @BindView(R.id.confirm_add)
    ImageView confirmAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_dialog);
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

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }

    @OnClick({R.id.confirm_add, R.id.exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_add:
                String name = addName.getText().toString().trim();
                String tel = addTel.getText().toString().trim();
                AddPerson addPerson = new AddPerson(name,tel);
                EventBus.getDefault().post(addPerson);
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}
