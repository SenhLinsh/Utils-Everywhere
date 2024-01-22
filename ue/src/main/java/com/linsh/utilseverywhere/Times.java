package com.linsh.utilseverywhere;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/04/14
 *    desc   : 时间计算
 * </pre>
 */
public class Times {

    private static final long SECOND = 1000L;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;

    /**
     * 计算天数所需时间
     *
     * @param day 天
     */
    public static long day(int day) {
        return day * DAY;
    }

    /**
     * 计算天数所需时间
     *
     * @param day 天
     */
    public static long day(float day) {
        return (long) (day * DAY);
    }

    /**
     * 计算天数所需时间
     *
     * @param day  天
     * @param hour 时
     * @param min  分
     * @param sec  秒
     */
    public static long day(int day, int hour, int min, int sec) {
        return day * DAY + hour * HOUR + min * MINUTE + sec * SECOND;
    }
}
