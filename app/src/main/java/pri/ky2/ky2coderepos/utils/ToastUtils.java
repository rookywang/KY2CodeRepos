package pri.ky2.ky2coderepos.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import pri.ky2.ky2coderepos.base.BaseApplication;

/**
 * Toast 工具类
 *
 * @author wangkaiyan
 * @date 2019/07/23
 */
public class ToastUtils {

    private static Toast mToast;

    public static void show(String message) {
        showCenter(BaseApplication.getAppContext(), message, Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        showCenter(BaseApplication.getAppContext(), BaseApplication.getAppContext().getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showLong(String message) {
        showCenter(BaseApplication.getAppContext(), message, Toast.LENGTH_LONG);
    }

    public static void showLong(int resId) {
        showCenter(BaseApplication.getAppContext(), BaseApplication.getAppContext().getString(resId), Toast.LENGTH_LONG);
    }

    private static void showCenter(Context context, String message, int duration) {
        show(context, message, Gravity.CENTER, 0, 0, duration);
    }

    public static void show(Context context, String message, int gravity, int xOffset, int yOffset, int duration) {
        try {
            doShow(context, message, gravity, xOffset, yOffset, duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void doShow(Context context, String msg, int gravity, int xOffset, int yOffset, int duration) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, msg, duration);
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }
}
