package com.linsh.utilseverywhere;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 工具类: 单位转换相关
 * </pre>
 */
public class UnitConverseUtils {

    private UnitConverseUtils() {
    }

    /**
     * dp 转 px
     *
     * @param dp dp 值
     * @return px 值
     */
    public static int dp2px(int dp) {
        float density = ContextUtils.get().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * dp 转 px
     *
     * @param dp dp 值
     * @return px 值
     */
    public static int dp2px(float dp) {
        float density = ContextUtils.get().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param px px 值
     * @return dp 值
     */
    public static int px2dp(int px) {
        float density = ContextUtils.get().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param px px 值
     * @return dp 值
     */
    public static int px2sp(float px) {
        final float fontScale = ContextUtils.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param sp sp 值
     * @return px 值
     */
    public static int sp2px(float sp) {
        final float fontScale = ContextUtils.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    /**
     * 十进制数值 转 十六进制字符串
     *
     * @param value 十进制数值
     * @return 十六进制字符串
     */
    public static String toHexString(int value) {
        return Integer.toHexString(value);
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * byte 数组 转 十六进制字符串
     *
     * @param bytes byte 数组
     * @return 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
