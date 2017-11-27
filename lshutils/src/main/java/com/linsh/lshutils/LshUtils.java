package com.linsh.lshutils;

import android.app.Application;
import android.content.Context;

import com.linsh.lshutils.utils.ApplicationUtils;
import com.linsh.lshutils.utils.LogUtils;
import com.linsh.lshutils.utils.ActivityLifecycleUtils;
import com.linsh.lshutils.utils.FileManagerUtils;

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
