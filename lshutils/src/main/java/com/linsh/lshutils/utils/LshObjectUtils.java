package com.linsh.lshutils.utils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: Object 相关
 * </pre>
 */
public class LshObjectUtils {

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
}
