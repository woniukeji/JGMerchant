package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.PublishUser;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class NewFilterAdapter extends SwipeMenuAdapter<NewFilterAdapter.ViewHolder> {
    private final List<PublishUser.ListTUserInfoEntity> mValues;
    private final Context context;
    private final int type;
    private final String jobid;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private onChatClickListener onChatClickListener;

    private FilterItemClickListener filterItemClickListener;
    public void setFilterItemClickListener(FilterItemClickListener filterItemClickListener) {
        this.filterItemClickListener = filterItemClickListener;
    }

    private EnrollOrRefuseClickListener enrollOrRefuseClickListener;
    public void setEnrollOrRefuseClickListener(EnrollOrRefuseClickListener enrollOrRefuseClickListener) {
        this.enrollOrRefuseClickListener = enrollOrRefuseClickListener;
    }

    public NewFilterAdapter(List<PublishUser.ListTUserInfoEntity> values, Context context,int type,String jobid) {
        this.mValues = values;
        this.context = context;
        this.type = type;
        this.jobid = jobid;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case NORMAL:
                view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_filter_item_new, parent, false);
                break;
            case IS_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_footer, parent, false);
                break;
        }

        return view;
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        ViewHolder viewHolder = new ViewHolder(realContentView,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mValues.size() ==0) {
            //不让footer显示
            holder.itemView.setVisibility(View.GONE);
        }
        if (mValues.size() == position) {
            if (mValues.size() > 4) {
                holder.loading.setText("已加载全部");
                holder.itemView.setVisibility(View.VISIBLE);
            } else {
                holder.itemView.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.setData(mValues.get(position),position);
            if (filterItemClickListener != null) {
                holder.setFilterItemClickListener(filterItemClickListener);
            }
            if (enrollOrRefuseClickListener != null) {
                holder.setEnrollOrRefuseClickListener(enrollOrRefuseClickListener);
            }
            if (onChatClickListener!=null) {
                holder.setOnChatClickListener(onChatClickListener);
            }

        }

    }

    @Override
    public int getItemCount() {
        return mValues.size() > 0 ? mValues.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mValues.size()) {
            return IS_FOOTER;
        } else {
            return NORMAL;
        }
    }

    public void setOnChatClickListener(com.woniukeji.jianmerchant.partjob.onChatClickListener onChatClickListener) {
        this.onChatClickListener = onChatClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleimgHead;
        private TextView tvUserName;
        private TextView imgSex;
        private TextView tvSchoolName;
        private Button btnCancel;
        private Button btnConfirm;
        private TextView tvPublishDate;
        private ImageView userPhone;
        private ImageView userGezi1;
        private ImageView userGezi2;
        private ImageView userGezi3;
        private ImageView userGezi4;
        private ImageView userGezi5;
        private TextView loading;

        private FilterItemClickListener filterItemClickListener;
        private ImageView userChat;
        private onChatClickListener onChatClickListener;
        private PublishUser.ListTUserInfoEntity userInfo;


        public void setFilterItemClickListener(FilterItemClickListener filterItemClickListener) {
            this.filterItemClickListener = filterItemClickListener;
        }

        private EnrollOrRefuseClickListener enrollOrRefuseClickListener;
        public void setEnrollOrRefuseClickListener(EnrollOrRefuseClickListener enrollOrRefuseClickListener) {
            this.enrollOrRefuseClickListener = enrollOrRefuseClickListener;
        }

        public ViewHolder(View itemView, int type) {
            super(itemView);
            if (itemView != null) {
                switch (type) {
                    case NORMAL:
                        initView(itemView);
//                        点击整个item事件，暂时还不需要
//                        itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                filterItemClickListener.onItemClick(getAdapterPosition());
//                            }
//                        });
                        itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                return filterItemClickListener.onItemLongClick(getAdapterPosition(),v,userInfo.getLogin_id());
                            }
                        });

                        break;
                    case IS_FOOTER:
                        loading = (TextView)itemView.findViewById(R.id.tv_loading);
                        break;
                }
            }
        }

        private void initView(View itemView) {
            circleimgHead = (CircleImageView) itemView.findViewById(R.id.circleimg_head);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            imgSex = (TextView) itemView.findViewById(R.id.img_sex);
            tvSchoolName = (TextView) itemView.findViewById(R.id.tv_school_name);
            tvPublishDate = (TextView) itemView.findViewById(R.id.tv_publish_date);
            btnCancel = (Button) itemView.findViewById(R.id.btn_cancel);
            btnConfirm = (Button) itemView.findViewById(R.id.btn_confirm);
            userPhone = (ImageView) itemView.findViewById(R.id.user_phone);
            userGezi1 = (ImageView) itemView.findViewById(R.id.user_gezi1);
            userGezi2 = (ImageView) itemView.findViewById(R.id.user_gezi2);
            userGezi3 = (ImageView) itemView.findViewById(R.id.user_gezi3);
            userGezi4 = (ImageView) itemView.findViewById(R.id.user_gezi4);
            userGezi5 = (ImageView) itemView.findViewById(R.id.user_gezi5);
            userChat = (ImageView)itemView.findViewById(R.id.user_talk);
        }

        public void setData(final PublishUser.ListTUserInfoEntity userInfo, int position) {
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
            }else if (userInfo.getUser_status().equals("4")) {
                btnCancel.setText("用户取消");
                btnCancel.setClickable(false);
                btnConfirm.setVisibility(View.GONE);
                btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if (userInfo.getUser_status().equals("5")) {
                btnCancel.setText("取消录取");
                btnConfirm.setVisibility(View.GONE);
                btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if (userInfo.getUser_status().equals("6")) {
                btnCancel.setText("用户取消");
                btnCancel.setClickable(false);
                btnConfirm.setVisibility(View.GONE);
                btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if (userInfo.getUser_status().equals("7")) {
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
            Picasso.with(context).load(userInfo.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(circleimgHead);

            //------------------------------------------------------
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type=0;
                    if (userInfo.getUser_status().equals("0")){
                        type=3;
                    }else {
                        return;
                    }
                    enrollOrRefuseClickListener.onClick(getAdapterPosition(),userInfo.getLogin_id(),type,v);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = 0;
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
                    enrollOrRefuseClickListener.onClick(getAdapterPosition(),userInfo.getLogin_id(),type,v);
                }
            });
            userPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+userInfo.getTel()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            userChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChatClickListener.onChat(getAdapterPosition(),userInfo.getLogin_id(),v);
                }
            });
        }

        public void setOnChatClickListener(com.woniukeji.jianmerchant.partjob.onChatClickListener onChatClickListener) {
            this.onChatClickListener = onChatClickListener;
        }

        private void showPigeonCount(int count) {
            if (count == 1) {
                userGezi1.setVisibility(View.VISIBLE);
                userGezi2.setVisibility(View.INVISIBLE);
                userGezi3.setVisibility(View.INVISIBLE);
                userGezi4.setVisibility(View.INVISIBLE);
                userGezi5.setVisibility(View.INVISIBLE);
            } else if (count==2) {
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
            } else if (count ==0) {
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
    }

}
