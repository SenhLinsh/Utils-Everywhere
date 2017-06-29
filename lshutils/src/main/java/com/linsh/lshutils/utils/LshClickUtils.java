package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/6/29.
 */

public class LshClickUtils {

    private static long lastClickTime;

    /**
     * 判断快速点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
