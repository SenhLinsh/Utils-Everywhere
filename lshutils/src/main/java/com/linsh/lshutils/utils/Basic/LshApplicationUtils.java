package com.linsh.lshutils.utils.Basic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.linsh.lshutils.utils.LshArrayUtils;

/**
 * Created by Senh Linsh on 16/12/23.
 */
public class LshApplicationUtils {

    private static Context appContext;
    private static int mainTid;
    private static Handler mainHandler;

    private static String realPackageName;

    public static void init(Application application) {
        // 初始化context
        appContext = application.getApplicationContext();
        // 获取主线程id
        mainTid = Process.myTid();
        // 初始化handler
        mainHandler = new Handler();
        // 设置真正的包名
        setRealPackageName(application);
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

    public static void removeRunnable(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }

    public static String getPackageName() {
        return appContext.getPackageName();
    }

    /**
     * 获取应用真正的包名, 即清单文件里声明的包名, 用来存放R文件和BuildConfig, 以及常用来作为项目的一级包名
     */
    private static void setRealPackageName(Application application) {
        String contextPac = application.getApplicationContext().getPackageName();
        String applicationPac = application.getClass().getPackage().getName();
        if (applicationPac.contains(contextPac)) {
            // 可以匹配上, 包名没有添加后缀
            realPackageName = contextPac;
        } else {
            // 包名添加后缀
            String[] contextSplit = contextPac.split("\\.");
            String[] applicationSplit = applicationPac.split("\\.");
            for (int i = 0; i < applicationSplit.length; i++) {
                if (i >= contextSplit.length || i >= applicationSplit.length || !contextSplit[i].equals(applicationSplit[i])) {
                    if (contextSplit[i].contains(applicationSplit[i])) {
                        realPackageName = LshArrayUtils.joint(applicationSplit, i + 1, ".");
                    } else {
                        realPackageName = LshArrayUtils.joint(applicationSplit, i, ".");
                    }
                    break;
                }
            }
        }
        // FIXME: 17/3/14
        // 当前方法有可能无法获取真正的包名
    }

    /**
     * 获取应用真正的包名
     * 1. gradle可以重新指定包名
     * 2. gradle可以给PackageName添加后缀
     * 从而导致Context.getPackageName()无法获取真正的包名
     */
    public static String getRealPackageName() {
        return realPackageName;
    }
}
