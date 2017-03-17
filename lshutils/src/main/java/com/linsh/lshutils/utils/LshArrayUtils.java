package com.linsh.lshutils.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static <T> List<T> toList(T[] array) {
        if (array == null) return null;

        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    public static void copy(int[] srcArray, int[] destArray) {
        if (srcArray == null) {
            destArray = null;
            return;
        }
        if (destArray == null || destArray.length != srcArray.length) {
            destArray = new int[srcArray.length];
        }
        System.arraycopy(srcArray, 0, destArray, 0, srcArray.length);
    }

    public static int[] getCopy(int[] srcArray) {
        if (srcArray == null) {
            return null;
        }
        return getCopy(srcArray, srcArray.length);
    }

    public static int[] getCopy(int[] srcArray, int newLength) {
        int[] destArray = new int[newLength];

        if (srcArray != null && srcArray.length > 0 && newLength > 0) {
            System.arraycopy(srcArray, 0, destArray, 0, Math.min(srcArray.length, destArray.length));
        }
        return destArray;
    }
}
