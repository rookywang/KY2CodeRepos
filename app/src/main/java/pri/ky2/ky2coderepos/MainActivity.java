package pri.ky2.ky2coderepos;

import pri.ky2.ky2coderepos.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterInitViews() {

    }

    @Override
    protected int setTitleId() {
        return R.string.app_name;
    }
}
