package com.linsh.utilseverywhere;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Method;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Context 相关
 * </pre>
 */
public class ContextUtils {

    private static final String TAG = "ContextUtils";
    private static Context sAppContext;

    private ContextUtils() {
    }

    static void init(Context context) {
        try {
            sAppContext = context.getApplicationContext();
        } catch (Exception ignored) {
        }
        if (sAppContext == null) {
            if (context instanceof Activity) {
                sAppContext = ((Activity) context).getBaseContext();
            } else if (context instanceof Service) {
                sAppContext = ((Service) context).getBaseContext();
            } else {
                sAppContext = context;
            }
        }
    }

    /**
     * 获取 APP Context, 如果 Context 将抛出异常
     *
     * @return Application Context
     */
    public static Context get() {
        if (sAppContext == null) {
            sAppContext = getApplication();
            if (sAppContext == null)
                throw new RuntimeException(String.format("must call %s.init() first.", Utils.class.getName()));
        }
        return sAppContext;
    }

    /**
     * 反射获取 Application
     */
    @SuppressLint("PrivateApi")
    private static Application getApplication() {
        try {
            Method method = Class.forName("android.app.ActivityThread")
                    .getMethod("currentActivityThread");
            method.setAccessible(true);
            Object activityThread = method.invoke(null);
            Object app = activityThread.getClass().getMethod("getApplication")
                    .invoke(activityThread);
            return (Application) app;
        } catch (Throwable e) {
            Log.e(TAG, "can not access Application context by reflection", e);
        }
        return null;
    }

    /**
     * 获取 APP Context
     *
     * @return Application Context
     */
    public static Context getSafely() {
        return sAppContext;
    }

    /**
     * 获取当前 APP 的包名
     *
     * @return 当前 APP 的包名
     */
    public static String getPackageName() {
        return get().getPackageName();
    }

    /**
     * 获取 Resources, 在不方便获取 Context 的地方直接获取 Resources, 简化代码
     */
    public static Resources getResources() {
        return get().getResources();
    }

    /**
     * 获取 ContentResolver
     */
    public static ContentResolver getContentResolver() {
        return get().getContentResolver();
    }

    /**
     * 获取 PackageManager, 在不方便获取 Context 的地方直接获取 PackageManager, 简化代码
     */
    public static PackageManager getPackageManager() {
        return get().getPackageManager();
    }

    /**
     * 获取 ActivityManager
     */
    public static ActivityManager getActivityManager() {
        return (ActivityManager) get().getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 获取 WindowManager
     */
    public static WindowManager getWindowManager() {
        return (WindowManager) get().getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 获取 NotificationManager
     */
    public static NotificationManager getNotificationManager() {
        return (NotificationManager) get().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 获取 AudioManager
     */
    public static AudioManager getAudioManager() {
        return (AudioManager) get().getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * 启动 Activity
     */
    public static void startActivity(Intent intent) {
        get().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 获取系统服务
     *
     * @param name 系统服务名
     */
    public static Object getSystemService(String name) {
        return get().getSystemService(name);
    }

    /**
     * 启动服务
     */
    public static void startService(Intent intent) {
        get().startService(intent);
    }

    /**
     * 停止服务
     */
    public static boolean stopService(Intent intent) {
        return get().stopService(intent);
    }

    /**
     * 获取应用缓存文件夹
     */
    public static File getCacheDir() {
        return get().getCacheDir();
    }

    /**
     * 获取应用数据文件夹
     */
    public static File getFilesDir() {
        return get().getFilesDir();
    }

    /**
     * 获取应用外部缓存文件夹
     */
    public static File getExternalCacheDir() {
        return get().getExternalCacheDir();
    }
}
