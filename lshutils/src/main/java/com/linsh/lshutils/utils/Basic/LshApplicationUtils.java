package com.linsh.lshutils.utils.Basic;

import android.app.Activity;
import android.app.Application;
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

    public static void init(Application application) {
        // 初始化context
        appContext = application.getApplicationContext();
        // 获取主线程id
        mainTid = Process.myTid();
        // 初始化handler
        mainHandler = new Handler();
    }

    public static void init(Activity activity) {
        if (appContext == null) appContext = activity.getApplicationContext();
        if (mainTid == 0) mainTid = Process.myTid();
        if (mainHandler == null) mainHandler = new Handler();
    }

    public static Context getContext() {
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

    public static void removeRunnalbe(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }
}
