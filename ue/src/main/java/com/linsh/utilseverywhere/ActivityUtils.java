package com.linsh.utilseverywhere;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

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

    @IntDef({
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED,
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_USER,
            ActivityInfo.SCREEN_ORIENTATION_BEHIND,
            ActivityInfo.SCREEN_ORIENTATION_SENSOR,
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR,
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

    /**
     * 设置屏幕方向
     *
     * @param activity             Activity
     * @param requestedOrientation 方向
     */
    public static void setScreenOrientation(Activity activity, @ScreenOrientation int requestedOrientation) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}
