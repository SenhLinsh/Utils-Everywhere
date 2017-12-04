package com.linsh.utilseverywhere;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
    private static List<WeakReference<Activity>> sCreatedActivities;
    private static Application.ActivityLifecycleCallbacks sLifecycleCallbacks = null;

    private ActivityLifecycleUtils() {
    }

    /**
     * 使用该工具类需要先进行初始化
     */
    public static void init(Application application) {
        if (sLifecycleCallbacks == null) {
            sCreatedActivities = new ArrayList<>();
            sLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    sCreatedActivities.add(new WeakReference<>(activity));
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
                    for (int i = 0; i < sCreatedActivities.size(); ) {
                        Activity savedActivity = sCreatedActivities.get(i).get();
                        if (savedActivity == null || savedActivity == activity) {
                            sCreatedActivities.remove(i);
                        } else {
                            i++;
                        }
                    }
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
     * 获取所有已经创建且没有被销毁的 Activities
     *
     * @return 已经创建的 Activity 集合
     */
    public static List<Activity> getCreatedActivities() {
        check();
        ArrayList<Activity> list = new ArrayList<>();
        for (WeakReference<Activity> reference : sCreatedActivities) {
            Activity activity = reference.get();
            if (activity != null) {
                list.add(activity);
            }
        }
        return list;
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
        int size = sCreatedActivities.size();
        if (size > 0) {
            return sCreatedActivities.get(size - 1).get();
        } else {
            return null;
        }
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
