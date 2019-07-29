package pri.ky2.ky2coderepos.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import pri.ky2.ky2coderepos.R;
import pri.ky2.ky2coderepos.base.BaseActivity;
import pri.ky2.ky2coderepos.utils.ToastUtils;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    protected void initVariables(@Nullable Bundle savedInstanceState) {
        super.initVariables(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterInitViews() {
        showTvMenu(R.string.image_description);
    }

    @Override
    protected int setTitleId() {
        return R.string.app_name;
    }

    @Override
    protected void onTvMenuClick(TextView textView) {
        super.onTvMenuClick(textView);
        ToastUtils.show(R.string.image_description);
    }

    @Override
    protected void onTitleClick() {
        super.onTitleClick();
    }
}
