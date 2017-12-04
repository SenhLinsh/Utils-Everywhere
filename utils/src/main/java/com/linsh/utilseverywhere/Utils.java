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

    /**
     * 初始化工具类
     *
     * @param context Context 对象
     */
    public static void init(Context context) {
        ContextUtils.init(context);
        HandlerUtils.init(context);
        HandlerUtils.postRunnable(new Runnable() {
            @Override
            public void run() {
                ThreadUtils.init();
            }
        });
        if (context.getApplicationContext() instanceof Application) {
            ActivityLifecycleUtils.init((Application) context.getApplicationContext());
        }
    }

    /**
     * 初始化 LogUtils, 可手动设置是否打印日志
     * @param isDebug true 表示打印日志, false 不打印
     */
    public static void initLogUtils(boolean isDebug) {
        LogUtils.init(isDebug);
    }
}
