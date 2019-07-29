package pri.ky2.ky2coderepos.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_MAIN = 0;
    public static final int TAB_MINE = 1;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_MAIN:
                return new MainFragment();
            case TAB_MINE:
                return new MineFragment();
        }
        return new MainFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
