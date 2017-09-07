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
        return LshLunarCalendarUtils.getLunarDate(date, hasYear);
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
}
