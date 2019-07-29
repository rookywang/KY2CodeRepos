package pri.ky2.ky2coderepos.utils;

import com.orhanobut.logger.Logger;

/**
 * 日志工具
 *
 * @author wangkaiyan
 * @date 2019/07/29
 */
public class LogUtils {

    /**
     * 是否开启日志 true：开启，即 Debug 模式
     */
    protected static boolean isEnable = AppInfoUtils.isDebug();


    public static void d(String msg) {
        if (isEnable) {
            Logger.d(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isEnable) {
            Logger.t(tag).d(msg);
        }
    }

    public static void json(String msg) {
        if (isEnable) {
            Logger.json(msg);
        }
    }

    public static void json(String tag, String msg) {
        if (isEnable) {
            Logger.t(tag).json(msg);
        }
    }

    public static void i(String msg) {
        if (isEnable) {
            Logger.i(msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isEnable) {
            Logger.t(tag).i(msg);
        }
    }

    public static void w(String msg) {
        if (isEnable) {
            Logger.w(msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isEnable) {
            Logger.t(tag).w(msg);
        }
    }

    public static void e(String msg) {
        if (isEnable) {
            Logger.e(msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isEnable) {
            Logger.t(tag).e(msg);
        }
    }
}
