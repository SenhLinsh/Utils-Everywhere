package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/5/11.
 */

import android.app.Application;

/**
 * 通过Activity的状态记录App是否处于后台的工具类
 * <p>
 * 该类会记录 Activity 的 onStart 和 onStop 状态, 从而判断是否处于后台.
 */
public class LshBackgroundUtils {

    public static void init(Application application) {
        LshActivityLifecycleUtils.init(application);
    }

    /**
     * 判断App是否在后台运行
     * <p>注意: 该方法通过Activity状态判断, 关闭屏幕时返回true</p>
     */
    public static boolean isBackground() {
        return LshActivityLifecycleUtils.isBackGround();
    }
}
