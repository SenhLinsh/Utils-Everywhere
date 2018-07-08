package com.linsh.utilseverywhere;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.util.Arrays;
import java.util.Iterator;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 打印相关
 * </pre>
 */
public class LogUtils {

    private LogUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 是否为 Debug 模式, true 打印日志, false 关闭日志
     */
    private static boolean sIsDebug = checkDebugMode();
    /**
     * 打印时的 TAG
     */
    private static String sTag = LogUtils.class.getSimpleName() + ": ";

    /**
     * 初始化, 可自定义是否打印 Log
     *
     * @param isDebug 是否为 Debug 模式, true 打印日志, false 关闭日志
     */
    public static void init(boolean isDebug) {
        sIsDebug = isDebug;
    }

    /**
     * 初始化, 可自定义是否打印 Log 和 Tag
     *
     * @param isDebug 是否为 Debug 模式, true 打印日志, false 关闭日志
     * @param tag     TAG 标签
     */
    public static void init(boolean isDebug, String tag) {
        sIsDebug = isDebug;
        sTag = tag + " : ";
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void v(String msg) {
        if (sIsDebug) {
            log(Log.VERBOSE, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void v(Object msg) {
        if (sIsDebug) {
            checkAndLog(Log.VERBOSE, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void v(Object... array) {
        if (sIsDebug) {
            log(Log.VERBOSE, sTag + getClassName(), Arrays.toString(array));
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void d(String msg) {
        if (sIsDebug) {
            log(Log.DEBUG, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void d(Object msg) {
        if (sIsDebug) {
            checkAndLog(Log.DEBUG, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void d(Object... array) {
        if (sIsDebug) {
            log(Log.DEBUG, sTag + getClassName(), Arrays.toString(array));
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void i(String msg) {
        if (sIsDebug) {
            log(Log.INFO, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果打印信息为可迭代对象或数组, 将以数组表示形式打印子元素</p>
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void i(Object msg) {
        if (sIsDebug) {
            checkAndLog(Log.INFO, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void i(Object... array) {
        if (sIsDebug) {
            log(Log.INFO, sTag + getClassName(), Arrays.toString(array));
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void w(String msg) {
        if (sIsDebug) {
            log(Log.WARN, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果打印信息为可迭代对象或数组, 将以数组表示形式打印子元素</p>
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void w(Object msg) {
        if (sIsDebug) {
            checkAndLog(Log.WARN, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void w(Object... array) {
        if (sIsDebug) {
            log(Log.WARN, sTag + getClassName(), Arrays.toString(array));
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void e(String msg) {
        if (sIsDebug) {
            log(Log.ERROR, sTag + getClassName(), msg);
        }
    }

    /**
     * 打印日志
     * <p>如果打印信息为可迭代对象或数组, 将以数组表示形式打印子元素</p>
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void e(String... array) {
        if (sIsDebug) {
            log(Log.ERROR, sTag + getClassName(), Arrays.toString(array));
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void e(Throwable thr) {
        if (sIsDebug) {
            Log.e(sTag + getClassName(), "", thr);
        }
    }

    /**
     * 打印日志
     * <p>如果需要设置 Tag, 请在初始化时设置指定的 Tag, 见 {@link #init(boolean, String)}</p>
     */
    public static void e(String msg, Throwable thr) {
        if (sIsDebug) {
            Log.e(sTag + getClassName(), msg, thr);
        }
    }

    /**
     * 检查打印信息并打印, 如果打印信息为可迭代对象或数组, 则将以集合形式打印所有子元素
     *
     * @param priority 打印等级
     * @param tag      Tag
     * @param msg      打印信息
     */
    private static void checkAndLog(int priority, String tag, Object msg) {
        log(priority, tag, toString(msg));
    }

    /**
     * 执行 Log 打印
     *
     * @param priority 打印等级
     * @param tag      Tag
     * @param msg      打印信息
     */
    private static void log(int priority, String tag, Object msg) {
        Log.println(priority, tag, msg != null ? msg.toString() : "null");
    }

    /**
     * 获取可抛异常对象的堆栈信息
     *
     * @param thr 可抛异常
     * @return 堆栈信息
     */
    public static String getStackTraceString(Throwable thr) {
        return Log.getStackTraceString(thr);
    }

    public static boolean isDebugMode() {
        return sIsDebug;
    }

    /**
     * 自动检查当前是否为 Debug 模式
     */
    private static boolean checkDebugMode() {
        ApplicationInfo applicationInfo = getContext().getApplicationInfo();
        boolean debug = applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i("LshLog", "checkDebugMode: " + debug);
        return debug;
    }

    /**
     * 获取调用 Log 方法的类名
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

    private static String toString(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (obj instanceof Object[]) {
            return Arrays.toString((Object[]) obj);
        } else if (obj instanceof Iterable) {
            return toString((Iterable) obj);
        } else {
            return obj.toString();
        }
    }

    private static String toString(Iterable iterable) {
        Iterator iterator = iterable.iterator();
        if (!iterator.hasNext()) return "[]";
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (; ; ) {
            Object element = iterator.next();
            builder.append(element);
            if (!iterator.hasNext())
                return builder.append(']').toString();
            builder.append(',').append(' ');
        }
    }
}
