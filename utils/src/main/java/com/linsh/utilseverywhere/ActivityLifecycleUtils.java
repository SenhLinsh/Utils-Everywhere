package com.linsh.utilseverywhere;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类：Activity 生命周期回调相关，目前主要用于获取顶部 Activity 以及判断 APP 是否处于后台
 * </pre>
 */
public class ActivityLifecycleUtils {

    private static int foregroundActivityCount = 0;
    private static LinkedHashMap<Integer, WeakReference<Activity>> sCreatedActivities;
    private static Application.ActivityLifecycleCallbacks sLifecycleCallbacks = null;

    private ActivityLifecycleUtils() {
    }

    /**
     * 使用该工具类需要先进行初始化
     */
    public static void init(Application application) {
        if (sLifecycleCallbacks == null) {
            sCreatedActivities = new LinkedHashMap<>();
            sLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    sCreatedActivities.put(activity.hashCode(), new WeakReference<>(activity));
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    foregroundActivityCount++;
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    foregroundActivityCount--;
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    sCreatedActivities.remove(activity.hashCode());
                }
            };
        }
        application.registerActivityLifecycleCallbacks(sLifecycleCallbacks);
    }

    /**
     * 判断 App 是否处于后台 (屏幕关闭也算处于后台)
     *
     * @return true: 处于后台
     * <br/>false: 处于前台
     */
    public static boolean isAppInBackground() {
        check();
        return foregroundActivityCount <= 0;
    }

    /**
     * 获取处于栈顶的 Activity
     *
     * @return 栈顶 Activity
     */
    public static Activity getTopActivity() {
        check();
        return getTopActivitySafely();
    }

    static Activity getTopActivitySafely() {
        Activity top = null;
        Set<Map.Entry<Integer, WeakReference<Activity>>> entries = sCreatedActivities.entrySet();
        for (Map.Entry<Integer, WeakReference<Activity>> entry : entries) {
            Activity activity = entry.getValue().get();
            if (activity != null) {
                top = activity;
            }
        }
        return top;
    }

    /**
     * 检查该工具类是否被初始化, 没有初始化则抛出异常
     */
    private static void check() {
        if (sLifecycleCallbacks == null) {
            throw new RuntimeException(String.format("请先调用初始化方法 %s.init()", ActivityLifecycleUtils.class.getName()));
        }
    }
}
