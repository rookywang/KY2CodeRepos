package pri.ky2.ky2coderepos.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import pri.ky2.ky2coderepos.R;
import pri.ky2.ky2coderepos.net.NetRequest;

/**
 * UI 辅助类，定义了加载框
 *
 * @author wangkaiyan
 * @date 2019/07/23
 */
public class BaseUiHelper {

    private Activity mActivity;
    private BaseFragment mFragment;
    private Dialog mLoadingDialog;
    private List<Disposable> mRequestList;
    private Unbinder mUnBinder;

    public BaseUiHelper(Activity activity) {
        mActivity = activity;
        // ButterKnife 绑定，必须在 setContentView 之后
        mUnBinder = ButterKnife.bind(activity);
    }

    public BaseUiHelper(BaseFragment fragment, View view) {
        mFragment = fragment;
        mUnBinder = ButterKnife.bind(fragment, view);
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

    public void addNetRequest(Disposable disposable) {
        if (mRequestList == null) {
            mRequestList = new ArrayList<>();
        }
        mRequestList.add(disposable);
    }

    public void destroy() {
        hideLoading();
        NetRequest.cancel(mRequestList);
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        }
        if (mFragment != null) {
            return mFragment.getActivity();
        }
        return null;
    }

    public boolean isClosed() {
        if (mActivity == null || mActivity.isFinishing() || mActivity.isDestroyed()) {
            return true;
        }
        return mFragment != null && mFragment.isDetached();
    }
}
