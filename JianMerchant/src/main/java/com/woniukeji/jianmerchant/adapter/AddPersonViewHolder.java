package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.AddPerson;

import butterknife.BindView;

/**
 * Created by Se7enGM on 2016/9/13.
 */
public class AddPersonViewHolder extends TopViewHolder<AddPerson> {
    @BindView(R.id.add_name)
    TextView addName;
    @BindView(R.id.add_tel)
    TextView addTel;

    public AddPersonViewHolder(Context context, ViewGroup root) {
        super(context, R.layout.item_add_person, root);
    }

    @Override
    public void bindData(AddPerson addPerson) {
        if (addPerson != null) {
            addName.setText(addPerson.getName());
            addTel.setText(addPerson.getTel());
        }
    }
}
