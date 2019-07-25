package pri.ky2.ky2coderepos.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * 页面加载框与网络请求管理接口
 *
 * @author wangkaiyan
 * @date 2019/07/25
 */
public interface ILoadingView {

    /**
     * 显示加载框
     *
     * @param msg 加载框文字内容
     */
    void showLoadingDialog(String msg);

    /**
     * 隐藏加载框
     */
    void hideLoadingDialog();

    /**
     * 保存当前页面的所有网络请求，以便页面结束之后取消网络请求
     *
     * @param disposable 可以被取消的网络请求任务
     */
    void addNetRequest(Disposable disposable);

    /**
     * 页面是否结束
     *
     * @return true：结束
     */
    boolean isClosed();

    /**
     * 获取页面名称
     *
     * @return 页面名称
     */
    String getUIName();
}
