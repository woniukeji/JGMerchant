package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.JobInfo;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.publish.ChangeJobActivity;
import com.woniukeji.jianmerchant.publish.PublishDetailActivity;
import com.woniukeji.jianmerchant.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Se7enGM on 2016/9/5.
 */
public class HistoryJobItemViewHolder extends TopViewHolder<JobInfo> {


    @BindView(R.id.tv_merchant_name)
    TextView tvTitle;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_publish_time)
    LinearLayout llPublishTime;
    @BindView(R.id.tv_human)
    TextView tvHuman;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_publisher)
    LinearLayout llPublisher;
    @BindView(R.id.tv_enroll_num)
    TextView tvEnrollNum;
    @BindView(R.id.btn_muban_use)
    Button btnMubanUse;
    @BindView(R.id.btn_muban_delete)
    Button btnMubanDelete;
    @BindView(R.id.img_his)
    ImageView imgHis;
    @BindView(R.id.rl_job)
    RelativeLayout rlJob;
    @BindView(R.id.ll_muban)
    LinearLayout llMuban;
    private JobInfo job;
    private String mType;
    private String name;
    private int position;

    public HistoryJobItemViewHolder(Context context, ViewGroup root, String type, String name) {
        super(context, R.layout.history_jobitem, root);
        this.mType = type;
        this.name = name;
        setOnClick(itemView);
    }



    @Override
    public void bindData(JobInfo listTJobEntity) {
        job = listTJobEntity;
        if (mType.equals("0")) {//历史纪录
            tvTitle.setText(job.getJob_name());
            llMuban.setVisibility(View.GONE);
            imgHis.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setText(job.getModel_name());
            llMuban.setVisibility(View.VISIBLE);
            imgHis.setVisibility(View.GONE);
        }
        tvName.setText(name);
//        if (null!=job.getRegedit_time()&& !job.getRegedit_time().equals("")){
//            String date = job.getRegedit_time().substring(5 ,11);
//            tvDate.setText(date);
//        }
    }


    private DeleteCallBack deleteCallBack;

    public void setDeleteCallBack(DeleteCallBack deleteCallBack) {
        this.deleteCallBack = deleteCallBack;
    }

    @OnClick({R.id.tv_enroll_num, R.id.btn_muban_use, R.id.btn_muban_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_enroll_num:
                break;
            case R.id.btn_muban_use:
                Intent intent=new Intent(getContext(), ChangeJobActivity.class);
                intent.putExtra("jobid",job.getId()+"");
                intent.setAction("fromItem");
                intent.putExtra("isHistory",2);
                intent.putExtra("type","old");
                getContext().startActivity(intent);
                break;
            case R.id.btn_muban_delete:
                if (deleteCallBack != null) {
//                    deleteCallBack.onDelete(job.getId(), job.getMerchant_id(), position);
                } else {
                    LogUtils.e("deleteCallback","没有setDeleteCallback");
                }
                break;
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public interface DeleteCallBack {
        void onDelete(int job_id, int merchant_id, int position);
    }

    /**
     * 设置整个item的点击事件
     */
    private void setOnClick(View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ChangeJobActivity.class);
                intent.putExtra("jobid",job.getId()+"");
                intent.setAction("fromItem");
                intent.putExtra("isHistory",1);
                intent.putExtra("type","old");
                getContext().startActivity(intent);
            }
        });
    }
}
