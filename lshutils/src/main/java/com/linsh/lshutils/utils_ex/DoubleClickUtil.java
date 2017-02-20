package com.linsh.lshutils.utils_ex;

/**
 * Created by jian on 16/9/6.
 */
public class DoubleClickUtil {
    private static final long exitInterval = 2000;
    private static long lastTryTime;

    public static boolean clickAndReturnIsDoubleClick() {
        boolean result;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTryTime < exitInterval) {
            result = true;
        } else {
            result = false;
        }
        lastTryTime = currentTime;
        return result;
    }
}
