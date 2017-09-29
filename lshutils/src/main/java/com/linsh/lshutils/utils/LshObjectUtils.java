package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/9/20.
 */

public class LshObjectUtils {

    public static boolean isAllNull(Object... objects) {
        for (Object object : objects) {
            if (object != null) return false;
        }
        return true;
    }

    public static boolean isAllNotNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) return false;
        }
        return true;
    }

    public static boolean isNotAllNull(Object... objects) {
        return !isAllNull(objects);
    }

    public static boolean isAnyOneNull(Object... objects) {
        return !isAllNotNull(objects);
    }
}
