package com.linsh.utilseverywhere;

import android.app.Activity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Activity 相关
 * </pre>
 */
public class ActivityUtils {

    private ActivityUtils() {
    }

    /**
     * 设置屏幕方向
     *
     * @param activity             Activity
     * @param requestedOrientation 方向, 见 {@link android.content.pm.ActivityInfo#screenOrientation ActivityInfo.screenOrientation}
     */
    public static void setScreenOrientation(Activity activity, int requestedOrientation) {
        activity.setRequestedOrientation(requestedOrientation);
    }
}
