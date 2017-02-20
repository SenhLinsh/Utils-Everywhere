package com.linsh.lshutils.utils;

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
        if (date == null || prefix == null) return "";

        switch (date.getDay()) {
            case 0:
                prefix += "日";
                break;
            case 1:
                prefix += "一";
                break;
            case 2:
                prefix += "二";
                break;
            case 3:
                prefix += "三";
                break;
            case 4:
                prefix += "四";
                break;
            case 5:
                prefix += "五";
                break;
            case 6:
                prefix += "六";
                break;
        }
        return prefix;
    }
}
