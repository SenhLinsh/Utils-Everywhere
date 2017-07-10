package com.linsh.lshutils.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Senh Linsh on 17/6/5.
 */

public class LshActivityLifecycleUtils {

    private static int foregroundActivityCount = 0;
    private static List<WeakReference<Activity>> sCreatedActivities;
    private static Application.ActivityLifecycleCallbacks sLifecycleCallbacks = null;

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

    public static boolean isBackGround() {
        check();
        return foregroundActivityCount <= 0;
    }

    public static ArrayList<Activity> getCreatedActivities() {
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

    public static Activity getTopActivity() {
        check();
        int size = sCreatedActivities.size();
        if (size > 0) {
            return sCreatedActivities.get(size - 1).get();
        } else {
            return null;
        }
    }

    private static void check() {
        if (sLifecycleCallbacks == null) {
            throw new RuntimeException("请先调用初始化方法 init()");
        }
    }
}
