package pri.ky2.ky2coderepos.base;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getAppContext() {
        return mContext;
    }
}
