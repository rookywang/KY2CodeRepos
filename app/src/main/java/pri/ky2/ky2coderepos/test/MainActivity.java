package pri.ky2.ky2coderepos.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.OnClick;
import pri.ky2.ky2coderepos.R;
import pri.ky2.ky2coderepos.base.BaseFragmentActivity;
import pri.ky2.ky2coderepos.widget.MainViewPager;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.vp_main)
    MainViewPager mvpMain;
    @BindView(R.id.rb_main)
    RadioButton rbMain;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;

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
        mvpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mvpMain.setCurrentItem(MainPagerAdapter.TAB_MAIN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        togglePage(mvpMain.getCurrentItem());
    }

    @Override
    protected int setTitleId() {
        return 0;
    }

    @OnClick(R.id.rb_main)
    public void showDevice() {
        togglePage(MainPagerAdapter.TAB_MAIN);
    }

    @OnClick(R.id.rb_mine)
    public void showMine() {
        togglePage(MainPagerAdapter.TAB_MINE);
    }

    /**
     * 切换 Fragment 和菜单选项按钮
     */
    private void togglePage(int position) {
        mvpMain.setCurrentItem(position);
        rbMain.setSelected(false);
        rbMine.setSelected(false);
        if (position == MainPagerAdapter.TAB_MAIN) {
            rbMain.setSelected(true);
        } else if (position == MainPagerAdapter.TAB_MINE) {
            rbMine.setSelected(true);
        }
    }
}
