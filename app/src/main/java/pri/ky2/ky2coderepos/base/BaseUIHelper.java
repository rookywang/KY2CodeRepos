package pri.ky2.ky2coderepos.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import pri.ky2.ky2coderepos.R;

/**
 * UI 辅助类，定义了加载框
 *
 * @author wangkaiyan
 * @date 2019/07/23
 */
public class BaseUIHelper {

    private Activity mActivity;
    private Dialog mLoadingDialog;

    public BaseUIHelper(Activity activity) {
        mActivity = activity;
    }

    public void showLoading(boolean canCancel, String msg) {
        if (isClosed() || getContext() == null) {
            return;
        }
        mLoadingDialog = new Dialog(getContext(), R.style.DialogTheme);
        View view = LayoutInflater.from(getContext())
                .inflate(TextUtils.isEmpty(msg) ? R.layout.dialog_progress_loading : R.layout.dialog_progress_loading_msg, null);
        TextView tvLoadingMsg = view.findViewById(R.id.tv_msg);
        if (tvLoadingMsg != null && !TextUtils.isEmpty(msg)) {
            tvLoadingMsg.setText(msg);
        }
        mLoadingDialog.setContentView(view);
        mLoadingDialog.setCancelable(canCancel);
        mLoadingDialog.setCanceledOnTouchOutside(canCancel);
        mLoadingDialog.show();
    }

    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        }
        return null;
    }

    private boolean isClosed() {
        if (mActivity == null || mActivity.isFinishing()) {
            return true;
        }
        return false;
    }
}
