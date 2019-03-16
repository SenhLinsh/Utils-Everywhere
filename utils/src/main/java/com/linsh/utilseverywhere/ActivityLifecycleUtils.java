package com.linsh.utilseverywhere;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    private static final String TAG = "ActivityLifecycleUtils";
    private static int foregroundActivityCount = 0;
    private static LinkedHashMap<Integer, ActivityStatus> sCreatedActivities;
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
                    ActivityStatus status = new ActivityStatus(activity);
                    sCreatedActivities.put(activity.hashCode(), status);
                    status.setStatus(ActivityStatus.STATUS_CREATED);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    foregroundActivityCount++;
                    ActivityStatus status = sCreatedActivities.get(activity.hashCode());
                    if (status != null) {
                        status.setStatus(ActivityStatus.STATUS_STARTED);
                    }
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    ActivityStatus status = sCreatedActivities.get(activity.hashCode());
                    if (status != null) {
                        status.setStatus(ActivityStatus.STATUS_RESUMED);
                    }
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    ActivityStatus status = sCreatedActivities.get(activity.hashCode());
                    if (status != null) {
                        status.setStatus(ActivityStatus.STATUS_PAUSED);
                    }
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    foregroundActivityCount--;
                    ActivityStatus status = sCreatedActivities.get(activity.hashCode());
                    if (status != null) {
                        status.setStatus(ActivityStatus.STATUS_STOPPED);
                    }
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    ActivityStatus status = sCreatedActivities.get(activity.hashCode());
                    if (status != null) {
                        status.setStatus(ActivityStatus.STATUS_DESTROYED);
                    }
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
     * 获取指定 Activity 的生命周期状态
     */
    public ActivityStatus getActivityStatus(Activity activity) {
        check();
        return sCreatedActivities.get(activity.hashCode());
    }

    /**
     * 获取指定 Activity 的生命周期状态
     * <p>
     * 如果存在多个该 Activity 的实例, 则返回栈顶 Activity 的状态
     */
    public ActivityStatus getActivityStatus(Class<? extends Activity> clazz) {
        check();
        ActivityStatus result = null;
        for (Map.Entry<Integer, ActivityStatus> entry : sCreatedActivities.entrySet()) {
            ActivityStatus status = entry.getValue();
            Activity activity = status.activityRef.get();
            if (activity != null && activity.getClass() == clazz) {
                result = status;
            }
        }
        return result;
    }

    /**
     * 获取处于栈顶的 Activity 的生命周期状态
     *
     * @return 栈顶的 Activity 的生命周期状态
     */
    public static ActivityStatus getTopActivityStatus() {
        check();
        ActivityStatus result = null;
        for (Map.Entry<Integer, ActivityStatus> entry : sCreatedActivities.entrySet()) {
            ActivityStatus status = entry.getValue();
            Activity activity = status.activityRef.get();
            if (activity != null) {
                result = status;
            }
        }
        return result;
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
        if (sCreatedActivities != null) {
            Set<Map.Entry<Integer, ActivityStatus>> entries = sCreatedActivities.entrySet();
            for (Map.Entry<Integer, ActivityStatus> entry : entries) {
                Activity activity = entry.getValue().activityRef.get();
                if (activity != null) {
                    top = activity;
                }
            }
        }
        return top;
    }

    /**
     * 获取已经创建的 Activity
     */
    public static List<Activity> getCreatedActivities() {
        check();
        ArrayList<Activity> list = new ArrayList<>();
        for (Map.Entry<Integer, ActivityStatus> entry : sCreatedActivities.entrySet()) {
            Activity activity = entry.getValue().activityRef.get();
            if (activity != null) {
                list.add(activity);
            }
        }
        return list;
    }

    /**
     * 检查该工具类是否被初始化, 没有初始化则抛出异常
     */
    private static void check() {
        if (sLifecycleCallbacks == null) {
            Context context = ContextUtils.get();
            if (context instanceof Application) {
                init((Application) context);
            } else {
                Application application = getApplication();
                if (application != null)
                    init(application);
            }
            if (sLifecycleCallbacks == null)
                throw new RuntimeException(String.format("请先调用初始化方法 %s.init()", ActivityLifecycleUtils.class.getName()));
        }
    }

    public static class ActivityStatus {

        public static final int STATUS_DESTROYED = 0;
        public static final int STATUS_CREATED = 1;
        public static final int STATUS_STARTED = 2;
        public static final int STATUS_RESUMED = 3;
        public static final int STATUS_PAUSED = 4;
        public static final int STATUS_STOPPED = 5;

        private int status;
        private WeakReference<Activity> activityRef;

        public ActivityStatus(Activity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        public int getStatus() {
            return status;
        }

        void setStatus(int status) {
            this.status = status;
        }

        public boolean isCreated() {
            return status >= STATUS_CREATED;
        }

        public boolean isStarted() {
            return status >= STATUS_STARTED;
        }

        public boolean isResumed() {
            return status >= STATUS_RESUMED;
        }

        public boolean isPaused() {
            return status >= STATUS_PAUSED;
        }

        public boolean isStoped() {
            return status >= STATUS_STOPPED;
        }

        public boolean isDestroyed() {
            return status == STATUS_DESTROYED;
        }
    }

    /**
     * 反射获取 Application
     */
    @SuppressLint("PrivateApi")
    private static Application getApplication() {
        try {
            Method method = Class.forName("android.app.ActivityThread").getMethod("currentActivityThread");
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
}
