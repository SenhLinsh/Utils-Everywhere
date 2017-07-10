package com.linsh.lshutils.utils;

import java.util.Random;

/**
 * Created by Senh Linsh on 17/7/6.
 */

public class LshRandomUtils {

    public static boolean getBoolean() {
        return new Random().nextBoolean();
    }

    /**
     * 获取 0 到 max 的随机数
     */
    public static int getInt(int max) {
        return new Random().nextInt(max + 1);
    }

    /**
     * 获取 min 到 max 的随机数
     */
    public static int getInt(int min, int max) {
        int diff = max - min;
        if (diff < 0) {
            throw new IllegalArgumentException("max should bigger than min");
        } else if (diff == 0) {
            return min;
        }
        return min + new Random().nextInt(diff + 1);
    }

    public static String getNumberString(int length) {
        String letters = "0123456789";
        return getString(letters, length);
    }

    public static String getLetterString(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return getString(letters, length);
    }

    public static String getNumberAndLetterString(int length) {
        String letters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return getString(letters, length);
    }

    public static String getLowerCaseLetterString(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        return getString(letters, length);
    }

    public static String getUpperCaseLetterString(int length) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return getString(letters, length);
    }

    public static String getString(String source, int length) {
        return getString(source.toCharArray(), length);
    }

    public static String getString(char[] source, int length) {
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(source[random.nextInt(source.length)]);
        }
        return str.toString();
    }
}
