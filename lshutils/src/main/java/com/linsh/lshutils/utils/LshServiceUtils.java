package com.linsh.lshutils.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Senh Linsh on 17/1/8.
 */
public class LshServiceUtils {
    /**
     * 判断服务是否正在运行
     */
    public static boolean isRunning(Context context, Class<? extends Service> service) {
        //获取Activity管理器
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        //获取运行中服务
        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(1000);
        String serviceName = service.getName();
        for (ActivityManager.RunningServiceInfo info : services) {
            //获取每一条运行中的服务的类名并判断
            String name = info.service.getClassName();
            if (TextUtils.equals(serviceName, name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 启动服务
     */
    public static void startService(Class<?> clazz) {
        LshContextUtils.startService(new Intent(LshContextUtils.get(), clazz));
    }

    /**
     * 停止服务
     */
    public static boolean stopService(Class<?> clazz) {
        return LshContextUtils.stopService(new Intent(LshContextUtils.get(), clazz));
    }
}
