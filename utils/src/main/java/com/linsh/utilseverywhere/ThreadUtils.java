package com.linsh.utilseverywhere;

import android.os.Process;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 线程相关
 * </pre>
 */
public class ThreadUtils {

    private static int sMainTid;

    private ThreadUtils() {
    }

    static void init() {
        sMainTid = Process.myTid();
    }

    /**
     * 获取当前线程名
     *
     * @return 当前线程名
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * 判断是否为主线程
     *
     * @param tid 当前线程的 thread id
     * @return true 是; false 不是
     */
    public static boolean isMainThread(int tid) {
        return sMainTid == tid;
    }
}
