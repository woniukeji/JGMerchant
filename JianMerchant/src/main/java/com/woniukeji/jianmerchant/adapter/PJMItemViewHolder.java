package com.woniukeji.jianmerchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.JobInfo;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.partjob.FilterActivity;
import com.woniukeji.jianmerchant.partjob.JobItemDetailActivity;
import com.woniukeji.jianmerchant.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Se7enGM on 2016/9/5.
 */
public class PJMItemViewHolder extends TopViewHolder<JobInfo> {
    @BindView(R.id.tv_merchant_name)
    TextView tvTitle;
    @BindView(R.id.img_type)
    ImageView imgType;
    @BindView(R.id.tv_manager_name)
    TextView tvManagerName;
    @BindView(R.id.tv_wages)
    TextView tvWages;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_publish_time)
    LinearLayout llPublishTime;
    @BindView(R.id.tv_chakan_browse)
    TextView tvChakanBrowse;
    @BindView(R.id.ll_browse)
    LinearLayout llBrowse;
    @BindView(R.id.tv_human)
    TextView tvHuman;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.ll_publisher)
    LinearLayout llPublisher;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.btn_admit_action)
    Button btnAdmitAction;
    @BindView(R.id.rl_job)
    RelativeLayout rlJob;
    @BindView(R.id.progress_admit)
    ProgressBar progressAdmit;
    private JobInfo job;
    private int maxProgress;

    public PJMItemViewHolder(Context context, ViewGroup root) {
        super(context, R.layout.admit_jobitem, root);
        setOnClick(itemView);
    }

    @Override
    public void bindData(JobInfo listTJobEntity) {
        job = listTJobEntity;
        tvTitle.setText(job.getJob_name());
        String date = DateUtils.getTime(Long.valueOf(job.getStart_date()), Long.valueOf(job.getEnd_date()));
        tvDate.setText(date);
        maxProgress = job.getCount();
        tvCount.setText(job.getCount() + "/" + job.getSum());
        if (job.getTerm() == 0) {//0=月结，1=周结，2=日结，3=小时结，4=次，5=义工
            tvWages.setText(job.getMoney() + "/月");
        } else if (job.getTerm() == 1) {
            tvWages.setText(job.getMoney() + "/周");
        } else if (job.getTerm() == 2) {
            tvWages.setText(job.getMoney() + "/日");
        } else if (job.getTerm() == 3) {
            tvWages.setText(job.getMoney() + "/小时");
        } else if (job.getTerm() == 4) {
            tvWages.setText(job.getMoney() + "/次");
        } else if (job.getTerm() == 5) {
            tvWages.setText("义工");
        } else if (job.getTerm() == 6) {
            tvWages.setText("面议");
        }
        if (job.getStatus() == 1) {
            btnAdmitAction.setText("招聘中");
        } else if (job.getStatus() == 2) {
            btnAdmitAction.setText("暂停中");
        } else if (job.getStatus() == 3) {
            btnAdmitAction.setText("已招满");
        } else if (job.getStatus() == 4) {
            btnAdmitAction.setText("已下架");
        }

        progressAdmit.setMax(job.getSum());
        if (job.getCount() > job.getSum()) {
            maxProgress = job.getSum();
        }
        progressAdmit.setProgress(maxProgress);
    }

    @OnClick({R.id.btn_admit_action,R.id.progress_admit,R.id.ll_publisher})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.progress_admit:
            case R.id.ll_publisher:
                Intent Intent = new Intent(getContext(), FilterActivity.class);
                Intent.putExtra("jobid",job.getId()+"");
                Intent.putExtra("jobname", job.getJob_name());
                getContext().startActivity(Intent);
                break;
        }
    }

    /**
     * 设置整个item的点击事件
     */
    private void setOnClick(View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), JobItemDetailActivity.class);
                intent.putExtra("job", job);
                getContext().startActivity(intent);
            }
        });
    }

    @OnClick(R.id.progress_admit)
    public void onClick() {
    }
}
