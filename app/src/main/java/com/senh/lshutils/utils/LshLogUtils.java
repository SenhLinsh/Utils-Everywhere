package com.senh.lshutils.utils;

import android.os.Environment;
import android.util.Log;

import com.example.yy.feibo_common.DrawApplication;
import com.example.yy.feibo_common.TestVariable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class LshLogUtils {
    public static boolean IS_DEBUG = TestVariable.IS_DEBUG;
    public static boolean IS_PRINT = false;
    public static boolean IS_PRINT_OUTSIDE = false;

    private static String mTag = "LshLogUtils";
    public static String FilePath;

    static {
        if (IS_PRINT_OUTSIDE) {
            FilePath = Environment.getExternalStorageDirectory() + "/FeiboLog.txt";
        } else {
            FilePath = new File(DrawApplication.getContext().getFilesDir(), "FeiboLog.txt").getAbsolutePath();
        }
    }

    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;
    // 输出LOG的级别
    private static int DEBUG_LEVEL = 0;
    // 打印到本地的输出级别
    private static int PRINT_LEVEL = 2;

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (DEBUG_LEVEL < LEVEL_VERBOSE && IS_DEBUG) {
            Log.v(mTag + getClassName(), mmsg);
        }
        if (PRINT_LEVEL < LEVEL_VERBOSE && IS_PRINT) {
            mmsg = mmsg + "        (##" + getClassName() + "##" + callMethodAndLine() + ")";
            printLog("--VERBOSE--" + mmsg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (DEBUG_LEVEL < LEVEL_DEBUG && IS_DEBUG) {
            Log.d(mTag + getClassName(), mmsg);
        }
        if (PRINT_LEVEL < LEVEL_DEBUG && IS_PRINT) {
            printLog("--DEBUG--" + mmsg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (DEBUG_LEVEL < LEVEL_INFO && IS_DEBUG) {
            Log.i(mTag + getClassName(), mmsg);
        }
        if (PRINT_LEVEL < LEVEL_INFO && IS_PRINT) {
            printLog("--INFO--" + mmsg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (DEBUG_LEVEL < LEVEL_WARN && IS_DEBUG) {
            Log.w(mTag + getClassName(), mmsg);
        }
        if (PRINT_LEVEL < LEVEL_WARN && IS_PRINT) {
            printLog("--WARN--" + mmsg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (DEBUG_LEVEL < LEVEL_ERROR && IS_DEBUG) {
            Log.e(mTag + getClassName(), mmsg);
        }
        if (PRINT_LEVEL < LEVEL_ERROR && IS_PRINT) {
            printLog("--ERROR--" + mmsg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(Object msg, Throwable e) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (DEBUG_LEVEL < LEVEL_ERROR && IS_DEBUG) {
            Log.e(mTag + getClassName(), mmsg, e);
            if (e != null) {
                e.printStackTrace();
            }
        }
        if (PRINT_LEVEL < LEVEL_ERROR && IS_PRINT) {
            mmsg = mmsg + "        (##" + getClassName() + "##" + callMethodAndLine() + ")";
            printLog("--ERROR--" + mmsg);
            printLog("---------------------程序崩溃啦---------------\r\n" + e.getMessage() + "\r\n");
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement stack : stackTrace) {
                printLog(stack.toString());
            }
        }
    }

    private static void printLog(String msg) {
        BufferedWriter bw = null;
        try {
            File file = new File(FilePath);
            if (file.exists() && isFileSizeOutof1M(file)) {
                file.delete();
            }
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(msg);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LshIOUtils.close(bw);
        }

    }

    private static String getClassName() {
        String result = "";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        return result;
    }

    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result += thisMethodStack.getClassName() + "";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

    public static boolean isFileSizeOutof1M(File file) throws Exception {
        if (file == null) return false;
        return file.length() >= 1048576 * 0.1;
    }

}
