package com.linsh.utilseverywhere;

import android.content.Context;
import android.os.Handler;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/28
 *    desc   : 工具类: Handler 相关
 *             默认开启一个 Handler，方便在各个地方随时执行主线程任务
 * </pre>
 */
public class HandlerUtils {

    private static Handler mainHandler;

    private HandlerUtils() {
    }

    static void init(Context context) {
        if (mainHandler == null) mainHandler = new Handler(context.getMainLooper());
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
}
