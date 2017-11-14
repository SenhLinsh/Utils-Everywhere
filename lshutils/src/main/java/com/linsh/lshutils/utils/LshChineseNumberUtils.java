package com.linsh.lshutils.utils;

import android.support.annotation.IntRange;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 中文数字相关
 * </pre>
 */
public class LshChineseNumberUtils {

    static final char[] sCnNums = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};
    static final char[] sCnMonths = {'正', '二', '三', '四', '五', '六', '七', '八', '九', '十', '冬', '腊'};

    /**
     * 解析单个常规中文数字字符, 如: 零一...十百千万亿
     * <p>不包含大写中文数字壹到玖以及廿/卅/腊/冬数字</p>
     *
     * @param cnChar 常规中文数字字符
     * @return int 数值
     */
    public static int parseChar(char cnChar) {
        for (int j = 0; j < sCnNums.length; j++) {
            if (sCnNums[j] == cnChar) {
                return j + 1;
            }
        }
        switch (cnChar) {
            case '零':
                return 0;
            case '十':
                return 10;
            case '百':
                return 100;
            case '千':
                return 1000;
            case '万':
                return 10000;
            case '亿':
                return 100000000;
        }
        throw new NumberFormatException(String.format("无法解析字符 \"%s\"", cnChar));
    }

    /**
     * 将 0 - 10 的数字格式化为中文数字字符
     *
     * @param number 0 - 10 的数字
     */
    public static char formatChar(int number) {
        if (number < 0 || number > 10) {
            throw new NumberFormatException("不接收 0 - 10 以外的数字");
        }
        if (number == 0) {
            return '零';
        }
        if (number == 10) {
            return '十';
        }
        return sCnNums[number - 1];
    }

    /**
     * 解析不带单位的中文数字, 如: 一九九二, 二零零二, 二〇〇二
     *
     * @param cnNumber 不带单位的中文数字
     * @return int 数值
     */
    public static int parseNumberWithoutUnit(String cnNumber) {
        int result = 0;

        outside:
        for (int i = 0; i < cnNumber.length(); i++) {
            char cnChar = cnNumber.charAt(i);

            for (int j = 0; j < sCnNums.length; j++) {
                if (sCnNums[j] == cnChar) {
                    result = result * 10 + (j + 1);
                    continue outside;
                }
            }
            if ('零' == cnChar || '〇' == cnChar) {
                result *= 10;
                continue;
            }
            throw new NumberFormatException(String.format("无法解析字符 \"%s\"", cnChar));
        }
        return result;
    }

    /**
     * 格式化数字为不带单位的中文数字, 如: 二零二三四五
     *
     * @param number long 数值
     * @return 不带单位的中文数字表示
     */
    public static String formatNumberWithoutUnit(long number) {
        StringBuilder builder = new StringBuilder();
        while (number > 0) {
            builder.insert(0, formatChar((int) (number % 10)));
            number /= 10;
        }
        return builder.toString();
    }

    /**
     * 解析中文数字, 如: 一万八千零八(18008), 一百八(180), 十八(18)
     *
     * @param cnNumber 中文数字, 一亿以内
     * @return int 数值
     */
    public static int parseNumber(String cnNumber) {
        int[] result = new int[5];
        int unit = 1;
        result[0] = 1;

        char zero = '零';
        char wan = '万';
        char[] units = new char[]{'十', '百', '千'};
        int[] figures = new int[]{1, 10, 100, 1000, 10000};

        int methods = 0x1111;
        outside:
        for (int i = 0; i < cnNumber.length(); i++) {
            char cnChar = cnNumber.charAt(i);

            // 一到九
            if ((methods & 0x0001) == 0x0001) {
                for (int j = 0; j < sCnNums.length; j++) {

                    if (sCnNums[j] == cnChar) {
                        result[0] = j + 1;
                        methods = 0x0110;
                        continue outside;
                    }
                }
            }
            // 十百千
            if ((methods & 0x0010) == 0x0010) {
                for (int j = 0; j < units.length; j++) {
                    if (units[j] == cnChar) {
                        unit = j + 1;
                        result[unit] += result[0];
                        result[0] = 0;
                        methods = 0x1101;
                        continue outside;
                    }
                }
            }
            // 万
            if ((methods & 0x0100) == 0x0100) {
                if (wan == cnChar) {
                    unit = 4;
                    result[unit] = result[unit] * figures[unit];
                    for (int k = 0; k < unit; k++) {
                        result[unit] += result[k] * figures[k];
                        result[k] = 0;
                    }
                    methods = 0x1101;
                    continue;
                }
            }
            // 零
            if ((methods & 0x1000) == 0x1000 && zero == cnChar) {
                result[0] = 0;
                methods = 0x1001;
                continue;
            }

            String error = i > 0 ? String.format("无法解析字符 \"%s\" 或组合 \"%s\"", cnChar, cnNumber.substring(i - 1, i + 1))
                    : String.format("无法解析字符 \"%s\"", cnChar);
            throw new NumberFormatException(error);
        }
        return result[4] * figures[4] + result[3] * figures[3] + result[2] * figures[2] + result[1] * figures[1] + result[0] * figures[unit] / 10;
    }

    /**
     * 解析中文数字, 如: 一万八千零八(18008), 一百八(180), 十八(18)
     *
     * @param cnNumber 中文数字, 接受一亿及以上的大数字
     * @return long 数值
     */
    public static long parseNumberAsLong(String cnNumber) {
        long[] result = new long[6];
        int unit = 1;
        result[0] = 1;

        char zero = '零';
        char[] units = new char[]{'十', '百', '千'};
        char[] bigUnits = new char[]{'万', '亿'};
        long[] figures = new long[]{1L, 10L, 100L, 1000L, 10000L, 100000000L};

        int methods = 0x1111;
        outside:
        for (int i = 0; i < cnNumber.length(); i++) {
            char cnChar = cnNumber.charAt(i);

            // 一到九
            if ((methods & 0x0001) == 0x0001) {
                for (int j = 0; j < sCnNums.length; j++) {

                    if (sCnNums[j] == cnChar) {
                        result[0] = j + 1;
                        methods = 0x0110;
                        continue outside;
                    }
                }
            }
            // 十百千
            if ((methods & 0x0010) == 0x0010) {
                for (int j = 0; j < units.length; j++) {
                    if (units[j] == cnChar) {
                        unit = j + 1;
                        result[unit] += result[0];
                        result[0] = 0;
                        methods = 0x1101;
                        continue outside;
                    }
                }
            }
            // 万亿
            if ((methods & 0x0100) == 0x0100) {
                for (int j = 0; j < bigUnits.length; j++) {

                    if (bigUnits[j] == cnChar) {
                        unit = j + 4;
                        result[unit] = result[unit] * figures[unit];
                        for (int k = 0; k < unit; k++) {
                            result[unit] += result[k] * figures[k];
                            result[k] = 0;
                        }
                        methods = 0x1101;
                        continue outside;
                    }
                }
            }
            // 零
            if ((methods & 0x1000) == 0x1000 && zero == cnChar) {
                result[0] = 0;
                methods = 0x1001;
                continue;
            }

            String error = i > 0 ? String.format("无法解析字符 \"%s\" 或组合 \"%s\"", cnChar, cnNumber.substring(i - 1, i + 1))
                    : String.format("无法解析字符 \"%s\"", cnChar);
            throw new NumberFormatException(error);
        }
        return result[5] * figures[5] + result[4] * figures[4] + result[3] * figures[3] + result[2] * figures[2] + result[1] * figures[1] + result[0] * figures[unit] / 10;
    }

    /**
     * 解析农历年份, 例: 一九九二(1992), 九二年(1992), 零八(2008), 一七年(2017), 二零(1920)
     *
     * @param lunarYear 农历年份, 简写时默认为过去的年份
     * @return int 数值
     */
    public static int parseLunarYear(String lunarYear) {
        String year = lunarYear;
        if (year.charAt(year.length() - 1) == '年') {
            year = year.substring(0, year.length() - 1);
        }
        if (year.length() > 4 || year.length() == 0) {
            throw new NumberFormatException("无法解析输入的年份 \"" + lunarYear + "\"");
        }
        int result = parseNumberWithoutUnit(year);
        if (year.length() <= 2) {
            if (result <= 17) {
                result += 2000;
            } else {
                result += 1900;
            }
        }
        return result;
    }

    /**
     * 解析农历月份, 例: 一月(1), 正月(1), 二(2), 冬(11), 腊月(12)等
     *
     * @param lunarMonth 农历月份, 末尾可带可不带 '月' 字
     * @return int 数值
     */
    public static int parseLunarMonth(String lunarMonth) {
        if (lunarMonth.charAt(lunarMonth.length() - 1) == '月') {
            lunarMonth = lunarMonth.substring(0, lunarMonth.length() - 1);
        }
        switch (lunarMonth) {
            case "一":
            case "正":
                return 1;
            case "二":
                return 2;
            case "三":
                return 3;
            case "四":
                return 4;
            case "五":
                return 5;
            case "六":
                return 6;
            case "七":
                return 7;
            case "八":
                return 8;
            case "九":
                return 9;
            case "十":
                return 10;
            case "十一":
            case "冬":
                return 11;
            case "十二":
            case "腊":
                return 12;
        }
        return 0;
    }

    /**
     * 格式化数字为农历月份
     *
     * @param month 1 - 12 代表月份
     * @return 农历月份字符
     */
    public static char formatLunarMonth(int month) {
        int index = month % 12 - 1;
        if (index >= 0) {
            return sCnMonths[index];
        }
        return sCnMonths[12 + index];
    }

    /**
     * 解析农历日期中的日, 如: 初一(1), 一(1), 十三(13), 廿二(22), 二十二(22), 卅(30), 三十(30)
     *
     * @param lunarDay 农历日期中的日, 末尾可带可不带 '日' 字
     * @return int 数值
     */
    public static int parseLunarDay(String lunarDay) {
        if (lunarDay.charAt(lunarDay.length() - 1) == '日') {
            lunarDay = lunarDay.substring(0, lunarDay.length() - 1);
        }
        int result = 0;
        int temp = 0;
        int methods = 0x111;
        outside:
        for (int i = 0; i < lunarDay.length(); i++) {
            char lunarChar = lunarDay.charAt(i);

            if ((methods & 0x001) == 0x001) {
                if ('初' == lunarChar) {
                    methods = 0x110;
                    continue;
                }
                if ('廿' == lunarChar) {
                    result = 20;
                    methods = 0x100;
                    continue;
                }
                if ('卅' == lunarChar) {
                    result = 30;
                    methods = 0x100;
                    continue;
                }
            }

            if ((methods & 0x010) == 0x010 && '十' == lunarChar) {
                result = temp == 0 ? 10 : temp * 10;
                temp = 0;
                methods = 0x100;
                continue;
            }

            if ((methods & 0x100) == 0x100) {
                for (int j = 0; j < sCnNums.length; j++) {
                    if (sCnNums[j] == lunarChar) {
                        temp = j + 1;
                        methods = 0x010;
                        continue outside;
                    }
                }
            }

            String error = i > 0 ? String.format("无法解析字符 \"%s\" 或组合 \"%s\"", lunarChar, lunarDay.substring(i - 1, i + 1))
                    : String.format("无法解析字符 \"%s\"", lunarChar);
            throw new NumberFormatException(error);
        }
        return result + temp;
    }

    /**
     * 格式化数字为农历日份, 如 1 -> 初一、 20 -> 二十
     *
     * @param day 农历日份数值
     * @return 字符串表示的农历日份
     */
    public static String formatLunarDay(@IntRange(from = 1, to = 31) int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("无法接收 1 - 31 以外的数值");
        }
        if (day == 10) {
            return "初十";
        } else if (day == 20) {
            return "二十";
        } else if (day == 30) {
            return "三十";
        } else {
            char[] cnFigures = {'初', '十', '廿', '卅'};
            return String.valueOf(cnFigures[day / 10]) + String.valueOf(sCnNums[day % 10 - 1]);
        }
    }

}
