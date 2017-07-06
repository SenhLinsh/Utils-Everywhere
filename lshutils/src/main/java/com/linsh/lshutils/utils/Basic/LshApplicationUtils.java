package com.linsh.lshutils.utils.Basic;

import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by Senh Linsh on 16/12/23.
 */
public class LshApplicationUtils {

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
            throw new RuntimeException("must call LshApplicationUtils.init() first.");
        }
        return appContext;
    }

    public static boolean isMainThread(int tid) {
        return mainTid == tid;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    public static void postRunnable(Runnable runnable) {
        getMainHandler().post(runnable);
    }

    public static void postRunnable(Runnable runnable, long delay) {
        getMainHandler().postDelayed(runnable, delay);
    }

    public static void removeRunnable(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }

    public static String getPackageName() {
        return appContext.getPackageName();
    }
}
