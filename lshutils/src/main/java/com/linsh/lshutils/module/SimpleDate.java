package com.linsh.lshutils.module;

import com.linsh.lshutils.utils.LshChineseNumberUtils;
import com.linsh.lshutils.utils.LshLunarCalendarUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Senh Linsh on 17/9/8.
 */

public class SimpleDate {

    private int[] mDate;

    private boolean mIsLunar;

    public SimpleDate(String date) throws Exception {
        mDate = new int[3];
        parseDateString(date);
    }

    public SimpleDate(long date) {
        this(new Date(date));
    }

    public SimpleDate(Date date) {
        this(date, false);
    }

    public SimpleDate(Date date, boolean isLunar) {
        this(date.getYear() + 1900, date.getMonth() + 1, date.getDate(), isLunar);
    }

    public SimpleDate(int year, int month, int day) {
        this(year, month, day, false);
    }

    public SimpleDate(int year, int month, int day, boolean isLunar) {
        mDate = new int[3];
        mDate[0] = year;
        mDate[1] = month;
        mDate[2] = day;
        mIsLunar = isLunar;
    }

    public boolean isLunar() {
        return mIsLunar;
    }

    public void setLunar(boolean lunar) {
        mIsLunar = lunar;
    }

    public int getYear() {
        return mDate[0];
    }

    public void setYear(int year) {
        mDate[0] = year;
    }

    public int getMonth() {
        return mDate[1];
    }

    public void setMonth(int month) {
        mDate[1] = month;
    }

    public int getDay() {
        return mDate[2];
    }

    public void setDay(int day) {
        mDate[2] = day;
    }

    public Date getDate() {
        return new Date(mDate[0], mDate[1] - 1, mDate[2]);
    }

    public String getNormalizedString() {
        return getNormalizedString(true);
    }

    public String getNormalizedString(boolean hasYear) {
        StringBuilder builder = new StringBuilder();
        if (mDate[0] > 0 && hasYear) {
            builder.append(mDate[0]).append('-');
        }
        if (mDate[1] < 10) {
            builder.append('0');
        }
        builder.append(mDate[0]).append('-');
        if (mDate[2] < 10) {
            builder.append('0');
        }
        builder.append(mDate[2]);
        return builder.toString();
    }

    public String getDisplayString() {
        return getDisplayString(true);
    }

    public String getDisplayString(boolean hasYear) {
        StringBuilder builder = new StringBuilder();
        if (mDate[0] > 0 && hasYear) {
            builder.append(mDate[0]).append('年');
        }
        if (!mIsLunar) {
            builder.append(mDate[1]).append('月')
                    .append(mDate[2]).append('日');
        } else {
            builder.append(LshLunarCalendarUtils.getLunarDate(getDate(), false));
        }
        return builder.toString();
    }

    private void parseDateString(String date) throws Exception {
        Matcher matcher = Pattern.compile("^((\\d{2,4})[年-])?(\\d{1,2})[月-](\\d{1,2})日?$").matcher(date);
        if (matcher.find()) {
            String year = matcher.group(2);
            String month = matcher.group(3);
            String day = matcher.group(4);

            if (year != null) {
                mDate[0] = Integer.parseInt(year);
            }
            mDate[1] = Integer.parseInt(month);
            mDate[2] = Integer.parseInt(day);
            return;
        }
        matcher = Pattern.compile("^(((\\d{2,4})|([\\u4e00-\\u9fa5]{2,4}))年)?([\\u4e00-\\u9fa5]{1,2})月([\\u4e00-\\u9fa5]{1,3})日?$").matcher(date);
        if (matcher.find()) {
            String yearAr = matcher.group(3);
            String yearCn = matcher.group(4);
            String month = matcher.group(5);
            String day = matcher.group(6);

            try {
                if (yearAr != null) {
                    mDate[0] = Integer.parseInt(yearAr);
                }
                if (yearCn != null) {
                    mDate[0] = LshChineseNumberUtils.parseLunarYear(yearCn);
                }
                mDate[1] = LshChineseNumberUtils.parseLunarMonth(month);
                mDate[2] = LshChineseNumberUtils.parseLunarDay(day);
                mIsLunar = true;
            } catch (Exception e) {
                throw new Exception(e);
            }
            return;
        }
        throw new Exception("无法解析当前日期");
    }
}
