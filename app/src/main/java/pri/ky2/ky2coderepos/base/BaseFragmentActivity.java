package pri.ky2.ky2coderepos.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.disposables.Disposable;
import pri.ky2.ky2coderepos.R;
import pri.ky2.ky2coderepos.interfaces.ILoadingView;

/**
 * FragmentActivity 基类
 *
 * @author wangkaiyan
 * @date 2019/07/26
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements ILoadingView {

    private BaseUIHelper mUIHelper;
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvMenu;
    protected ImageView ivMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mUIHelper = new BaseUIHelper(this);
        initVariables(savedInstanceState);
        // 如果有 title
        // 一定要在布局文件里面引入 <include layout="@layout/include_common_title"/>
        if (setTitleId() != 0) {
            initHeader();
            tvTitle.setText(getString(setTitleId()));
        }
        afterInitViews();
    }

    protected void initHeader() {
        try {
            ivBack = findViewById(R.id.iv_common_title_back);
            tvTitle = findViewById(R.id.tv_common_title_title);
            tvMenu = findViewById(R.id.tv_common_title_menu);
            ivMenu = findViewById(R.id.iv_common_title_menu);

            ivBack.setOnClickListener(mClickListener);
            tvTitle.setOnClickListener(mClickListener);
            tvMenu.setOnClickListener(mClickListener);
            ivMenu.setOnClickListener(mClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == ivBack) {
                onBackClick();
            } else if (view == tvTitle) {
                onTitleClick();
            } else if (view == tvMenu) {
                onTvMenuClick(tvMenu);
            } else if (view == ivMenu) {
                onIvMenuClick(ivMenu);
            }
        }
    };

    @Override
    public void onBackPressed() {
        onBackClick();
    }

    /**
     * 隐藏返回键
     */
    public void hideBackBtn() {
        if (null != ivBack) {
            ivBack.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 返回按键
     */
    protected void onBackClick() {
        finish();
    }

    /**
     * 点击标题响应事件
     */
    protected void onTitleClick() {
    }

    /**
     * 设置菜单按钮文案
     *
     * @param menuText 文案 stringId
     */
    protected void showTvMenu(@StringRes int menuText) {
        String text = getString(menuText);
        if (tvMenu != null && !TextUtils.isEmpty(text)) {
            tvMenu.setText(text);
        }
    }

    /**
     * 文字菜单按钮点击事件
     *
     * @param textView 菜单按钮
     */
    protected void onTvMenuClick(TextView textView) {
    }

    /**
     * 设置菜单按钮图片
     *
     * @param menuIco 图片 id
     */
    protected void showIvMenu(@DrawableRes int menuIco) {
        if (ivMenu != null) {
            ivMenu.setVisibility(View.VISIBLE);
            ivMenu.setImageResource(menuIco);
        }
    }

    /**
     * 图片菜单按钮点击事件
     *
     * @param imageView 图片按钮
     */
    protected void onIvMenuClick(ImageView imageView) {
    }

    /**
     * 设置布局文件
     *
     * @return 布局文件的 layoutId
     */
    protected abstract @LayoutRes
    int setLayoutId();

    /**
     * 初始化各种变量，如 Bundle、上个页面传递过来的数据
     */
    protected void initVariables(@Nullable Bundle savedInstanceState) {
    }

    /**
     * 初始化 View 之后，进行数据填充、网络请求等操作
     */
    protected abstract void afterInitViews();

    /**
     * 设置页面标题
     *
     * @return 页面标题 stringId，没有标题返回 0
     */
    protected abstract int setTitleId();

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
        return this.getClass().getSimpleName();
    }
}
