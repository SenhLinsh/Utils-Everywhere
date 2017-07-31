package com.linsh.lshutils.utils;

import java.util.Calendar;
import java.util.Date;

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
        StringBuilder builder = new StringBuilder();

        String[] chineseMonths = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"};
        String[] chineseDayPres = {"初", "十", "廿", "三十"};
        String[] chineseDays = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (hasYear) {
            int year = calendar.get(Calendar.YEAR);
            while (year > 0) {
                builder.insert(0, chineseDays[year % 10 - 1]);
                year /= 10;
            }
            builder.append("年");
        }
        int month = calendar.get(Calendar.MONTH);
        builder.append(chineseMonths[month]).append("月");
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day == 10) {
            builder.append("初十");
        } else if (day == 20) {
            builder.append("二十");
        } else {
            builder.append(chineseDayPres[day / 10]).append(chineseDays[day % 10]);
        }
        return builder.toString();
    }
}
