package com.linsh.utilseverywhere;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Context 相关
 * </pre>
 */
public class ContextUtils {

    private static Context sAppContext;

    private ContextUtils() {
    }

    static void init(Context context) {
        sAppContext = context.getApplicationContext();
    }

    /**
     * 获取 APP Context, 如果 Context 将抛出异常
     *
     * @return Application Context
     */
    public static Context get() {
        if (sAppContext == null) {
            throw new RuntimeException(String.format("must call %s.init() first.", Utils.class.getName()));
        }
        return sAppContext;
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
     * 获取 PackageManager, 在不方便获取 Context 的地方直接获取 PackageManager, 简化代码
     */
    public static PackageManager getPackageManager() {
        return get().getPackageManager();
    }

    /**
     * 启动 Activity
     */
    public static void startActivity(Intent intent) {
        get().startActivity(intent);
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
