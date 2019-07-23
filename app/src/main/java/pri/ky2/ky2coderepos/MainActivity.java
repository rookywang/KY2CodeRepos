package pri.ky2.ky2coderepos;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import pri.ky2.ky2coderepos.base.BaseActivity;
import pri.ky2.ky2coderepos.base.BaseApplication;
import pri.ky2.ky2coderepos.utils.ToastUtils;

public class MainActivity extends BaseActivity {

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
        Log.d(TAG, "" + BaseApplication.getAppContext());
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
        showLoading(getString(R.string.image_description));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
            }
        }, 2000);
    }
}
