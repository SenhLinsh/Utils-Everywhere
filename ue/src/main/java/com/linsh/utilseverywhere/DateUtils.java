package com.linsh.utilseverywhere;

import com.linsh.utilseverywhere.entity.DateFormatType;

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
     * 判断是否为同一天
     *
     * @param timeMillis1 时间毫秒值1
     * @param timeMillis2 时间毫秒值2
     * @return 是否为同一天
     */
    public static boolean isSameDay(int timeMillis1, int timeMillis2) {
        return isSameDay(new Date(timeMillis1), new Date(timeMillis2));
    }

    /**
     * 判断是否为同一天
     *
     * @param date1 日期对象1
     * @param date2 日期对象2
     * @return 是否为同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate();
    }

    /**
     * 转换至当天凌晨(00:00)的时间
     *
     * @param timeMillis 时间毫秒值
     * @return 当天凌晨(00 : 00)的时间
     */
    public static long toDayBegin(long timeMillis) {
        return toDayBegin(new Date(timeMillis)).getTime();
    }

    /**
     * 转换至当天凌晨(00:00)的时间
     *
     * @param date 日期对象
     * @return 新创建的当天凌晨(00 : 00)的时间
     */
    public static Date toDayBegin(Date date) {
        return new Date(date.getYear(), date.getMonth(), date.getDate());
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
        return format(date, DateFormatType.DATE_TIME);
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
        return format(date, DateFormatType.DATE);
    }

    /**
     * 将当前日期格式化为字符串
     *
     * @param format 格式
     * @return 时间字符串
     */
    public static String format(DateFormatType format) {
        return format(new Date(), format.getFormat());
    }

    /**
     * 将日期格式化为字符串
     *
     * @param time   指定时间
     * @param format 格式
     * @return 时间字符串
     */
    public static String format(long time, DateFormatType format) {
        return format(time, format.getFormat());
    }

    /**
     * 将日期格式化为字符串
     *
     * @param date   指定时间
     * @param format 格式
     * @return 时间字符串
     */
    public static String format(Date date, DateFormatType format) {
        return format(date, format.getFormat());
    }

    /**
     * 将日期格式化为字符串
     *
     * @param time    指定时间
     * @param pattern 格式
     * @return 时间字符串
     */
    public static String format(long time, String pattern) {
        return format(new Date(time), pattern);
    }

    /**
     * 将日期格式化为字符串
     *
     * @param date    指定时间
     * @param pattern 格式
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
     * @deprecated 请使用 {@link DateUtils#parseDateTime(String)}
     */
    @Deprecated
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
     * 解析日期字符串, 字符串需要符合格式: [yyyy-MM-dd]
     *
     * @param date 时间字符串
     * @return 时间, 解析失败返回 -1
     */
    public static long parseDate(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 解析日期字符串, 字符串需要符合格式: [yyyy-MM-dd HH:mm:ss]
     *
     * @param date 时间字符串
     * @return 时间, 解析失败返回 -1
     */
    public static long parseDateTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取星期几的字符串
     *
     * @param milliseconds 时间
     */
    public static String getWeekdayStr(long milliseconds) {
        return getWeekdayStr(milliseconds, "星期");
    }

    /**
     * 获取星期几的字符串
     *
     * @param date   日期对象
     * @param prefix 前缀 如:需返回"周一"则传入"周", 返回"星期几"则传入"星期"
     */
    public static String getWeekdayStr(Date date, String prefix) {
        if (date == null || prefix == null) return "";
        return getWeekdayStr(date.getTime(), prefix);
    }

    /**
     * 获取星期几的字符串
     *
     * @param milliseconds 时间
     * @param prefix       前缀 如:需返回"周一"则传入"周", 返回"星期几"则传入"星期"
     */
    public static String getWeekdayStr(long milliseconds, String prefix) {
        if (prefix == null) return "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
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
