package pri.ky2.ky2coderepos.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import pri.ky2.ky2coderepos.R;
import pri.ky2.ky2coderepos.base.BaseApplication;

/**
 * 通用 Dialog
 *
 * @author wangkaiyan
 * @date 2019/07/23
 */
public class CommonDialog extends Dialog {

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvCancel, tvConfirm;
    private View cancelView;

    public CommonDialog(Context context, Builder builder) {
        super(context, R.style.CommonDialogStyle);
        setContentView(R.layout.dialog_common);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }
        initView(builder);
    }

    private void initView(final Builder builder) {
        tvTitle = findViewById(R.id.dialog_title);
        tvContent = findViewById(R.id.dialog_content);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_sure);
        cancelView = findViewById(R.id.cancel_group);

        tvTitle.setVisibility(builder.isShowTitle ? View.VISIBLE : View.GONE);
        tvTitle.setText(builder.titleStr);
        tvContent.setText(builder.contentStr);
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, builder.contentSize);
        cancelView.setVisibility(builder.isShowNegativeBtn ? View.VISIBLE : View.GONE);
        tvConfirm.setText(builder.positiveStr);
        tvCancel.setText(builder.negativeStr);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.positiveListener != null) {
                    builder.positiveListener.onClick(v);
                }
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.negativeListener != null) {
                    builder.negativeListener.onClick(v);
                }
                dismiss();
            }
        });
        if (builder.dismissListener != null) {
            this.setOnDismissListener(builder.dismissListener);
        }
        this.setCancelable(builder.cancelableFlag);
        this.setCanceledOnTouchOutside(builder.cancelableFlag);
    }

    public static class Builder {

        private Context mContext;

        /**
         * 确定按钮，右边的
         */
        private View.OnClickListener positiveListener;

        /**
         * 取消按钮，左边的
         */
        private View.OnClickListener negativeListener;

        private OnDismissListener dismissListener;

        private boolean isShowTitle = false;
        private String titleStr;
        private String contentStr;
        private int contentSize;
        private String positiveStr = "确定";
        private String negativeStr = "取消";
        private boolean isShowNegativeBtn = false;
        private boolean cancelableFlag = true;

        public Builder(Context context) {
            mContext = context;
            contentSize = (int) (15 * mContext.getResources().getDisplayMetrics().density + 0.5f);
        }

        public Builder showTitle(int resSource) {
            return showTitle(getString(resSource));
        }

        public Builder showTitle(String title) {
            isShowTitle = true;
            titleStr = title;
            return this;
        }

        public Builder setContent(int resSource) {
            return setContent(getString(resSource));
        }

        public Builder setContent(String str) {
            contentStr = str;
            return this;
        }

        public Builder setContentSize(int contentSize) {
            contentSize = (int) (contentSize * mContext.getResources().getDisplayMetrics().density + 0.5f);
            return this;
        }

        public Builder setPositiveStr(int resSource) {
            return setPositiveStr(getString(resSource));
        }

        public Builder setPositiveStr(String str) {
            positiveStr = str;
            return this;
        }

        public Builder showNegativeBtn() {
            isShowNegativeBtn = true;
            return this;
        }

        public Builder setNegativeStr(int resSource) {
            return setNegativeStr(getString(resSource));
        }

        public Builder setNegativeStr(String str) {
            negativeStr = str;
            return this;
        }

        public Builder setPositiveListener(View.OnClickListener clickListener) {
            positiveListener = clickListener;
            return this;
        }

        public Builder setNegativeListener(View.OnClickListener clickListener) {
            negativeListener = clickListener;
            return this;
        }

        public Builder setmDismissListener(OnDismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }

        public Builder setCancelable(boolean cancelableFlag) {
            this.cancelableFlag = cancelableFlag;
            return this;
        }

        private String getString(int resSource) {
            return BaseApplication.getAppContext().getString(resSource);
        }

        public CommonDialog build() {
            return new CommonDialog(mContext, this);
        }
    }
}
