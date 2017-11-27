package com.linsh.lshutils.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Application 相关, 即将被解体
 * </pre>
 */
public class ApplicationUtils {

    private static Context appContext;
    private static int mainTid;
    private static Handler mainHandler;

    public static void init(Context context) {
        // 初始化context
        appContext = context.getApplicationContext();
        // 获取主线程id
        if (mainTid == 0) mainTid = Process.myTid();
        // 初始化handler
        if (mainHandler == null) mainHandler = new Handler();
    }

    public static Context getContext() {
        if (appContext == null) {
            throw new RuntimeException(String.format("must call %s.init() first.", ApplicationUtils.class.getSimpleName()));
        }
        return appContext;
    }

    public static Context getContextSafely() {
        return appContext;
    }

    /**
     * 判断是否为主线程
     *
     * @param tid 当前线程的 thread id
     * @return true 是; false 不是
     */
    public static boolean isMainThread(int tid) {
        return mainTid == tid;
    }

    /**
     * 获取主线程 Handler
     *
     * @return 主线程 Handler
     */
    public static Handler getMainHandler() {
        return mainHandler;
    }

    /**
     * 在主线程 Handler 中执行任务
     *
     * @param runnable 可执行的任务
     */
    public static void postRunnable(Runnable runnable) {
        getMainHandler().post(runnable);
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     *
     * @param runnable 可执行的任务
     * @param delay    延迟时间
     */
    public static void postRunnable(Runnable runnable, long delay) {
        getMainHandler().postDelayed(runnable, delay);
    }

    /**
     * 在主线程 Handler 中清除任务
     *
     * @param runnable 需要清除的任务
     */
    public static void removeRunnable(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }

    /**
     * 获取当前 APP 的包名
     *
     * @return 当前 APP 的包名
     */
    public static String getPackageName() {
        return appContext.getPackageName();
    }
}
