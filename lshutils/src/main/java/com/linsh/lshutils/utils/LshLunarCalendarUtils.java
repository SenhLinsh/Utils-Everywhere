package com.linsh.lshutils.utils;

import com.linsh.lshutils.module.SimpleDate;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Senh Linsh on 17/8/22.
 */

public class LshLunarCalendarUtils {

    /**
     * 农历日期字符串 转 Date
     *
     * @param lunarStr 农历日期字符串, 如: 二零零零年正月初一, 腊月廿三, 1992年十一月二十一
     * @return Date 日期格式, 解析失败返回 null
     */
    public static Date lunarStr2Date(String lunarStr) {
        SimpleDate simpleDate = parseLunarStr(lunarStr);
        if (simpleDate != null) {
            return new Date(simpleDate.getYear(), simpleDate.getMonth() - 1, simpleDate.getDay());
        }
        return null;
    }

    /**
     * 农历日期字符串 转 常规日期字符串
     *
     * @param lunarStr 农历日期字符串, 如: 二零零零年正月初一, 腊月廿三, 十一月二十一
     * @return 常规日期字符串, 如: 2000-01-01, 12-23, 11-21
     */
    public static String lunarStr2NormalStr(String lunarStr) {
        if (lunarStr == null) return null;

        SimpleDate simpleDate = parseLunarStr(lunarStr);
        if (simpleDate != null) {
            return simpleDate.getNormalizedString();
        }
        return null;
    }

    public static SimpleDate parseLunarStr(String lunarStr) {
        if (LshStringUtils.isEmpty(lunarStr)) return null;

        Matcher matcher = Pattern.compile("^(((\\d{2,4})|([\\u4e00-\\u9fa5]{2,4}))年)?([\\u4e00-\\u9fa5]{1,2})月([\\u4e00-\\u9fa5]{1,3})日?$").matcher(lunarStr);
        if (matcher.find()) {
            String yearArStr = matcher.group(3);
            String yearCnStr = matcher.group(4);
            String monthStr = matcher.group(5);
            String dayStr = matcher.group(6);

            try {
                int year = 0;
                if (yearArStr != null) {
                    year = Integer.parseInt(yearArStr);
                } else if (yearCnStr != null) {
                    year = LshChineseNumberUtils.parseLunarYear(yearCnStr);
                }
                int month = LshChineseNumberUtils.parseLunarMonth(monthStr);
                int day = LshChineseNumberUtils.parseLunarDay(dayStr);
                return new SimpleDate(year, month, day, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static SimpleDate parseNormalizedStr(String dateStr) {
        if (LshStringUtils.isEmpty(dateStr)) return null;
        Matcher matcher = Pattern.compile("^((\\d{2,4})[年-])?(\\d{1,2})[月-](\\d{1,2})日?$").matcher(dateStr);
        if (matcher.find()) {
            String yearStr = matcher.group(2);
            String monthStr = matcher.group(3);
            String dayStr = matcher.group(4);

            try {
                int year = 0;
                if (yearStr != null) {
                    year = Integer.parseInt(yearStr);
                }
                int month = Integer.parseInt(monthStr);
                int day = Integer.parseInt(dayStr);
                return new SimpleDate(year, month, day, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 常规日期字符串 转 农历日期字符串
     *
     * @param dateStr 常规日期字符串, 如: 2000-01-01, 12-23, 11-21
     * @return 农历日期字符串, 如: 正月初一, 腊月廿三
     */
    public static String normalStr2LunarStr(String dateStr) {
        SimpleDate simpleDate = parseNormalizedStr(dateStr);
        if (simpleDate != null) {
            simpleDate.setLunar(true);
            return simpleDate.getDisplayString(true);
        }
        return null;
    }

    public static String getLunarStr(Date date, boolean hasYear) {
        return getLunarStr(hasYear ? date.getYear() + 1900 : 0, date.getMonth() + 1, date.getDate());
    }

    public static String getLunarStr(int year, int month, int day) {
        StringBuilder builder = new StringBuilder();

        if (year > 0) {
            builder.append(year).append("年");
        }
        builder.append(LshChineseNumberUtils.formatLunarMonth(month)).append("月");
        builder.append(LshChineseNumberUtils.formatLunarDay(day));
        return builder.toString();
    }

    public static String[] getLunarMonths() {
        return getLunarMonths(true);
    }

    public static String[] getLunarMonths(boolean hasMonthChar) {
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            if (hasMonthChar) {
                months[i] = LshChineseNumberUtils.sCnMonths[i] + "月";
            } else {
                months[i] = String.valueOf(LshChineseNumberUtils.sCnMonths[i]);
            }
        }
        return months;
    }

    public static String[] getLunarDays() {
        String[] days = new String[30];
        for (int i = 0; i < 9; i++) {
            days[i] = "初" + LshChineseNumberUtils.sCnNums[i];
        }
        days[9] = "初十";
        for (int i = 10; i < 19; i++) {
            days[i] = "十" + LshChineseNumberUtils.sCnNums[i - 10];
        }
        days[19] = "二十";
        for (int i = 20; i < 29; i++) {
            days[i] = "廿" + LshChineseNumberUtils.sCnNums[i - 20];
        }
        days[29] = "三十";
        return days;
    }
}
