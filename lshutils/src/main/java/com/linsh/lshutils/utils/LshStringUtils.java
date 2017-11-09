package com.linsh.lshutils.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Senh Linsh on 17/1/11.
 */
public class LshStringUtils {

    /**
     * null & 空字符串 : 返回true
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static boolean notEmpty(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean isAllEmpty(CharSequence... str) {
        if (str == null) return true;
        for (CharSequence charSequence : str) {
            if (!isEmpty(charSequence)) return false;
        }
        return true;
    }

    public static boolean isAllNotEmpty(CharSequence... str) {
        if (str == null) return false;
        for (CharSequence charSequence : str) {
            if (isEmpty(charSequence)) return false;
        }
        return true;
    }

    public static boolean isNotAllEmpty(CharSequence... str) {
        return !isAllEmpty(str);
    }

    /**
     * null & 空字符串 & 空格字符串 : 返回true
     */
    public static boolean isTrimEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }


    /**
     * null & 空白字符串 : 返回true
     */
    public static boolean isBlank(String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEquals(String actual, String expected) {
        return actual == expected || (actual != null && actual.equals(expected));
    }

    public static boolean notEquals(String actual, String expected) {
        return !isEquals(actual, expected);
    }

    /**
     * null 返回空串
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    public static String utf8Encode(String str, String defaultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defaultReturn;
            }
        }
        return str;
    }

    /**
     * 判断时候存在中文汉字
     */
    public static boolean hasChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }


    /**
     * 格式化字符串, 用参数进行替换, 例子: format("I am {arg1}, {arg2}", arg1, arg2);
     */
    public static String format(String format, Object... args) {
        for (Object arg : args) {
            format = format.replaceFirst("\\{[^\\}]+\\}", arg.toString());
        }
        return format;
    }
}
