package com.linsh.lshutils.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 日期相关
 * </pre>
 */
public class DateUtils {

    /**
     * 获取农历日期 (不带年份)
     * <p>获取的是所指定的公历日历的农历表示, 而不是所指定的公历日期所以对应的农历
     *
     * @param date 日期对象
     * @return 农历日期
     */
    public static String getLunarDate(Date date) {
        return getLunarDate(date, false);
    }

    /**
     * 获取农历日期
     * <p>获取的是所指定的公历日历的农历表示, 而不是所指定的公历日期所以对应的农历
     *
     * @param date    日期对象
     * @param hasYear 是否带年份
     * @return 农历日期
     */
    public static String getLunarDate(Date date, boolean hasYear) {
        return LunarCalendarUtils.getLunarStr(date, hasYear);
    }

    /**
     * 格式化日期时间
     *
     * @param time  时间
     * @param flags 格式化选项, 详见 {@link android.text.format.DateUtils#FORMAT_SHOW_TIME} 等
     * @return 格式化日期时间
     */
    protected static String formatDateTime(long time, int flags) {
        return android.text.format.DateUtils.formatDateTime(ContextUtils.get(), time, flags);
    }

    /**
     * 格式化日期时间
     *
     * @param time   时间
     * @param flags  格式化选项, 详见 {@link android.text.format.DateUtils#FORMAT_SHOW_TIME} 等
     * @param locale 语言环境
     * @return 格式化日期时间
     */
    protected static String formatDateTime(long time, int flags, Locale locale) {
        return android.text.format.DateUtils.formatDateRange(ContextUtils.get(), new Formatter(new StringBuilder(50), locale), time, time, flags).toString();
    }

    /**
     * 获取星期几的字符串
     *
     * @param time 时间
     * @return 星期*
     */
    public static String getWeekDayStringCN(long time) {
        return formatDateTime(time, android.text.format.DateUtils.FORMAT_SHOW_WEEKDAY, Locale.CHINA);
    }

    /**
     * 获取星期几的字符串
     *
     * @param time   时间
     * @param locale 语言环境
     * @return 不同国家及地区的星期几字符串表示
     */
    public static String getWeekDayString(long time, Locale locale) {
        return formatDateTime(time, android.text.format.DateUtils.FORMAT_SHOW_WEEKDAY, locale);
    }

    /**
     * 获取当前年份
     *
     * @return 当前年份
     */
    public static int getCurYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份, 1 - 12
     */
    public static int getCurMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日份
     *
     * @return 当前日份, 1 - 31
     */
    public static int getCurDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取常规日期, 如 2000-01-01
     *
     * @param date    日期
     * @param hasYear 是否显示年份
     * @return 日期字符串
     */
    public static String getNormalizedStr(Date date, boolean hasYear) {
        return getNormalizedStr(hasYear ? date.getYear() + 1900 : 0, date.getMonth() + 1, date.getDate());
    }

    /**
     * 获取常规日期, 如 2000-01-01
     *
     * @param year  年份, 如果年份小于等于 0, 将不显示年份
     * @param month 月份
     * @param day   日份
     * @return 日期字符串
     */
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
}
