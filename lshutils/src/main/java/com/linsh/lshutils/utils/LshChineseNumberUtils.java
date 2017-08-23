package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/8/22.
 */

public class LshChineseNumberUtils {

    private static char[] sCnNums = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};

    public static int parseChar(char chineseNumber) {
        for (int j = 0; j < sCnNums.length; j++) {

            if (sCnNums[j] == chineseNumber) {
                return j + 1;
            }
        }
        switch (chineseNumber) {
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
        throw new NumberFormatException(String.format("无法解析字符 \"%s\"", chineseNumber));
    }

    public static int parseNumber(String chineseNumber) {
        int[] result = new int[5];
        int unit = 1;
        result[0] = 1;

        char zero = '零';
        char wan = '万';
        char[] figures = new char[]{'十', '百', '千'};
        int[] units = new int[]{1, 10, 100, 1000, 10000};

        int methods = 0x1111;
        forNumber:
        for (int i = 0; i < chineseNumber.length(); i++) {
            char cnChar = chineseNumber.charAt(i);

            // 一到九
            if ((methods & 0x0001) == 0x0001) {
                for (int j = 0; j < sCnNums.length; j++) {

                    if (sCnNums[j] == cnChar) {
                        result[0] = j + 1;
                        methods = 0x0110;
                        continue forNumber;
                    }
                }
            }
            // 十百千
            if ((methods & 0x0010) == 0x0010) {
                for (int j = 0; j < figures.length; j++) {
                    if (figures[j] == cnChar) {
                        unit = j + 1;
                        result[unit] += result[0];
                        result[0] = 0;
                        methods = 0x1101;
                        continue forNumber;
                    }
                }
            }
            // 万
            if ((methods & 0x0100) == 0x0100) {
                if (wan == cnChar) {
                    unit = 4;
                    result[unit] = result[unit] * units[unit];
                    for (int k = 0; k < unit; k++) {
                        result[unit] += result[k] * units[k];
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

            String error = i > 0 ? String.format("无法解析字符 \"%s\" 或组合 \"%s\"", cnChar, chineseNumber.substring(i - 1, i + 1))
                    : String.format("无法解析字符 \"%s\"", cnChar);
            throw new NumberFormatException(error);
        }
        return result[4] * units[4] + result[3] * units[3] + result[2] * units[2] + result[1] * units[1] + result[0] * units[unit] / 10;
    }

    public static long parseNumberAsLong(String chineseNumber) {
        long[] result = new long[6];
        int unit = 1;
        result[0] = 1;

        char zero = '零';
        char[] figures = new char[]{'十', '百', '千'};
        char[] bigFigures = new char[]{'万', '亿'};
        long[] units = new long[]{1L, 10L, 100L, 1000L, 10000L, 100000000L};

        int methods = 0x1111;
        forNumber:
        for (int i = 0; i < chineseNumber.length(); i++) {
            char cnChar = chineseNumber.charAt(i);

            // 一到九
            if ((methods & 0x0001) == 0x0001) {
                for (int j = 0; j < sCnNums.length; j++) {

                    if (sCnNums[j] == cnChar) {
                        result[0] = j + 1;
                        methods = 0x0110;
                        continue forNumber;
                    }
                }
            }
            // 十百千
            if ((methods & 0x0010) == 0x0010) {
                for (int j = 0; j < figures.length; j++) {
                    if (figures[j] == cnChar) {
                        unit = j + 1;
                        result[unit] += result[0];
                        result[0] = 0;
                        methods = 0x1101;
                        continue forNumber;
                    }
                }
            }
            // 万亿
            if ((methods & 0x0100) == 0x0100) {
                for (int j = 0; j < bigFigures.length; j++) {

                    if (bigFigures[j] == cnChar) {
                        unit = j + 4;
                        result[unit] = result[unit] * units[unit];
                        for (int k = 0; k < unit; k++) {
                            result[unit] += result[k] * units[k];
                            result[k] = 0;
                        }
                        methods = 0x1101;
                        continue forNumber;
                    }
                }
            }
            // 零
            if ((methods & 0x1000) == 0x1000 && zero == cnChar) {
                result[0] = 0;
                methods = 0x1001;
                continue;
            }

            String error = i > 0 ? String.format("无法解析字符 \"%s\" 或组合 \"%s\"", cnChar, chineseNumber.substring(i - 1, i + 1))
                    : String.format("无法解析字符 \"%s\"", cnChar);
            throw new NumberFormatException(error);
        }
        return result[5] * units[5] + result[4] * units[4] + result[3] * units[3] + result[2] * units[2] + result[1] * units[1] + result[0] * units[unit] / 10;
    }


}
