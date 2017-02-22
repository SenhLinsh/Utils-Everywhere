package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/2/22.
 */

public class LshArrayUtils {

    public static String joint(Object[] arr, String divider) {
        String joint = "";
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) {
                joint += divider;
            }
            joint += arr[i].toString();
        }
        return joint;
    }

    public static String joint(Object[] arr, int length, String divider) {
        String joint = "";
        for (int i = 0; i < Math.min(arr.length, length); i++) {
            if (i != 0) {
                joint += divider;
            }
            joint += arr[i].toString();
        }
        return joint;
    }
}
