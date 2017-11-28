package com.linsh.everywhere.utils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 线程相关
 * </pre>
 */
public class ThreadUtils {

    /**
     * 获取当前线程名
     *
     * @return 当前线程名
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}
