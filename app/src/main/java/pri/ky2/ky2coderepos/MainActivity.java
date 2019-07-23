package pri.ky2.ky2coderepos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import pri.ky2.ky2coderepos.base.BaseActivity;

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
    }

    @Override
    protected int setTitleId() {
        return R.string.app_name;
    }

    @Override
    protected void onTvMenuClick(TextView textView) {
        super.onTvMenuClick(textView);
        Toast.makeText(this, R.string.image_description, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onTitleClick() {
        super.onTitleClick();
        Toast.makeText(this, "Title", Toast.LENGTH_SHORT).show();
    }
}
