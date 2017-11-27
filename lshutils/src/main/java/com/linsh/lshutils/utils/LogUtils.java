package com.linsh.lshutils.utils;

import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 打印相关
 * </pre>
 */
public class LogUtils {

    /**
     * 是否为 Debug 模式, true 打印日志, false 关闭日志
     */
    private static boolean sIsDebug = checkDebugMode();
    /**
     * 打印时的 TAG
     */
    private static String sTag = LogUtils.class.getSimpleName() + ": ";

    private static Tracer sTracer;
    private static Printer sPrinter;

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
            log(Log.VERBOSE, sTag + getClassName(), ArrayUtils.toString(array));
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
            log(Log.DEBUG, sTag + getClassName(), ArrayUtils.toString(array));
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
            log(Log.INFO, sTag + getClassName(), ArrayUtils.toString(array));
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
            log(Log.WARN, sTag + getClassName(), ArrayUtils.toString(array));
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
            log(Log.ERROR, sTag + getClassName(), ArrayUtils.toString(array));
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
        if (msg instanceof Iterable) {
            log(priority, tag, ListUtils.toString((Iterable) msg));
        } else if (msg.getClass().isArray()) {
            log(priority, tag, ArrayUtils.toString(msg));
        } else {
            log(priority, tag, msg);
        }
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

    /**
     * 自动检查当前是否为 Debug 模式
     */
    private static boolean checkDebugMode() {
        ApplicationInfo applicationInfo = ApplicationUtils.getContext().getApplicationInfo();
        boolean debug = applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i("LshLog", "checkDebugMode: " + debug);
        return debug;
    }

    //================================================ 日志追踪相关 ================================================//
    public static Tracer tracer() {
        if (sTracer == null) {
            synchronized (LogUtils.class) {
                if (sTracer == null) {
                    sTracer = new Tracer();
                }
            }
        }
        return sTracer;
    }

    public static class Tracer {
        private static int mainCount = 10;
        private static int maxCount = 20;
        private SoftReference<LinkedList<String>> extraTracesReference;
        private LinkedList<String> traces;

        private Tracer() {
            traces = new LinkedList<>();
            extraTracesReference = new SoftReference<>(new LinkedList<String>());
        }

        public static void init(int mainCount, int maxCount) {
            Tracer.mainCount = mainCount;
            Tracer.maxCount = maxCount;
        }

        public void i(String msg) {
            if (sIsDebug) {
                LogUtils.i(msg);
            }
            traces.add(msg);
            while (traces.size() > 10) {
                String first = traces.removeFirst();
                LinkedList<String> extraTraces = extraTracesReference.get();
                if (extraTraces == null) {
                    extraTraces = new LinkedList<>();
                    extraTracesReference = new SoftReference<>(extraTraces);
                }
                extraTraces.add(first);
                while (extraTraces.size() > 10) {
                    extraTraces.removeFirst();
                }
            }
        }

        public List<String> getTraces() {
            LinkedList<String> list = new LinkedList<>();
            list.addAll(traces);
            LinkedList<String> extraTraces = extraTracesReference.get();
            if (extraTraces != null) {
                list.addAll(extraTraces);
            }
            return list;
        }
    }

    //================================================ 打印日志到本地相关 ================================================//
    public static Printer printer() {
        if (sPrinter == null) {
            synchronized (LogUtils.class) {
                if (sPrinter == null) {
                    sPrinter = new Printer();
                }
            }
        }
        return sPrinter;
    }

    /**
     * Printer 目前还不完善, 没有开子线程加上频繁的 IO 调用对系统的损耗较多
     */
    public static class Printer {

        // 保存本地log文件的目录
        private static String sLogFilePath =
                Environment.getExternalStorageDirectory() + "/" + ApplicationUtils.getPackageName() + "/LshLog.txt";

        private Printer() {
        }

        public static void setLogFilePath(File filePath) {
            setLogFilePath(filePath.toString());
        }

        public static void setLogFilePath(String filePath) {
            sLogFilePath = filePath;
        }

        public void i(String msg) {
            if (sIsDebug) {
                LogUtils.i(msg);
            }
            print(msg);
        }

        public void i(List<String> msgs) {
            if (sIsDebug) {
                for (String msg : msgs) {
                    LogUtils.i(msg);
                }
            }
            print(msgs);
        }

        public void e(Throwable thr) {
            e(null, thr);
        }

        public void e(String msg, Throwable thr) {
            msg = msg == null ? "msg = null" : msg;
            if (sIsDebug) {
                LogUtils.e(msg, thr);
            }
            List<String> logs = new ArrayList<>();
            logs.add("  ---------------------Throw an ERROR----------------  ");
            if (thr == null) {
                logs.add(msg);
            } else {
                logs.add(msg + " (##" + getClassName() + "##" + callMethodAndLine() + ")");
                logs.add("Message = " + thr.getMessage());
                StackTraceElement[] stackTrace = thr.getStackTrace();
                for (StackTraceElement stack : stackTrace) {
                    logs.add(stack.toString());
                }
                logs.add("\r\n");
            }
            print(logs);
        }

        private static void print(String... logs) {
            print(Arrays.asList(logs));
        }

        private static void print(List<String> logs) {
            if (!FileUtils.checkPermission()) return;

            BufferedWriter bw = null;
            try {
                File file = new File(sLogFilePath);
                if (file.exists()) {
                    // 超过一个星期没有修改, 直接删除
                    if (System.currentTimeMillis() - file.lastModified() > 1000L * 60 * 60 * 24 * 7) {
                        file.delete();
                    }
                } else if (!FileUtils.makeParentDirs(file)) {
                    return;
                }

                String curTime = "[" + TimeUtils.getCurrentTimeStringEN() + "] ";
                bw = new BufferedWriter(new FileWriter(file, true));
                for (String msg : logs) {
                    bw.append(curTime).append(msg);
                    bw.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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

    /**
     * 获取调用 Log 方法的方法和行数
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
