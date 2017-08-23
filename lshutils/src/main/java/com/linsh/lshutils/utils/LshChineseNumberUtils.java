package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/8/22.
 */

public class LshChineseNumberUtils {

    private static char[] sCnNums = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};

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

}
