package com.senh.lshutils.utils.Basic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Senh Linsh on 17/1/11.
 */
public class LshStringUtils {

    // null & 空字符串 & 空格字符串 : 返回true
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    // null & 空字符串 : 返回true
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    // 避免了空指针异常
    public static boolean isEquals(String actual, String expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    // null 返回空串
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
}
