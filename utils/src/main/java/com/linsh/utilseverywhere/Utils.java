package com.linsh.utilseverywhere;

import android.app.Application;
import android.content.Context;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/28
 *    desc   : 用于初始化 Utils-Everywhere
 * </pre>
 */
public class Utils {

    private Utils() {
    }

    public static void init(Context context) {
        ContextUtils.init(context);
        HandlerUtils.init();
        HandlerUtils.postRunnable(new Runnable() {
            @Override
            public void run() {
                ThreadUtils.init();
            }
        });
    }

    public static void initLogUtils(boolean isDebug) {
        LogUtils.init(isDebug);
    }

    public static void initActivityLifecycleUtils(Application application) {
        ActivityLifecycleUtils.init(application);
    }
}
