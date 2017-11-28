package com.linsh.everywhere;

import android.app.Application;
import android.content.Context;

import com.linsh.everywhere.utils.ActivityLifecycleUtils;
import com.linsh.everywhere.utils.ApplicationUtils;
import com.linsh.everywhere.utils.LogUtils;

/**
 * Created by Senh Linsh on 17/7/6.
 */

public class LshUtils {

    public static void init(Context context) {
        ApplicationUtils.init(context);
    }

    public static void initLogUtils(boolean isDebug) {
        LogUtils.init(isDebug);
    }

    public static void initActivityLifecycleUtils(Application application) {
        ActivityLifecycleUtils.init(application);
    }
}
