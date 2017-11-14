package com.linsh.lshutils.utils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 工具类: 单位转换相关
 * </pre>
 */
public class LshUnitConverseUtils {

    /**
     * dp 转 px
     *
     * @param dp dp 值
     * @return px 值
     */
    public static int dp2px(int dp) {
        float density = LshApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * dp 转 px
     *
     * @param dp dp 值
     * @return px 值
     */
    public static int dp2px(float dp) {
        float density = LshApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param px px 值
     * @return dp 值
     */
    public static int px2dp(int px) {
        float density = LshApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param px px 值
     * @return dp 值
     */
    public static int px2sp(float px) {
        final float fontScale = LshApplicationUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param sp sp 值
     * @return px 值
     */
    public static int sp2px(float sp) {
        final float fontScale = LshApplicationUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    /**
     * 十进制数值 转 十六进制字符串
     *
     * @param value 十进制数值
     * @return 十六进制字符串
     */
    public static String toHexString(int value) {
        if (value < 0) return null;

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder builder = new StringBuilder();
        do {
            builder.insert(0, chars[value % 16]);
            value /= 16;
        } while (value > 0);
        return builder.toString();
    }
}
