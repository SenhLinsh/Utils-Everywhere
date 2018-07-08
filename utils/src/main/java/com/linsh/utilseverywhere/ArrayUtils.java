package com.linsh.utilseverywhere;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 数组相关
 * </pre>
 */
public class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * 判断数组是否为空
     *
     * @param array 指定的数组
     * @return null 或空数组返回 true; 不为空返回 false
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否所有的数组都为空, 单个数组是否为空见 {@link #isEmpty(T[])}
     *
     * @param arrays 包含多个数组的数组或可变参数
     * @return 所有数组都为空时返回 true; 任意一个数组不为空时返回 false
     */
    public static <T> boolean isAllEmpty(T[]... arrays) {
        for (T[] arr : arrays) {
            if (!isEmpty(arr)) return false;
        }
        return true;
    }

    /**
     * 判断是否任意一个数组为空, 单个数组是否为空见 {@link #isEmpty(T[])}
     *
     * @param arrays 包含多个数组的数组或可变参数
     * @return 任意一个数组为空时返回 true; 其他(所有数组不为空)返回 false
     */
    public static <T> boolean isAnyOneEmpty(T[]... arrays) {
        for (T[] arr : arrays) {
            if (isEmpty(arr)) return true;
        }
        return false;
    }

    /**
     * 拼接数组中的所有元素
     *
     * @param arr     指定的数组
     * @param divider 串联在元素之间的字符串
     * @return 拼接好的字符串
     */
    public static String joint(Object[] arr, String divider) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) {
                builder.append(divider);
            }
            builder.append(arr[i].toString());
        }
        return builder.toString();
    }

    /**
     * 拼接数组中的元素
     *
     * @param arr     指定的数组
     * @param length  需要拼接的元素个数
     * @param divider 串联在元素之间的字符串
     * @return 拼接好的字符串
     */
    public static String joint(Object[] arr, int length, String divider) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Math.min(arr.length, length); i++) {
            if (i != 0) {
                builder.append(divider);
            }
            builder.append(arr[i].toString());
        }
        return builder.toString();
    }

    /**
     * 将数组转化为集合形式<br/>
     * 本质还是数组, 所有无法使用 {@link List#add(Object)}、{@link List#remove(int)}} 等改变集合长度的方法,
     * 使用后会抛出异常<br/>
     * 如果需要改变集合长度等操作, 可使用 {@link #toList(Object[])} 方法, 转成集合
     *
     * @param array 指定的数组
     * @return 转化后得到的集合 (注意不能增删)
     */
    public static <T> List<T> asList(T[] array) {
        return Arrays.asList(array);
    }

    /**
     * 将数组转化成集合
     *
     * @param array 指定的数组
     * @return 转化后得到的集合
     */
    public static <T> List<T> toList(T[] array) {
        if (array == null) return null;

        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    /**
     * 将源数组的元素复制目标数组中<br/>
     * 如果源数组比目标数组长, 源数组超出部分的元素将不被复制;
     * 如果目标数组比源数组长, 目标数组超出部分的原色将不会被改变
     *
     * @param srcArray  源数组; 为 null 将抛出 IllegalArgumentException 异常
     * @param destArray 目标数组; wei null 将抛出 IllegalArgumentException 异常
     */
    public static void copy(int[] srcArray, int[] destArray) {
        if (srcArray == null)
            throw new IllegalArgumentException("srcArray can't be null");
        if (destArray == null)
            throw new IllegalArgumentException("destArray can't be null");
        if (srcArray.length == 0 || destArray.length == 0)
            return;
        System.arraycopy(srcArray, 0, destArray, 0, Math.min(srcArray.length, destArray.length));
    }

    /**
     * 获取数组备份
     *
     * @param srcArray 源数组
     * @return 备份后的数组
     */
    public static int[] getCopy(int[] srcArray) {
        if (srcArray == null) {
            return null;
        }
        return getCopy(srcArray, srcArray.length);
    }

    /**
     * 获取数组备份
     *
     * @param srcArray  源数组
     * @param newLength 需要备份的长度
     * @return 备份后的数组
     */
    public static int[] getCopy(int[] srcArray, int newLength) {
        int[] destArray = new int[newLength];

        if (srcArray != null && srcArray.length > 0 && newLength > 0) {
            System.arraycopy(srcArray, 0, destArray, 0, Math.min(srcArray.length, destArray.length));
        }
        return destArray;
    }

    /**
     * 合并多个源数组到一个数组中
     *
     * @param clazz     数组类型对应的类, 用于自动创建新数组
     * @param srcArrays 源数组
     */
    @SafeVarargs
    public static <T> T[] mergeArrays(Class<T> clazz, T[]... srcArrays) {
        if (srcArrays == null)
            return null;
        int length = 0;
        length += srcArrays.length;
        for (T[] srcArray : srcArrays) {
            length += srcArray.length;
        }
        T[] destArray = (T[]) Array.newInstance(clazz, length);
        if (length > 0) {
            int index = 0;
            for (T[] oldArray : srcArrays) {
                for (T t : oldArray) {
                    destArray[index++] = t;
                }
            }
        }
        return destArray;
    }

    /**
     * 将数组转成 String 字符串形式,如 "[a, b, c]"
     *
     * @param array 数组
     * @return 格式化的数组形式的字符串
     */
    public static String toString(Object[] array) {
        return Arrays.toString(array);
    }

    /**
     * 将基本数据类型数组转成 String 字符串形式,如 "[1, 2, 3]"
     *
     * @param array 基本数据类型数组
     * @return 格式化的数组形式的字符串
     */
    public static String toString(Object array) {
        if (array == null) return "null";
        if (array instanceof int[]) {
            return Arrays.toString((int[]) array);
        } else if (array instanceof long[]) {
            return Arrays.toString((long[]) array);
        } else if (array instanceof short[]) {
            return Arrays.toString((short[]) array);
        } else if (array instanceof char[]) {
            return Arrays.toString((char[]) array);
        } else if (array instanceof byte[]) {
            return Arrays.toString((byte[]) array);
        } else if (array instanceof boolean[]) {
            return Arrays.toString((boolean[]) array);
        } else if (array instanceof float[]) {
            return Arrays.toString((float[]) array);
        } else if (array instanceof double[]) {
            return Arrays.toString((double[]) array);
        } else if (array instanceof Object[]) {
            return Arrays.toString((Object[]) array);
        } else {
            return "not an array";
        }
    }
}
