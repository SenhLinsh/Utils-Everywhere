package com.linsh.lshutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 时间相关
 * </pre>
 */
public class LshTimeUtils {

    /**
     * 当前时间下, 获取格式化的时间字符串, 英式
     *
     * @return 时间字符串
     */
    public static String getCurrentTimeStringEN() {
        return getTimeStringEN(System.currentTimeMillis());
    }

    /**
     * 获取格式化的时间字符串, 英式
     *
     * @param time 指定时间
     * @return 时间字符串
     */
    public static String getTimeStringEN(long time) {
        return getTimeString(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取格式化的时间字符串, 英式
     *
     * @param date 指定时间
     * @return 时间字符串
     */
    public static String getTimeStringEN(Date date) {
        return getTimeString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取格式化的时间字符串
     *
     * @param time    指定时间
     * @param pattern 样式
     * @return 时间字符串
     */
    public static String getTimeString(long time, String pattern) {
        return getTimeString(new Date(time), pattern);
    }

    /**
     * 获取格式化的时间字符串
     *
     * @param date    指定时间
     * @param pattern 样式
     * @return 时间字符串
     */
    public static String getTimeString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 解析时间字符串, 字符串需要符合格式: [yyyy-MM-dd HH:mm:ss]
     *
     * @param time 时间字符串
     * @return 时间
     */
    public static long getTimeLong(String time) {
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 解析时间字符串
     *
     * @param time    时间字符串
     * @param pattern 格式
     * @return 时间
     */
    public static long getTimeLong(String time, String pattern) {
        try {
            Date parse = new SimpleDateFormat(pattern).parse(time);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取星期几的字符串
     *
     * @param milliseconds 时间
     * @param prefix       前缀 如:需返回"周一"则传入"周", 返回"星期几"则传入"星期"
     */
    public static String getWeekDayString(long milliseconds, String prefix) {
        return getWeekDayString(new Date(milliseconds), prefix);
    }

    /**
     * 获取星期几的字符串
     *
     * @param date   日期对象
     * @param prefix 前缀 如:需返回"周一"则传入"周", 返回"星期几"则传入"星期"
     */
    public static String getWeekDayString(Date date, String prefix) {
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

    /**
     * 将以前的时间和现在对比, 转化成描述性时间字符串，并附上当时的时间后缀
     *
     * @param beforeDate 当前时间点以前的时间
     * @return 描述性时间字符串
     */
    public static String dateBefore2StringDesc(Date beforeDate) {
        return dateBefore2StringDesc(beforeDate, true);
    }

    /**
     * 将以前的时间毫秒值和现在对比，转化成描述性时间字符串，并附上当时的时间后缀
     *
     * @param beforeDate 当前时间点以前的时间
     * @return 描述性时间字符串
     */
    public static String dateBefore2StringDesc(Date beforeDate, boolean isShowWeek) {
        long milliseconds = beforeDate.getTime();
        SimpleDateFormat formatBuilder = new SimpleDateFormat("HH:mm");
        String time = formatBuilder.format(milliseconds);
        StringBuffer sb = new StringBuffer();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        long hour = calendar.get(Calendar.HOUR_OF_DAY);
        long datetime = System.currentTimeMillis() - (milliseconds);
        long day = (long) Math.ceil(datetime / 24 / 60 / 60 / 1000.0f); // 天前 (向上取整)

        if (day <= 7) { // 一周内
            if (day == 0) { // 今天
                if (hour <= 4) {
                    sb.append("凌晨 ");
                } else if (hour > 4 && hour <= 6) {
                    sb.append("早上 ");
                } else if (hour > 6 && hour <= 11) {
                    sb.append("上午 ");
                } else if (hour > 11 && hour <= 13) {
                    sb.append("中午 ");
                } else if (hour > 13 && hour <= 18) {
                    sb.append("下午 ");
                } else if (hour > 18 && hour <= 19) {
                    sb.append("傍晚 ");
                } else if (hour > 19 && hour <= 24) {
                    sb.append("晚上 ");
                } else {
                    sb.append("今天 ");
                }
            } else if (day == 1) { // 昨天
                sb.append("昨天 ");
            } else if (day == 2) { // 前天
                sb.append("前天 ");
            } else {
                sb.append(getWeekDayString(milliseconds, "周"));
            }
            // 添加后缀
            sb.append(time);
        } else if (day <= 30 && isShowWeek) { // 一周之前
            sb.append(day % 7 == 0 ? (day / 7) : (day / 7 + 1)).append("周前");
        } else { // 一个月之前
            sb.append(new SimpleDateFormat("MM月dd日 ").format(beforeDate)).append(time);
        }
        return sb.toString();
    }

    /**
     * 将指定的时间和当前时间对比出来的时间间隔转换成描述性字符串，如2天前，3月1天后等。
     *
     * @param toDate 相对的日期
     * @param isFull 是否全部显示： true 全部显示，如x年x月x日x时x分后； false 简单显示
     * @return 将时间间隔转换成描述性字符串，如2天前，3月1天后等。
     */
    public static String date2StringCN(Date toDate, boolean isFull) {
        String diffDesc = "";
        String fix = "";
        Long diffTime;
        Date curDate = new Date();
        if (curDate.getTime() > toDate.getTime()) {
            diffTime = curDate.getTime() - toDate.getTime();
            fix = "前";
        } else {
            diffTime = toDate.getTime() - curDate.getTime();
            fix = "后";
        }

        //换算成分钟数，防止Int溢出。
        diffTime = diffTime / 1000 / 60;
        Long year = diffTime / (60 * 24 * 30 * 12);
        diffTime = diffTime % (60 * 24 * 30 * 12);
        if (year > 0) {
            diffDesc = diffDesc + year + "年";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long month = diffTime / (60 * 24 * 30);
        diffTime = diffTime % (60 * 24 * 30);
        if (month > 0) {
            diffDesc = diffDesc + month + "月";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long day = diffTime / 60 / 24;
        diffTime = diffTime % (60 * 24);
        if (day > 0) {
            diffDesc = diffDesc + day + "天";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long hour = diffTime / (60);
        diffTime = diffTime % (60);
        if (hour > 0) {
            diffDesc = diffDesc + hour + "时";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long minitue = diffTime;
        if (minitue > 0) {
            diffDesc = diffDesc + minitue + "分";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        return diffDesc + fix;
    }

    /**
     * 将指定的时间长度转换成描述性字符串，如2天，3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     */
    public static String timeLong2StringCN(long timeLong) {
        return timeLong2StringCN(timeLong, true, 0);
    }

    /**
     * 将指定的时间长度转换成描述性字符串，如2天，3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     * @param isFull   是否全部显示： true 全部显示，如x年x月x日x时x分； false 简单显示,如4月 / 3天
     */
    public static String timeLong2StringCN(long timeLong, boolean isFull) {
        return timeLong2StringCN(timeLong, isFull, 0);
    }

    /**
     * 将指定的时间长度转换成描述性字符串，如 3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     * @param minUnit  最低显示单位, 6->年 5->月 4->日 3->时 2->分, 如3: 00时00分06秒
     */
    public static String timeLong2StringCN(long timeLong, int minUnit) {
        return timeLong2StringCN(timeLong, true, minUnit);
    }

    /**
     * 将指定的时间长度转换成描述性字符串，如2天，3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     * @param isFull   是否全部显示： true 全部显示，如x年x月x日x时x分； false 简单显示,如4月 / 3天
     * @param minUnit  最低显示单位, 6->年 5->月 4->日 3->时 2->分, 如3: 00时00分06秒
     */
    public static String timeLong2StringCN(long timeLong, boolean isFull, int minUnit) {
        StringBuilder diffDesc = new StringBuilder();

        //换算秒，防止Int溢出
        timeLong = timeLong / 1000L;

        Long year = timeLong / (60 * 60 * 24 * 30 * 12);
        timeLong = timeLong % (60 * 60 * 24 * 30 * 12);
        if (year > 0 || minUnit >= 6) {
            diffDesc.append(year).append("年");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long month = timeLong / (60 * 60 * 24 * 30);
        timeLong = timeLong % (60 * 60 * 24 * 30);
        if (month > 0 || diffDesc.length() != 0 || minUnit >= 5) {
            if (month < 10) diffDesc.append("0");
            diffDesc.append(month).append("月");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long day = timeLong / 60 / 60 / 24;
        timeLong = timeLong % (60 * 60 * 24);
        if (day > 0 || diffDesc.length() != 0 || minUnit >= 4) {
            if (day < 10) diffDesc.append("0");
            diffDesc.append(day).append("天");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long hour = timeLong / (60 * 60);
        timeLong = timeLong % (60 * 60);
        if (hour > 0 || diffDesc.length() != 0 || minUnit >= 3) {
            if (hour < 10) diffDesc.append("0");
            diffDesc.append(hour).append("时");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long minitue = timeLong / 60;
        timeLong = timeLong % (60);
        if (minitue > 0 || diffDesc.length() != 0 || minUnit >= 2) {
            if (minitue < 10) diffDesc.append("0");
            diffDesc.append(minitue).append("分");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long second = timeLong;
        if (second < 10) diffDesc.append("0");
        diffDesc.append(second).append("秒");
        if (!isFull) {
            return diffDesc.toString();
        }
        return diffDesc.toString();
    }

    /**
     * 将指定的时间长度转换成常规描述性的时间字符串, 时长为24小时以内, 如 23:32:00。
     *
     * @param timeLong 指定的时间长度
     * @return 常规描述性的时间字符串
     */
    public static String dayTime2StringNol(long timeLong) {
        StringBuilder desc = new StringBuilder();
        //换算秒，防止Int溢出。
        timeLong = timeLong / 1000L;
        // 截成天以内
        timeLong = timeLong % (60 * 60 * 24);

        Long hour = timeLong / (60 * 60);
        timeLong = timeLong % (60 * 60);
        if (hour > 0) {
            if (hour < 10) desc.append("0");
            desc.append(hour).append(":");
        }

        Long minitue = timeLong / 60;
        timeLong = timeLong % (60);
        if (minitue < 10) {
            desc.append("0");
        }
        desc.append(minitue).append(":");

        Long second = timeLong;
        if (second < 10) desc.append("0");
        desc.append(second);
        return desc.toString();
    }
}
