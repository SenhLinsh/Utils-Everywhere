package com.linsh.everywhere;

import android.app.Application;
import android.content.Context;

import com.linsh.everywhere.utils.ApplicationUtils;
import com.linsh.everywhere.utils.LogUtils;
import com.linsh.everywhere.utils.ActivityLifecycleUtils;

import java.io.File;

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

    public static void initLogUtilsTracer(int mainCount, int maxCount) {
        LogUtils.Tracer.init(mainCount, maxCount);
    }

    public static void initLogUtilsPrinter(String logFilePath) {
        LogUtils.Printer.setLogFilePath(logFilePath);
    }

    public static void initFileManagerUtils(File appDir) {
        FileManagerUtils.initAppDir(appDir);
    }

    public static void initActivityLifecycleUtils(Application application) {
        ActivityLifecycleUtils.init(application);
    }
}
