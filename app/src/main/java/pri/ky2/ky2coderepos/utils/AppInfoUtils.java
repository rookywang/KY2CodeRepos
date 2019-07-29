package pri.ky2.ky2coderepos.utils;

import pri.ky2.ky2coderepos.BuildConfig;

/**
 * App 信息工具类
 *
 * @author wangkaiyan
 * @date 2019/07/29
 */
public class AppInfoUtils {

    /**
     * 判断 APP 是否是 Debug 模式
     *
     * @return true：是
     */
    public static boolean isDebug() {
        if (BuildConfig.DEBUG) {
            return true;
        }
        return false;
    }
}
