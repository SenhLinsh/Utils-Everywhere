package com.linsh.utilseverywhere;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 服务相关
 * </pre>
 */
public class ServiceUtils {

    private ServiceUtils() {
    }

    /**
     * 判断服务是否正在运行
     *
     * @param service 服务类
     * @return 是否正在运行
     */
    public static boolean isRunning(Class<? extends Service> service) {
        ActivityManager manager = (ActivityManager) ContextUtils.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取运行中服务
        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(1000);
        String serviceName = service.getName();
        for (ActivityManager.RunningServiceInfo info : services) {
            // 获取每一条运行中的服务的类名并判断
            String name = info.service.getClassName();
            if (TextUtils.equals(serviceName, name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 启动指定的服务
     *
     * @param clazz 服务类
     */
    public static void startService(Class<?> clazz) {
        ContextUtils.startService(new Intent(ContextUtils.get(), clazz));
    }

    /**
     * 停止指定的服务
     *
     * @param clazz 服务类
     */
    public static boolean stopService(Class<?> clazz) {
        return ContextUtils.stopService(new Intent(ContextUtils.get(), clazz));
    }
}
