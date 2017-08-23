package com.linsh.lshutils.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Senh Linsh on 17/8/22.
 */

public class LshLunarCalendarUtils {

    /**
     * @param lunarStr 农历日期字符串, 如: 二零零零年正月初一, 腊月廿三, 十一月二十一
     * @return
     */
    public static Date lunarStr2Date(String lunarStr) {
        Matcher matcher = Pattern.compile("(([\\u4e00-\\u9fa5]{2,4})年)?([\\u4e00-\\u9fa5]{1,2})月([\\u4e00-\\u9fa5]{1,3})日?").matcher(lunarStr);
        if (matcher.find()) {
            String year = matcher.group(2);
            String month = matcher.group(3);
            String day = matcher.group(4);

            Date date = new Date();
            if (year != null) {
                date.setYear(LshChineseNumberUtils.parseLunarYear(year) - 1900);
            }
            date.setMonth(LshChineseNumberUtils.parseLunarMonth(month) - 1);
            date.setDate(LshChineseNumberUtils.parseLunarDay(day));
            return date;
        }
        return null;
    }

    public static String getLunarDate(Date date, boolean hasYear) {
        StringBuilder builder = new StringBuilder();

        String[] chineseMonths = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"};
        String[] chineseDayPres = {"初", "十", "廿", "三十"};
        String[] chineseDays = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
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
