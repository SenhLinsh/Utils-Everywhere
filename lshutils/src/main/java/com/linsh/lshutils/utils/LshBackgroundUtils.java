package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/5/11.
 */

/**
 * 通过Activity的状态记录App是否处于后台的工具类
 * <p>
 * 使用:
 * 方法一: 在Application中注册ActivityLifecycleCallbacks, 在 onActivityStarted() 和 onActivityStopped() 调用该类的同名方法.
 * 方法二: 在BaseActivity中的onStart() 和 onStop() 中调用该类的同名方法即可.
 * <p>
 * 该类会记录 Activity 的 onStart 和 onStop 状态, 从而判断是否处于后台.
 */
public class LshBackgroundUtils {

    private static int foregroundActivityCount = 0;

    public static void onActivityStarted() {
        foregroundActivityCount++;
    }

    public static void onActivityStopped() {
        foregroundActivityCount--;
    }

    /**
     * 判断App是否在后台运行
     * 注意: 该方法通过Activity状态判断, 关闭屏幕时返回true
     */
    public static boolean isBackGround() {
        return foregroundActivityCount <= 0;
    }
}
