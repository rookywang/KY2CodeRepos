package pri.ky2.ky2coderepos.base;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * @author wangkaiyan
 * @date 2019/07/23
 */
public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        initThirdLibraries();
    }

    public static Context getAppContext() {
        return mContext;
    }

    private void initThirdLibraries() {
        // LitePal 配置
        LitePal.initialize(this);
    }
}
