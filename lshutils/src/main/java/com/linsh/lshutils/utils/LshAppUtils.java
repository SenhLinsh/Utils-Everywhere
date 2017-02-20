package com.linsh.lshutils.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

import java.util.List;

/**
 * Created by Senh Linsh on 16/8/9.
 */
public class LshAppUtils {

    /**
     * 判断服务是否在运行
     */
    public static boolean isServiceRunning(String serviceClassName) {
        ActivityManager activityManager = (ActivityManager) LshApplicationUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断App是否在前台运行
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) LshApplicationUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (getPackageName().equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取包名
     */
    public static String getPackageName() {
        return LshApplicationUtils.getContext().getPackageName();
    }


    /**
     * 获取版本名称
     */
    public static String getVersionName() {
        PackageManager packageManager = LshApplicationUtils.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            // 获取版本名称
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取版本号
     */
    public static int getVersionCode() {
        PackageManager packageManager = LshApplicationUtils.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            // 获取版本名称
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
