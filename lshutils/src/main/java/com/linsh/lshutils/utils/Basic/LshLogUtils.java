package com.linsh.lshutils.utils.Basic;

import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;

import com.linsh.lshutils.utils.LshTimeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Senh Linsh on 17/1/8.
 */
public class LshLogUtils {

    private static boolean sIsDebug = checkDebugMode();
    private static String sTag = "LshLogUtils: ";
    // 保存本地log文件的目录
    private static String sErrorFilePath =
            Environment.getExternalStorageDirectory() + "/" + LshApplicationUtils.getPackageName() + "/LshLog.txt";

    public static void init(boolean isDebug) {
        sIsDebug = isDebug;
    }

    public static void init(boolean isDebug, String tag) {
        sIsDebug = isDebug;
        sTag = tag;
    }

    public static void init(boolean isDebug, String tag, String errorFilePath) {
        sIsDebug = isDebug;
        sTag = tag;
        sErrorFilePath = errorFilePath;
    }

    public static void v(Object msg) {
        if (sIsDebug) {
            String mmsg = msg != null ? msg.toString() : "msg == null";
            Log.v(sTag + getClassName(), mmsg);
        }
    }

    public static void v(String tag, Object msg) {
        if (sIsDebug) {
            String mmsg = msg != null ? msg.toString() : "msg == null";
            Log.v(sTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void d(Object msg) {
        if (sIsDebug) {
            String mmsg = msg != null ? msg.toString() : "msg == null";
            Log.d(sTag + getClassName(), mmsg);
        }
    }

    public static void d(String tag, Object msg) {
        String mmsg = msg != null ? msg.toString() : "msg == null";
        if (sIsDebug) {
            Log.d(sTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void i(Object msg) {
        if (sIsDebug) {
            String mmsg = msg != null ? msg.toString() : "msg == null";
            Log.i(sTag + getClassName(), mmsg);
        }
    }

    public static void i(String tag, Object msg) {
        if (sIsDebug) {
            String mmsg = msg != null ? msg.toString() : "msg == null";
            Log.i(sTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void w(Object msg) {
        if (sIsDebug) {
            String mmsg = msg != null ? msg.toString() : "msg == null";
            Log.w(sTag + getClassName(), mmsg);
        }
    }

    public static void w(String tag, Object msg) {
        if (sIsDebug) {
            String mmsg = msg != null ? msg.toString() : "msg == null";
            Log.w(sTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void e(String msg) {
        if (sIsDebug) {
            Log.e(sTag + getClassName(), msg);
        }
    }

    public static void e(String tag, String msg) {
        if (sIsDebug) {
            Log.e(sTag + getClassName(), tag + ": " + msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (sIsDebug) {
            Log.e(sTag + getClassName(), tag + ": " + msg);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    public static void e(String msg, Throwable e) {
        if (sIsDebug) {
            Log.e(sTag + getClassName(), msg, e);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    private static boolean checkDebugMode() {
        ApplicationInfo applicationInfo = LshApplicationUtils.getContext().getApplicationInfo();
        boolean debug = applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i("LshLog", "checkDebugMode: " + debug);
        return debug;
    }

    //================================================ 打印日志到本地相关 ================================================//
    public static void printE(String msg, Throwable e) {
        if (sIsDebug) {
            Log.e(sTag + getClassName(), msg, e);
            if (e != null) {
                e.printStackTrace();
            }
        }
        List<String> logs = new ArrayList<>();
        logs.add("  ---------------------Throw an ERROR----------------  ");
        if (e == null) {
            logs.add(msg);
        } else {
            logs.add(msg + " (##" + getClassName() + "##" + callMethodAndLine() + ")");
            logs.add("Message = " + e.getMessage());
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement stack : stackTrace) {
                logs.add(stack.toString());
            }
        }
        printLog(logs);
    }

    private static void printLog(String... logs) {
        printLog(Arrays.asList(logs));
    }

    private static void printLog(List<String> logs) {
        if (!LshFileUtils.checkPermission()) return;

        BufferedWriter bw = null;
        try {
            File file = new File(sErrorFilePath);
            if (file.exists()) {
                // 超过一个星期没有修改, 直接删除
                if (System.currentTimeMillis() - file.lastModified() > 1000L * 60 * 60 * 24 * 7) {
                    file.delete();
                }
            } else {
                LshFileUtils.makeParentDirs(file);
            }

            String curTime = "[" + LshTimeUtils.getCurrentTimeStringEN() + "] ";
            bw = new BufferedWriter(new FileWriter(file, true));
            for (String msg : logs) {
                bw.append(curTime).append(msg);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LshIOUtils.close(bw);
        }
    }

    /**
     * 获取调用Log方法的类名
     */
    private static String getClassName() {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        String[] split = result.split("\\.");
        try {
            result = split[split.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取调用Log方法的方法和行数
     */
    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result += thisMethodStack.getClassName() + "";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
}
