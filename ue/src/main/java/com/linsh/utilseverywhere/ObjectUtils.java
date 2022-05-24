package com.linsh.utilseverywhere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: Object 相关
 * </pre>
 */
public class ObjectUtils {

    private ObjectUtils() {
    }

    /**
     * 判断所有 Object 是否都为 null
     *
     * @param objects Object 数组或可变参数
     * @return 都为 null 返回 true, 否则返回 false
     */
    public static boolean isAllNull(Object... objects) {
        for (Object object : objects) {
            if (object != null) return false;
        }
        return true;
    }

    /**
     * 判断所有 Object 是否都不为 null
     *
     * @param objects Object 数组或可变参数
     * @return 都不为 null 返回 true, 否则返回 false
     */
    public static boolean isAllNotNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) return false;
        }
        return true;
    }

    /**
     * 判断是否任意一个 Object 不为 null
     *
     * @param objects Object 数组或可变参数
     * @return 任意一个 Object 不为 null 返回 true, 否则返回 false
     */
    public static boolean isAnyOneNotNull(Object... objects) {
        return !isAllNull(objects);
    }

    /**
     * 判断是否任意一个 Object 为 null
     *
     * @param objects Object 数组或可变参数
     * @return 任意一个 Object 为 null 返回 true, 否则返回 false
     */
    public static boolean isAnyOneNull(Object... objects) {
        return !isAllNotNull(objects);
    }

    /**
     * 检查对象为空时, 返回默认值
     *
     * @param value        检查值
     * @param defaultValue 默认值
     */
    public static <T> T nullToDefault(@Nullable T value, @Nullable T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 从对象列表中取首个不为空的值
     *
     * @param values 检查值列表
     */
    public static <T> T firstNotNull(T... values) {
        for (T value : values) {
            if (value != null)
                return value;
        }
        return null;
    }

    /**
     * 判断两个对象是否想等
     *
     * @param o1 对象1
     * @param o2 对象2
     * @return 是否想等
     */
    public static boolean isEqual(@Nullable Object o1, @Nullable Object o2) {
        if (o1 == null)
            return o2 == null;
        return o1.equals(o2);
    }

    /**
     * 检查对象是否为空, 如果不为空则返回, 如果为空则抛异常
     *
     * @param object 被检查对象
     */
    public static <T> T checkNotNull(@Nullable T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    /**
     * 检查对象是否为空, 如果不为空则返回, 如果为空则抛异常
     *
     * @param object  被检查对象
     * @param message 异常信息
     */
    public static <T> T checkNotNull(@Nullable T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 转 Object 对象默认的字符串
     *
     * @param obj 对象
     * @return 转字符串
     */
    public static String toString(@NonNull Object obj) {
        return obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
    }
}
