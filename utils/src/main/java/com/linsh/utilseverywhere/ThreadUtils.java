package com.linsh.utilseverywhere;

import android.content.Context;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 线程相关
 * </pre>
 */
public class ThreadUtils {

    private ThreadUtils() {
    }

    /**
     * 获取当前线程名
     *
     * @return 当前线程名
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * 判断是否为主线程
     *
     * @return true 是; false 不是
     */
    public static boolean isMainThread() {
        return getContext().getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }

    /**
     * 判断是否为主线程
     *
     * @param tid thread id
     * @return true 是; false 不是
     */
    public static boolean isMainThread(long tid) {
        return getContext().getMainLooper().getThread().getId() == tid;
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 判断当前线程是否为主线程
     *
     * @return true 是; false 不是
     */
    public static boolean isMainThread() {
        return sMainTid == Process.myTid();
    }
}
