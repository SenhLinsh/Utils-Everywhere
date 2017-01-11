package com.senh.lshutils.utils.Basic;

import android.os.Environment;
import android.util.Log;

import com.senh.lshutils.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by Senh Linsh on 17/1/8.
 */
public class LshLogUtils {
    public static boolean IS_DEBUG = BuildConfig.DEBUG;
    private static String mTag = "LshLogUtils: ";
    // 保存本地log文件的目录
    private static String FilePath =
            Environment.getExternalStorageDirectory() + "/" + LshApplicationUtils.getContext().getPackageName() + "/LshLog.txt";

    public static void v(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.v(mTag + getClassName(), mmsg);
        }
    }

    public static void v(String tag, Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.v(mTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void d(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.d(mTag + getClassName(), mmsg);
        }
    }

    public static void d(String tag, Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.d(mTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void i(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.i(mTag + getClassName(), mmsg);
        }
    }

    public static void i(String tag, Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.i(mTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void w(Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.w(mTag + getClassName(), mmsg);
        }
    }

    public static void w(String tag, Object msg) {
        String mmsg = msg != null ? msg.toString() : "obj == null";
        if (IS_DEBUG) {
            Log.w(mTag + getClassName(), tag + ": " + mmsg);
        }
    }

    public static void e(String msg) {
        if (IS_DEBUG) {
            Log.e(mTag + getClassName(), msg);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(mTag + getClassName(), tag + ": " + msg);
        }
    }

    public static void e(String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.e(mTag + getClassName(), msg, e);
        }
        if (e != null) {
            e.printStackTrace();
        }
    }

    public static void printE(String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.e(mTag + getClassName(), msg, e);
            if (e != null) {
                e.printStackTrace();
            }
        }
        msg = msg + "        (##" + getClassName() + "##" + callMethodAndLine() + ")";
        printLog("--ERROR--" + msg);
        printLog("---------------------程序崩溃啦---------------\r\n" + e.getMessage() + "\r\n");
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement stack : stackTrace) {
            printLog(stack.toString());
        }
    }

    /**
     * 打印Log到本地
     */
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

    /**
     * 获取调用Log方法的类名
     */
    private static String getClassName() {
        String result = "";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
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

    /**
     * 判断大小是否超过1M
     */
    public static boolean isFileSizeOutof1M(File file) throws Exception {
        if (file == null) return false;
        return file.length() >= 1048576 * 0.1;
    }

}
