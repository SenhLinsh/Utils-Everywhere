package com.linsh.lshutils.utils;

import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Senh Linsh on 16/11/12.
 */
public class LshDateUtils {

    /**
     * 获取星期几的字符串
     *
     * @param date   日期对象
     * @param prefix 前缀 如:需返回"周一"则传入"周", 返回"星期几"则传入"星期"
     */
    public static String getWeekDayString(Date date, String prefix) {
        return LshTimeUtils.getWeekDayString(date, prefix);
    }

    public static String getLunarDate(Date date) {
        return getLunarDate(date, true);
    }

    public static String getLunarDate(Date date, boolean hasYear) {
        return LshLunarCalendarUtils.getLunarStr(date, hasYear);
    }

    protected static String formatDateTime(long millis, int flags) {
        return DateUtils.formatDateTime(LshContextUtils.get(), millis, flags);
    }

    protected static String formatDateTime(long millis, int flags, Locale locale) {
        return DateUtils.formatDateRange(LshContextUtils.get(), new Formatter(new StringBuilder(50), locale), millis, millis, flags).toString();
    }

    public static String getWeekDayStringCN(long millis) {
        return formatDateTime(millis, DateUtils.FORMAT_SHOW_WEEKDAY, Locale.CHINA);
    }

    public static String getWeekDayString(long millis, Locale locale) {
        return formatDateTime(millis, DateUtils.FORMAT_SHOW_WEEKDAY, locale);
    }

    public static int getCurYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static String getNormalizedStr(Date date, boolean hasYear) {
        return getNormalizedStr(hasYear ? date.getYear() + 1900 : 0, date.getMonth() + 1, date.getDate());
    }

    public static String getNormalizedStr(int year, int month, int day) {
        StringBuilder builder = new StringBuilder();
        if (year > 0) {
            builder.append(year).append('-');
        }
        if (month < 10) {
            builder.append('0');
        }
        builder.append(month).append('-');
        if (day < 10) {
            builder.append('0');
        }
        builder.append(day);
        return builder.toString();
    }

    public static String getDisplayStr(Date date, boolean hasYear) {
        return getDisplayStr(hasYear ? date.getYear() + 1900 : 0, date.getMonth() + 1, date.getDate());
    }

    public static String getDisplayStr(int year, int month, int day) {
        StringBuilder builder = new StringBuilder();
        if (year > 0) {
            builder.append(year).append('年');
        }
        builder.append(month).append('月')
                .append(day).append('日');
        return builder.toString();
    }
}
