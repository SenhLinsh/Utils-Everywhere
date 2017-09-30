package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/8/23.
 */

public class LshTestUtils {

    public static void print(Object object) {
        System.err.print(object);
    }

    public static void print(Object... objects) {
        if (objects == null || objects.length == 0) return;
        for (Object object : objects) {
            System.err.print(object + "    ");
        }
    }

    public static void printLn(Object object) {
        System.err.println(object);
    }

    public static void printLn(Object... objects) {
        if (objects == null || objects.length == 0) return;
        for (Object object : objects) {
            System.err.println(object);
        }
    }

    public static long runFunction(Runnable runnable, int times) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            runnable.run();
        }
        return System.currentTimeMillis() - startTime;
    }
}
