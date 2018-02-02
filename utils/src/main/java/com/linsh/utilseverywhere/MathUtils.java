package com.linsh.utilseverywhere;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/02/01
 *    desc   : 工具类: 数学相关
 *             主要是为了扩展 Math 类没有的一些方法
 * </pre>
 */
public class MathUtils {

    public static int limit(int src, int max, int min) {
        if (src >= max) return max;
        if (src < min) return min;
        return src;
    }

    public static long limit(long src, long max, long min) {
        if (src >= max) return max;
        if (src < min) return min;
        return src;
    }

    public static float limit(float src, float max, float min) {
        if (src >= max) return max;
        if (src < min) return min;
        return src;
    }

    public static double limit(double src, double max, double min) {
        if (src >= max) return max;
        if (src < min) return min;
        return src;
    }
}
