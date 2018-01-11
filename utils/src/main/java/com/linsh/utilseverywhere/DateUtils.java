package com.linsh.utilseverywhere;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 日期相关
 *
 *             注: 部分 API 直接参考或使用 https://github.com/Blankj/AndroidUtilCode 中 TimeUtils 类里面的方法
 * </pre>
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 获取当前年份
     *
     * @return 当前年份
     */
    public static int getCurYear() {
        return new Date().getYear() + 1900;
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份, 1 - 12
     */
    public static int getCurMonth() {
        return new Date().getMonth() + 1;
    }

    /**
     * 获取当前日份
     *
     * @return 当前日份, 1 - 31
     */
    public static int getCurDay() {
        return new Date().getDate();
    }

    /**
     * 当前时间下, 获取格式化的日期字符串, 英式, 如: 2000-01-01
     *
     * @return 时间字符串
     */
    public static String getCurDateStr() {
        return getDateStr(System.currentTimeMillis());
    }

    /**
     * 当前时间下, 获取格式化的时间字符串, 英式, 如: 2000-01-01 08:12:00
     *
     * @return 时间字符串
     */
    public static String getCurDateTimeStr() {
        return getDateTimeStr(System.currentTimeMillis());
    }

    /**
     * 获取常规日期时间字符串, 如 2000-01-01 08:12:00
     *
     * @param time 指定时间
     * @return 时间字符串
     */
    public static String getDateTimeStr(long time) {
        return getDateTimeStr(new Date(time));
    }

    /**
     * 获取常规日期时间字符串, 如 2000-01-01 08:12:00
     *
     * @param date 指定时间
     * @return 时间字符串
     */
    public static String getDateTimeStr(Date date) {
        return getDateTimeStr(date.getYear() + 1900, date.getMonth() + 1, date.getDate(),
                date.getHours(), date.getMinutes(), date.getSeconds());
    }

    /**
     * 获取常规日期时间字符串, 如 2000-01-01 08:12:00
     *
     * @param year   年份, 如果年份小于等于 0, 将不显示年份
     * @param month  月份
     * @param day    日份
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 日期字符串
     */
    public static String getDateTimeStr(int year, int month, int day, int hour, int minute, int second) {
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
        builder.append(day).append(' ');
        if (hour < 10) {
            builder.append('0');
        }
        builder.append(hour).append(':');
        if (minute < 10) {
            builder.append('0');
        }
        builder.append(minute).append(':');
        if (second < 10) {
            builder.append('0');
        }
        builder.append(second);
        return builder.toString();
    }

    /**
     * 获取当前时间下的常规日期, 如 2000-01-01
     *
     * @return 日期字符串
     */
    public static String getDateStr() {
        return getDateStr(new Date());
    }

    /**
     * 获取常规日期, 如 2000-01-01
     *
     * @param time 日期
     * @return 日期字符串
     */
    public static String getDateStr(long time) {
        return getDateStr(new Date(time));
    }

    /**
     * 获取常规日期, 如 2000-01-01
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String getDateStr(Date date) {
        return getDateStr(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
    }

    /**
     * 获取常规日期, 如 2000-01-01
     *
     * @param date    日期
     * @param hasYear 是否显示年份
     * @return 日期字符串
     */
    public static String getDateStr(Date date, boolean hasYear) {
        return getDateStr(hasYear ? date.getYear() + 1900 : 0, date.getMonth() + 1, date.getDate());
    }

    /**
     * 获取常规日期字符串, 如 2000-01-01
     *
     * @param year  年份, 如果年份小于等于 0, 将不显示年份
     * @param month 月份
     * @param day   日份
     * @return 日期字符串
     */
    public static String getDateStr(int year, int month, int day) {
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

    /**
     * 将日期格式化为字符串
     *
     * @param time    指定时间
     * @param pattern 样式
     * @return 时间字符串
     */
    public static String format(long time, String pattern) {
        return format(new Date(time), pattern);
    }

    /**
     * 将日期格式化为字符串
     *
     * @param date    指定时间
     * @param pattern 样式
     * @return 时间字符串
     */
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }


    /**
     * 解析时间字符串, 字符串需要符合格式: [yyyy-MM-dd HH:mm:ss]
     *
     * @param date 时间字符串
     * @return 时间, 解析失败返回 -1
     */
    public static long parse(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 解析时间字符串
     *
     * @param time    时间字符串
     * @param pattern 格式
     * @return 时间
     */
    public static long parse(String time, String pattern) {
        try {
            Date parse = new SimpleDateFormat(pattern, Locale.getDefault()).parse(time);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取星期几的字符串
     *
     * @param milliseconds 时间
     * @param prefix       前缀 如:需返回"周一"则传入"周", 返回"星期几"则传入"星期"
     */
    public static String getWeekdayStr(long milliseconds, String prefix) {
        return getWeekdayStr(new Date(milliseconds), prefix);
    }

    /**
     * 获取星期几的字符串
     *
     * @param date   日期对象
     * @param prefix 前缀 如:需返回"周一"则传入"周", 返回"星期几"则传入"星期"
     */
    public static String getWeekdayStr(Date date, String prefix) {
        if (date == null || prefix == null) return "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayIndex) {
            case Calendar.SUNDAY:
                prefix += "日";
                break;
            case Calendar.MONDAY:
                prefix += "一";
                break;
            case Calendar.TUESDAY:
                prefix += "二";
                break;
            case Calendar.WEDNESDAY:
                prefix += "三";
                break;
            case Calendar.THURSDAY:
                prefix += "四";
                break;
            case Calendar.FRIDAY:
                prefix += "五";
                break;
            case Calendar.SATURDAY:
                prefix += "六";
                break;
        }
        return prefix;
    }
}
