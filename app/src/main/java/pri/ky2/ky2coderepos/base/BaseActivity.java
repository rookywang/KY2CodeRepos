package pri.ky2.ky2coderepos.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pri.ky2.ky2coderepos.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected final static String TAG = "BaseActivity";

    @Nullable
    @BindView(R.id.iv_common_title_back)
    protected ImageView ivBack;
    @Nullable
    @BindView(R.id.tv_common_title_title)
    protected TextView tvTitle;
    @Nullable
    @BindView(R.id.tv_common_title_menu)
    protected TextView tvMenu;
    @Nullable
    @BindView(R.id.iv_common_title_menu)
    protected ImageView ivMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        initVariables(savedInstanceState);
        Log.e(TAG, "tvTitle != null = " + (tvTitle != null));
        Log.e(TAG, "setTitleId() != 0 = " + (setTitleId() != 0));
        if (tvTitle != null && setTitleId() != 0) {
            tvTitle.setText(getString(setTitleId()));
        }
        afterInitViews();
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
    protected void initVariables(@Nullable Bundle savedInstanceState) {}

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
}
