package com.linsh.lshutils.utils_ex;

import java.util.Random;

public class RandomUtils {

    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS             = "0123456789";
    public static final String LETTERS             = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtils() {
        throw new AssertionError();
    }

    /** 获取一个固定长度的随机字符串, 包含：大小写字母、数字 */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /** 获取一个固定长度的随机字符串, 包含：数字 */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /** 获取一个固定长度的随机字符串, 包含：大小写字母 */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /** 获取一个固定长度的随机字符串, 包含：大写字母 */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /** 获取一个固定长度的随机字符串, 包含：小写字母 */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /** 获取一个固定长度的随机字符串, 包含：指定资源字符串的字符 */
    public static String getRandom(String source, int length) {
        return StringUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /** 获取一个固定长度的随机字符串, 包含：指定资源字符数组的字符 */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * 获取0到max的随机数(不包含max)
     * @return <ul>
     *         <li>if max <= 0, return 0</li>
     *         </ul>
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * 获取min到max的随机数(不包含max)
     * @return <ul>
     *         <li>if min > max, return 0</li>
     *         <li>if min == max, return min</li>
     *         </ul>
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /** 随机交换排序Object数组 */
    public static boolean shuffle(Object[] objArray) {
        if (objArray == null) {
            return false;
        }

        return shuffle(objArray, getRandom(objArray.length));
    }

    /**
     * 随机交换n次Object数组内的元素
     * 
     * @param objArray
     * @param shuffleCount 随机交换的次数
     * @return
     */
    public static boolean shuffle(Object[] objArray, int shuffleCount) {
        int length;
        if (objArray == null || shuffleCount < 0 || (length = objArray.length) < shuffleCount) {
            return false;
        }

        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }

    /** 随机交换排列int数组 */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }

        return shuffle(intArray, getRandom(intArray.length));
    }

    /**
     * 随机交换n次int数组内的元素
     * 
     * @param intArray
     * @param shuffleCount 随机交换的次数
     * @return
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }
}
