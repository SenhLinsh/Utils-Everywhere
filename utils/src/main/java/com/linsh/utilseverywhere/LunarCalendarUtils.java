package com.linsh.utilseverywhere;

import android.support.annotation.IntRange;

import com.linsh.utilseverywhere.module.SimpleDate;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 农历日期相关
 * </pre>
 */
public class LunarCalendarUtils {

    private LunarCalendarUtils() {
    }

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

    /**
     * 解析农历日期字符串
     *
     * @param lunarStr 农历日期字符串
     * @return SimpleDate 表示的日期
     */
    public static SimpleDate parseLunarStr(String lunarStr) {
        if (StringUtils.isEmpty(lunarStr)) return null;

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
                    year = ChineseNumberUtils.parseLunarYear(yearCnStr);
                }
                int month = ChineseNumberUtils.parseLunarMonth(monthStr);
                int day = ChineseNumberUtils.parseLunarDay(dayStr);
                return new SimpleDate(year, month, day, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析常规日期字符串, 如 "2000-01-01" 或 "2000年1月1日"
     *
     * @param dateStr 常规日期字符串
     * @return SimpleDate 表示的日期
     */
    public static SimpleDate parseNormalizedStr(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) return null;
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

    /**
     * 获取农历日期字符串
     *
     * @param date    日期
     * @param hasYear 是否显示年份
     * @return 农历日期字符串
     */
    public static String getLunarStr(Date date, boolean hasYear) {
        return getLunarStr(hasYear ? date.getYear() + 1900 : 0, date.getMonth() + 1, date.getDate());
    }

    /**
     * 获取农历日期字符串
     *
     * @param year  年份, 如果年份不大于0, 将不显示年份
     * @param month 月份
     * @param day   日份
     * @return
     */
    public static String getLunarStr(int year, @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day) {
        StringBuilder builder = new StringBuilder();

        if (year > 0) {
            builder.append(year).append("年");
        }
        builder.append(ChineseNumberUtils.formatLunarMonth(month)).append("月");
        builder.append(ChineseNumberUtils.formatLunarDay(day));
        return builder.toString();
    }

    /**
     * 获取农历月份的字符串数组
     *
     * @return 农历月份的字符串数组
     */
    public static String[] getLunarMonths() {
        return getLunarMonths(true);
    }

    /**
     * 获取农历月份的字符串数组
     *
     * @param hasMonthChar 是否包含字符'月'字
     * @return 农历月份的字符串数组
     */
    public static String[] getLunarMonths(boolean hasMonthChar) {
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            if (hasMonthChar) {
                months[i] = ChineseNumberUtils.sCnMonths[i] + "月";
            } else {
                months[i] = String.valueOf(ChineseNumberUtils.sCnMonths[i]);
            }
        }
        return months;
    }

    /**
     * 获取农历日份的字符串数组
     *
     * @return 农历日份的字符串数组
     */
    public static String[] getLunarDays() {
        String[] days = new String[30];
        for (int i = 0; i < 9; i++) {
            days[i] = "初" + ChineseNumberUtils.sCnNums[i];
        }
        days[9] = "初十";
        for (int i = 10; i < 19; i++) {
            days[i] = "十" + ChineseNumberUtils.sCnNums[i - 10];
        }
        days[19] = "二十";
        for (int i = 20; i < 29; i++) {
            days[i] = "廿" + ChineseNumberUtils.sCnNums[i - 20];
        }
        days[29] = "三十";
        return days;
    }
}
