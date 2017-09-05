package com.linsh.lshutils.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
     * @param lunarStr 农历日期字符串, 如: 二零零零年正月初一, 腊月廿三, 十一月二十一
     * @return Date 日期格式, 解析失败返回 null
     */
    public static Date lunarStr2Date(String lunarStr) {
        Matcher matcher = Pattern.compile("(([\\u4e00-\\u9fa5]{2,4})年)?([\\u4e00-\\u9fa5]{1,2})月([\\u4e00-\\u9fa5]{1,3})日?").matcher(lunarStr);
        if (matcher.find()) {
            String year = matcher.group(2);
            String month = matcher.group(3);
            String day = matcher.group(4);

            try {
                Date date = new Date();
                if (year != null) {
                    date.setYear(LshChineseNumberUtils.parseLunarYear(year) - 1900);
                }
                date.setMonth(LshChineseNumberUtils.parseLunarMonth(month) - 1);
                date.setDate(LshChineseNumberUtils.parseLunarDay(day));
                return date;
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        Matcher matcher = Pattern.compile("(([\\u4e00-\\u9fa5]{2,4})年)?([\\u4e00-\\u9fa5]{1,2})月([\\u4e00-\\u9fa5]{1,3})日?").matcher(lunarStr);
        if (matcher.find()) {
            String yearStr = matcher.group(2);
            String monthStr = matcher.group(3);
            String dayStr = matcher.group(4);

            StringBuilder builder = new StringBuilder();
            if (yearStr != null) {
                builder.append(LshChineseNumberUtils.parseLunarYear(yearStr)).append('-');
            }
            int month = LshChineseNumberUtils.parseLunarMonth(monthStr);
            if (month < 10) {
                builder.append('0');
            }
            builder.append(month).append('-');
            int day = LshChineseNumberUtils.parseLunarDay(dayStr);
            if (day < 10) {
                builder.append('0');
            }
            builder.append(day);
            return builder.toString();
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
        try {
            if (dateStr.length() > 5) {
                return getLunarDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr), true);
            } else {
                return getLunarDate(new SimpleDateFormat("MM-dd").parse(dateStr), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLunarDate(Date date, boolean hasYear) {
        StringBuilder builder = new StringBuilder();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (hasYear) {
            String year = LshChineseNumberUtils.formatNumberWithoutUnit(calendar.get(Calendar.YEAR));
            builder.append(year).append("年");
        }
        char month = LshChineseNumberUtils.formatLunarMonth(calendar.get(Calendar.MONTH) + 1);
        builder.append(month).append("月");
        String day = LshChineseNumberUtils.formatLunarDay(calendar.get(Calendar.DAY_OF_MONTH));
        builder.append(day);
        return builder.toString();
    }

}
