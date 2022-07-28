package com.linsh.utilseverywhere.entity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/07/27
 *    desc   :
 * </pre>
 */
public enum DateFormatType {

    DATE("yyyy-MM-dd"),
    DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    DATE_CN("yyyy年MM月dd日"),
    DATE_TIME_CN("yyyy年MM月dd日HH时mm分ss秒"),
    DATA_NO_YEAR("MM-dd"),
    DATA_NO_YEAR_CN("MM月dd日"),
    DIGITAL_DATE("yyyyMMdd"),
    DIGITAL_DATE_TIME("yyyyMMddHHmmss"),
    TIME("HH:mm:ss"),
    TIME_NO_SECOND("HH:mm"),
    TIME_NO_SECOND_CN("H时m分"),
    TIME_NO_HOUR("mm:ss"),
    TIME_NO_HOUR_CN("m分s秒");

    private final String format;

    DateFormatType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
