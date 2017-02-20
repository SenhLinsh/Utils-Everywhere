package com.linsh.lshutils.utils_ex;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static final int WEEKDAYS = 7;
    public static final String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long类型时间 --> 字符串
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long类型时间 --> 字符串<br>
     * 格式:yyyy-MM-dd HH:mm:ss
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取当前时间，返回long类型
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间，返回long类型<br>
     * 格式:yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 将以前的时间毫秒值和现在对比，转换成日期描述，如三周前，上午，昨天等
     *
     * @param milliseconds 时间
     * @return UTM转换成日期描述，如三周前，上午，昨天等
     */
    public static String getTimeDesc(long milliseconds) {
        return getTimeDesc(milliseconds, true);
    }

    /**
     * 将以前的时间毫秒值和现在对比，转换成日期描述，如三周前，上午，昨天等
     *
     * @param milliseconds milliseconds
     * @param isShowWeek   是否采用周的形式显示  true 显示为3周前，false 则显示为时间格式mm-dd
     * @return 如三周前，上午，昨天等
     */

    public static String getTimeDesc(long milliseconds, boolean isShowWeek) {
        StringBuffer sb = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        long hour = calendar.get(Calendar.HOUR_OF_DAY);

        calendar.setTimeInMillis(System.currentTimeMillis());
        long hourNow = calendar.get(Calendar.HOUR_OF_DAY);

        Log.v("---------->---", System.currentTimeMillis() + "----------" + milliseconds);
        long datetime = System.currentTimeMillis() - (milliseconds);
        long day = (long) Math.floor(datetime / 24 / 60 / 60 / 1000.0f) + (hourNow < hour ? 1 : 0);// 天前

        if (day <= 7) {// 一周内
            if (day == 0) {// 今天
                if (hour <= 4) {
                    sb.append(" 凌晨 ");
                } else if (hour > 4 && hour <= 6) {
                    sb.append(" 早上 ");
                } else if (hour > 6 && hour <= 11) {
                    sb.append(" 上午 ");
                } else if (hour > 11 && hour <= 13) {
                    sb.append(" 中午 ");
                } else if (hour > 13 && hour <= 18) {
                    sb.append(" 下午 ");
                } else if (hour > 18 && hour <= 19) {
                    sb.append(" 傍晚 ");
                } else if (hour > 19 && hour <= 24) {
                    sb.append(" 晚上 ");
                } else {
                    sb.append("今天 ");
                }
            } else if (day == 1) {// 昨天
                sb.append(" 昨天 ");
            } else if (day == 2) {// 前天
                sb.append(" 前天 ");
            } else {
                sb.append(" " + DateToWeek(milliseconds) + " ");
            }
        } else {// 一周之前
            if (isShowWeek) {
                sb.append((day % 7 == 0 ? (day / 7) : (day / 7 + 1)) + "周前");
            } else {
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                String time = format.format(milliseconds);
                sb.append(time);
            }
        }
        Log.v("sb---", sb.toString() + "");
        return sb.toString();

    }

    /**
     * 将以前的时间毫秒值和现在对比，转换成日期描述，并附上当时的时间后缀
     *
     * @param milliseconds milliseconds
     * @return UTM转换成带描述的日期
     */

    public static String getDescAndDisplayTime(long milliseconds) {
        SimpleDateFormat formatBuilder = new SimpleDateFormat("HH:mm");
        String time = formatBuilder.format(milliseconds);
        StringBuffer sb = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        long hour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.v("---------->---", System.currentTimeMillis() + "----------" + milliseconds);
        long datetime = System.currentTimeMillis() - (milliseconds);
        long day = (long) Math.ceil(datetime / 24 / 60 / 60 / 1000.0f);// 天前
        Log.v("day---hour---time---", day + "----" + hour + "-----" + time);

        if (day <= 7) {// 一周内
            if (day == 0) {// 今天
                if (hour <= 4) {
                    sb.append(" 凌晨 " + time);
                } else if (hour > 4 && hour <= 6) {
                    sb.append(" 早上 " + time);
                } else if (hour > 6 && hour <= 11) {
                    sb.append(" 上午 " + time);
                } else if (hour > 11 && hour <= 13) {
                    sb.append(" 中午 " + time);
                } else if (hour > 13 && hour <= 18) {
                    sb.append(" 下午 " + time);
                } else if (hour > 18 && hour <= 19) {
                    sb.append(" 傍晚 " + time);
                } else if (hour > 19 && hour <= 24) {
                    sb.append(" 晚上 " + time);
                } else {
                    sb.append("今天 " + time);
                }
            } else if (day == 1) {// 昨天
                sb.append("昨天 " + time);
            } else if (day == 2) {// 前天
                sb.append("前天 " + time);
            } else {
                sb.append(DateToWeek(milliseconds) + time);
            }
        } else {// 一周之前
            sb.append(day % 7 + "周前");
        }
        Log.v("sb---", sb.toString() + "");
        return sb.toString();

    }


    /**
     * 日期变量转成对应的星期字符串
     *
     * @param milliseconds data
     * @return 日期变量转成对应的星期字符串
     */
    public static String DateToWeek(long milliseconds) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }

    /**
     * 将指定的时间和当前时间对比出来的时间间隔转换成描述性字符串，如2天前，3月1天后等。
     *
     * @param toDate 相对的日期
     * @param isFull 是否全部显示： true 全部显示，如x年x月x日x时x分后； false 简单显示
     * @return 将时间间隔转换成描述性字符串，如2天前，3月1天后等。
     */
    public static String diffDateAsDesc(Date toDate, boolean isFull) {
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
     * @param isFull   是否全部显示： true 全部显示，如x年x月x日x时x分； false 简单显示,如4月 / 3天
     */
    public static String time2String(long timeLong, boolean isFull) {
        String diffDesc = "";

        //换算miao，防止Int溢出。
        timeLong = timeLong / 1000L;

        Long year = timeLong / (60 * 60 * 24 * 30 * 12);
        timeLong = timeLong % (60 * 60 * 24 * 30 * 12);
        if (year > 0) {
            diffDesc = diffDesc + year + "年";
            if (!isFull) {
                return diffDesc;
            }
        }

        Long month = timeLong / (60 * 60 * 24 * 30);
        timeLong = timeLong % (60 * 60 * 24 * 30);
        if (month > 0) {
            diffDesc = diffDesc + month + "月";
            if (!isFull) {
                return diffDesc;
            }
        }

        Long day = timeLong / 60 / 60 / 24;
        timeLong = timeLong % (60 * 60 * 24);
        if (day > 0) {
            diffDesc = diffDesc + day + "天";
            if (!isFull) {
                return diffDesc;
            }
        }

        Long hour = timeLong / (60 * 60);
        timeLong = timeLong % (60 * 60);
        if (hour > 0) {
            diffDesc = diffDesc + hour + "时";
            if (!isFull) {
                return diffDesc;
            }
        }

        Long minitue = timeLong / 60;
        timeLong = timeLong % (60);
        if (minitue > 0) {
            diffDesc = diffDesc + minitue + "分";
            if (!isFull) {
                return diffDesc;
            }
        }

        Long second = timeLong;
        if (second > 0) {
            diffDesc = diffDesc + second + "秒";
            if (!isFull) {
                return diffDesc;
            }
        }

        return diffDesc;
    }
}
