package com.woniukeji.jianmerchant.payWage;

/**
 * Created by Administrator on 2016/11/2.
 */
    import android.app.Dialog;
    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.woniukeji.jianmerchant.R;
    import com.woniukeji.jianmerchant.entity.BaseBean;
    import com.woniukeji.jianmerchant.eventbus.PayPassWordEvent;
    import com.woniukeji.jianmerchant.http.HttpMethods;
    import com.woniukeji.jianmerchant.http.ProgressSubscriberOnError;
    import com.woniukeji.jianmerchant.http.SubscriberOnNextErrorListener;

    import de.greenrobot.event.EventBus;

/**
     * Created by invinjun on 2016/4/19.
     */
    public class PayDialog extends Dialog {

        private Context context = null;

        private EditText passEText;
        private TextView titleText;
        private Button okBtn;
        private Button cancelBtn;
    private String tel;
        private SubscriberOnNextErrorListener<BaseBean> baseBeanSubscriberOnNextErrorListener;

        public PayDialog(Context context,String tel) {
            this(context, R.style.myDialogTheme);
            this.context = context;
            this.tel=tel;
        }

        public PayDialog(Context context, boolean cancelable,
                       OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
            this.context = context;
        }

        public PayDialog(Context context, int theme) {
            super(context, theme);
            this.context = context;
            setDialogContentView();
        }

        /**
         * 设置dialog里面的view
         */
        private void setDialogContentView()
        {
            // 加载自己定义的布局
            View view = LayoutInflater.from(context).inflate(R.layout.save_dialog, null);
            titleText = (TextView) view.findViewById(R.id.title_text);
            passEText = (EditText) view.findViewById(R.id.et_pass);
            okBtn = (Button) view.findViewById(R.id.ok_btn);
            cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PayDialog.this.dismiss();
                }
            });
            baseBeanSubscriberOnNextErrorListener=new SubscriberOnNextErrorListener<BaseBean>() {
                @Override
                public void onNext(BaseBean baseBean) {
                    super.onNext(baseBean);
                    PayPassWordEvent payPassWordEvent=new PayPassWordEvent();
                    payPassWordEvent.isCorrect=true;
                    EventBus.getDefault().post(payPassWordEvent);
                    PayDialog.this.dismiss();
                }

                @Override
                public void onError(String mes) {
                    super.onError(mes);
                    //否则
                    Toast.makeText(context,"密码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
                }
            };
            okBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    HttpMethods.getInstance().payPassword(context,new ProgressSubscriberOnError<BaseBean>(baseBeanSubscriberOnNextErrorListener,context),tel,passEText.getText().toString());

                }
            });
            setContentView(view);

        }

        /**
         * 设置ok按钮监听
         */
        public void setOKOnClickListener(View.OnClickListener listener){
            okBtn.setOnClickListener(listener);
        }

        /**
         * 设置显示的文字内容（传入string）
         * @param msg
         */
        public PayDialog setMsg(String msg) {

//            if (null != msgText) {
//                msgText.setText(msg);
//            }
            return this;
        }

        /**
         * 设置显示的文字内容（传入resId）
         */
        public PayDialog setMsg(int resId) {

//            if (null != msgText) {
//                msgText.setText(context.getString(resId));
//            }
            return this;
        }

        /**
         * 设置显示的标题（传入string）
         */
        public PayDialog setAlertTitle(String t) {

            if (null != titleText) {
                titleText.setText(t);
            }
            return this;
        }

        /**
         * 设置显示的标题（传入resId）
         */
        public PayDialog setAlertTitle(int resId) {
            if (null != titleText) {
                titleText.setText(context.getString(resId));
            }
            return this;
        }

}
