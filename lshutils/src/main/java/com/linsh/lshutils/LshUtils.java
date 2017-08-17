package com.linsh.lshutils;

import android.app.Application;
import android.content.Context;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;
import com.linsh.lshutils.utils.Basic.LshLogUtils;
import com.linsh.lshutils.utils.LshActivityLifecycleUtils;
import com.linsh.lshutils.utils.LshBackgroundUtils;
import com.linsh.lshutils.utils.LshFileManagerUtils;

import java.io.File;

/**
 * Created by Senh Linsh on 17/7/6.
 */

public class LshUtils {

    public static void init(Context context) {
        LshApplicationUtils.init(context);
    }

    public static void initLogUtils(boolean isDebug) {
        LshLogUtils.init(isDebug);
    }

    public static void initLogUtilsTracer(int mainCount, int maxCount) {
        LshLogUtils.Tracer.init(mainCount, maxCount);
    }

    public static void initLogUtilsPrinter(String logFilePath) {
        LshLogUtils.Printer.setLogFilePath(logFilePath);
    }

    public static void initFileManagerUtils(File appDir) {
        LshFileManagerUtils.initAppDir(appDir);
    }

    public static void initActivityLifecycleUtils(Application application) {
        LshActivityLifecycleUtils.init(application);
    }

    public static void initBackgroundUtils(Application application) {
        LshBackgroundUtils.init(application);
    }
}
