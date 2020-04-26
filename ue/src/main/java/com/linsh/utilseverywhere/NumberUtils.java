package com.linsh.utilseverywhere;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/04/26
 *    desc   : 数字相关
 * </pre>
 */
public final class NumberUtils {

    private NumberUtils() {
    }

    /**
     * 格式化小数数值
     *
     * @param num           数值
     * @param decimalPlaces 小数位数
     */
    public static String format(final double num, final int decimalPlaces) {
        return format(num, decimalPlaces, true, true);
    }

    /**
     * 格式化小数数值
     *
     * @param num           数值
     * @param decimalPlaces 小数位数
     * @param round         是否四舍五入, 如果为 false 则去掉多余位数
     */
    public static String format(final double num, final int decimalPlaces, final boolean round) {
        return format(num, decimalPlaces, round, true);
    }


    /**
     * 格式化小数数值
     *
     * @param num           数值
     * @param decimalPlaces 小数位数
     * @param round         是否四舍五入, 如果为 false 则去掉多余位数
     * @param fixZero       如果没数不足, 是否补零
     */
    public static String format(final double num, final int decimalPlaces, final boolean round, final boolean fixZero) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("decimalPlaces can not ne negative");
        }
        double decimal = Math.abs(num);
        StringBuilder result = new StringBuilder();
        if (num < 0) {
            result.append('-');
        }
        result.append((int) decimal);
        if (fixZero || decimal % 1 > 0) {
            if (decimalPlaces > 0) {
                result.append('.');
                int count = decimalPlaces;
                while (decimal % 1 > 0 || fixZero) {
                    result.append((int) ((decimal *= 10) % 10));
                    if (--count == 0) {
                        break;
                    }
                }
            }
            // 处理四舍五入
            if (round && (decimal % 1) >= 0.5) {
                boolean overflow = false;
                for (int i = result.length() - 1; i >= 0; i--) {
                    char c = result.charAt(i);
                    if (c >= '0' && c < '9') {
                        result.setCharAt(i, (char) (c + 1));
                        overflow = false;
                        break;
                    } else if (c == '9') {
                        result.setCharAt(i, '0');
                        overflow = true;
                    }
                }
                if (overflow) {
                    if (result.charAt(0) == '-') {
                        result.insert(1, 1);
                    } else {
                        result.insert(0, 1);
                    }
                }
            }
            if (decimalPlaces > 0) {
                int i;
                // 处理结尾多余的零
                if (!fixZero) {
                    while ((result.charAt(i = result.length() - 1)) == '0') {
                        result.deleteCharAt(i);
                    }
                }
                // 处理结尾多余的小数点
                if (result.charAt(i = result.length() - 1) == '.') {
                    result.deleteCharAt(i);
                }
            }
        }
        return result.toString();
    }
}
