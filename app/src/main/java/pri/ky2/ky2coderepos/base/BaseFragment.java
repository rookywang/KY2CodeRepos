package pri.ky2.ky2coderepos.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.Disposable;
import pri.ky2.ky2coderepos.interfaces.ILoadingView;

/**
 * Fragment 基类
 *
 * @author wangkaiyan
 * @date 2019/07/26
 */
public abstract class BaseFragment extends Fragment implements ILoadingView {

    private View mRootView;
    private BaseUIHelper mUIHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initVariables();
        if (mRootView == null) {
            mRootView = inflater.inflate(setLayoutId(), null);
        }
        mUIHelper = new BaseUIHelper(this, mRootView);
        afterInitViews();
        return mRootView;
    }

    /**
     * 在初始化布局之前处理的逻辑
     */
    public void initVariables() {
    }

    /**
     * 设置布局文件
     */
    public abstract @LayoutRes
    int setLayoutId();

    /**
     * 初始化 View 之后，进行数据填充、网络请求等操作
     */
    public void afterInitViews() {

    }

    /**
     * 显示加载圈
     */
    protected void showLoading() {
        showLoading(true, "");
    }

    protected void showLoading(String msg) {
        showLoading(true, msg);
    }

    protected void showLoading(boolean canCancel, String msg) {
        mUIHelper.showLoading(canCancel, msg);
    }

    /**
     * 取消加载圈
     */
    protected void hideLoading() {
        mUIHelper.hideLoading();
    }

    @Override
    public void showLoadingDialog(String msg) {
        mUIHelper.showLoading(true, msg);
    }

    @Override
    public void hideLoadingDialog() {
        mUIHelper.hideLoading();
    }

    @Override
    public void addNetRequest(Disposable disposable) {
        mUIHelper.addNetRequest(disposable);
    }

    @Override
    public boolean isClosed() {
        return mUIHelper.isClosed();
    }

    @Override
    public String getUIName() {
        if (getActivity() != null) {
            return getActivity().getClass().getSimpleName();
        }
        return "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUIHelper.destroy();
    }
}
