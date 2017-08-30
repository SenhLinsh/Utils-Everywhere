package com.linsh.lshutils.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Senh Linsh on 17/2/22.
 */

public class LshArrayUtils {

    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

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

    public static <T> List<T> asList(T[] array) {
        return Arrays.asList(array);
    }

    public static <T> List<T> toList(T[] array) {
        if (array == null) return null;

        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    public static <T> void toArray(List<T> src, T[] dest) {
        if (dest == null) {
            return;
        }
        if (src == null) {
            dest = null;
            return;
        }
        for (int i = 0; i < dest.length; i++) {
            dest[i] = i < src.size() ? src.get(i) : null;
        }
    }

    public static int[] toIntArray(List<Integer> list) {
        if (list == null) return null;

        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static String[] toStringArray(List<String> list) {
        if (list == null) return null;
        String[] array = new String[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
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

    @SafeVarargs
    public static <T> void addArrays(T[] newArray, T[]... oldArrays) {
        int index = 0;
        for (T[] oldArray : oldArrays) {
            for (T t : oldArray) {
                if (index >= newArray.length)
                    return;
                newArray[index++] = t;
            }
        }
    }
}
