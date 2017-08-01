package com.linsh.lshutils.utils.Basic;

import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;

import com.linsh.lshutils.utils.LshTimeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Senh Linsh on 17/1/8.
 */
public class LshLogUtils {

    private static boolean sIsDebug = checkDebugMode();
    private static String sTag = "LshLogUtils: ";

    private static Tracer mTracer;
    private static Printer mPrinter;

    public static void init(boolean isDebug) {
        sIsDebug = isDebug;
    }

    public static void init(boolean isDebug, String tag) {
        sIsDebug = isDebug;
        sTag = tag;
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

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    private static boolean checkDebugMode() {
        ApplicationInfo applicationInfo = LshApplicationUtils.getContext().getApplicationInfo();
        boolean debug = applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i("LshLog", "checkDebugMode: " + debug);
        return debug;
    }

    //================================================ 日志追踪相关 ================================================//
    public static Tracer tracer() {
        if (mTracer == null) {
            synchronized (LshLogUtils.class) {
                if (mTracer == null) {
                    mTracer = new Tracer();
                }
            }
        }
        return mTracer;
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
                Log.i(sTag + getClassName(), msg);
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
        if (mPrinter == null) {
            synchronized (LshLogUtils.class) {
                if (mPrinter == null) {
                    mPrinter = new Printer();
                }
            }
        }
        return mPrinter;
    }

    /**
     * Printer 目前还不完善, 没有开子线程加上频繁的 IO 调用对系统的损耗较多
     */
    public static class Printer {

        // 保存本地log文件的目录
        private static String sLogFilePath =
                Environment.getExternalStorageDirectory() + "/" + LshApplicationUtils.getPackageName() + "/LshLog.txt";

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
                Log.i(sTag + getClassName(), msg);
            }
            print(msg);
        }

        public void i(List<String> msgs) {
            if (sIsDebug) {
                for (String msg : msgs) {
                    Log.i(sTag + getClassName(), msg);
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
                Log.e(sTag + getClassName(), msg, thr);
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
            if (!LshFileUtils.checkPermission()) return;

            BufferedWriter bw = null;
            try {
                File file = new File(sLogFilePath);
                if (file.exists()) {
                    // 超过一个星期没有修改, 直接删除
                    if (System.currentTimeMillis() - file.lastModified() > 1000L * 60 * 60 * 24 * 7) {
                        file.delete();
                    }
                } else if (!LshFileUtils.makeParentDirs(file)) {
                    return;
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
