package com.senh.lshutils.handler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.senh.lshutils.utils.Basic.LshLogUtils;

import java.lang.reflect.Field;

/**
 * 自定义的 异常处理类 , 实现了 UncaughtExceptionHandler接口
 *
 * @author Administrator
 */
public class LshCrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    public LshCrashHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable thr) {
        caughtUncaughtException(context, thr);
    }

    public static void caughtUncaughtException(Context context, Throwable thr) {
        // 1.获取当前程序的版本号. 版本的id
        String versioninfo = getVersionInfo(context);

        // 2.获取手机的硬件信息.
        String mobileInfo = getMobileInfo();

        LshLogUtils.e("程序挂掉了......................................................." +
                "\r\n版本号:" + versioninfo + "||硬件信息:" + mobileInfo + "||错误信息:\r\n", thr);
    }

    /**
     * 获取手机的硬件信息
     *
     * @return
     */
    private static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        //通过反射获取系统的硬件信息   
        try {

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                //暴力反射 ,获取私有的信息   
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取手机的版本信息
     *
     * @return
     */
    private static String getVersionInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }

}