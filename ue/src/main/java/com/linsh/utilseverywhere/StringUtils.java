package com.linsh.utilseverywhere;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 字符串相关
 *             API  : 判空, 编码 等
 * </pre>
 */
public class StringUtils {

    private static final String LINE_SEPARATOR = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            ? System.lineSeparator() : System.getProperty("line.separator");
    private static final char CHINESE_SPACE_CHAR = '\u3000';

    private StringUtils() {
    }

    /**
     * 判断字符串是否为空
     *
     * @param string 指定字符串
     * @return null 或 空字符串返回 true, 否则返回 false
     */
    public static boolean isEmpty(CharSequence string) {
        return (string == null || string.length() == 0);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param string 指定字符串
     * @return 不为 null 且 长度大于 0 返回 true, 否则返回 false
     */
    public static boolean notEmpty(CharSequence string) {
        return string != null && string.length() > 0;
    }

    /**
     * 判断所有的字符串是否都为空
     *
     * @param strings 指定字符串数组获或可变参数
     * @return 所有字符串都为空返回 true, 否则放回 false
     */
    public static boolean isAllEmpty(CharSequence... strings) {
        if (strings == null) return true;
        for (CharSequence charSequence : strings) {
            if (!isEmpty(charSequence)) return false;
        }
        return true;
    }

    /**
     * 判断所有的字符串是否都不为空
     *
     * @param strings 指定字符串数组获或可变参数
     * @return 所有字符串都不为空返回 true, 否则放回 false
     */
    public static boolean isAllNotEmpty(CharSequence... strings) {
        if (strings == null) return false;
        for (CharSequence charSequence : strings) {
            if (isEmpty(charSequence)) return false;
        }
        return true;
    }

    /**
     * 判断所有的字符串是否不都为空
     *
     * @param strings 指定字符串数组获或可变参数
     * @return 所有字符串不都为空返回 true, 否则返回 false
     */
    public static boolean isNotAllEmpty(CharSequence... strings) {
        return !isAllEmpty(strings);
    }

    /**
     * 判断所有的字符串是否有任何一个为空
     *
     * @param strings 指定字符串数组获或可变参数
     * @return 任意存在一个为空返回 true, 否则返回 false
     */
    public static boolean isAnyEmpty(CharSequence... strings) {
        return !isAllNotEmpty(strings);
    }

    /**
     * 判断字符串是否为空或空格
     *
     * @param string 指定字符串
     * @return null 或空字符串或空格字符串返回true, 否则返回 false
     */
    public static boolean isTrimEmpty(String string) {
        return (string == null || string.trim().length() == 0);
    }

    /**
     * 判断字符串是否为空或空白
     *
     * @param string 指定字符串
     * @return null 或空白字符串返回true, 否则返回 false
     */
    public static boolean isBlank(String string) {
        if (string == null) return true;
        char c;
        for (int i = 0, len = string.length(); i < len; ++i) {
            if (!Character.isWhitespace(c = string.charAt(i)) || c == '\uFEFF') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个字符串是否相同
     *
     * @param a 作为对比的字符串
     * @param b 作为对比的字符串
     * @return 是否相同
     */
    public static boolean isEquals(String a, String b) {
        return a == b || (a != null && a.equals(b));
    }

    /**
     * 判断两个字符串是否相同
     *
     * @param str    需要进行对比的字符串
     * @param others 作为对比的字符串 (可多个)
     * @return 是否相同
     */
    public static boolean isAnyEquals(String str, String... others) {
        if (others == null) return false;
        for (String other : others) {
            if (isEquals(str, other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两个字符串是否不同
     *
     * @param a 作为对比的字符串
     * @param b 作为对比的字符串
     * @return 是否不同
     */
    public static boolean notEquals(String a, String b) {
        return !isEquals(a, b);
    }

    /**
     * null 转 空字符串
     *
     * @param obj 对象
     * @return 将 null 对象返回空字符串(""), 其他对象调用 toString() 返回的字符串
     */
    public static String nullToEmpty(Object obj) {
        return (obj == null ? "" : (obj instanceof String ? (String) obj : obj.toString()));
    }

    /**
     * null 转 指定的字符串
     *
     * @param obj             对象
     * @param defaultEmptyStr 用于替换 null 对象的字符串
     * @return 如果对象不为 null, 将返回该对象的 toString() 字符串; 如果对象为 null, 则返回指定的默认字符串
     */
    public static String nullToDefault(Object obj, String defaultEmptyStr) {
        return obj == null ? defaultEmptyStr : obj.toString();
    }

    /**
     * null 或空串 转 指定的字符串
     *
     * @param str             字符串
     * @param defaultEmptyStr 用于替换 null 或空串的字符串
     * @return 如果字符串不为 null 或空串, 将返回该字符串; 否则返回指定的默认字符串
     */
    public static String emptyToDefault(String str, String defaultEmptyStr) {
        return str == null || str.length() == 0 ? defaultEmptyStr : str;
    }


    /**
     * 修剪头尾的空格字符
     *
     * @param string 字符串
     * @return 返回修剪之后的字符串, 如果为 null 则返回 null
     */
    public static String trim(String string) {
        if (string == null) return null;
        return string.trim();
    }

    /**
     * 修剪头尾的空白字符, 包括空格, 缩进, 回车等
     *
     * @param string 字符串
     * @return 返回修剪之后的字符串, 如果为 null 则返回 null
     */
    public static String trimBlank(String string) {
        if (string == null) return null;
        int len = string.length();
        int st = 0;
        char c;
        while ((st < len) && (Character.isWhitespace(c = string.charAt(st)) || c == '\uFEFF')) {
            st++;
        }
        while ((st < len) && (Character.isWhitespace(c = string.charAt(len - 1)) || c == '\uFEFF')) {
            len--;
        }
        return ((st > 0) || (len < string.length())) ? string.substring(st, len) : string;
    }

    /**
     * 将字符串进行 UTF-8 编码
     *
     * @param string 指定字符串
     * @return 编码后的字符串
     */
    public static String utf8Encode(String string) {
        if (!isEmpty(string) && string.getBytes().length != string.length()) {
            try {
                return URLEncoder.encode(string, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return string;
    }

    /**
     * 将字符串进行 UTF-8 编码
     *
     * @param string        指定字符串
     * @param defaultReturn 编码失败返回的字符串
     * @return 编码后的字符串
     */
    public static String utf8Encode(String string, String defaultReturn) {
        if (!isEmpty(string) && string.getBytes().length != string.length()) {
            try {
                return URLEncoder.encode(string, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defaultReturn;
            }
        }
        return string;
    }

    /**
     * 判断字符串中是否存在中文汉字
     *
     * @param string 指定字符串
     * @return 是否存在
     */
    public static boolean hasChineseChar(String string) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(string);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }


    /**
     * 格式化字符串, 用参数进行替换, 例子: format("I am {arg1}, {arg2}", arg1, arg2);
     *
     * @param format 需要格式化的字符串
     * @param args   格式化参数
     * @return 格式化后的字符串
     */
    public static String format(String format, Object... args) {
        for (Object arg : args) {
            format = format.replaceFirst("\\{[^\\}]+\\}", arg.toString());
        }
        return format;
    }

    /**
     * 中文空格 (宽度和中文字符一致), 在字符串中可以用 \u3000 表示
     *
     * @return 中文空格字符串
     */
    public static String chineseSpace() {
        return String.valueOf(CHINESE_SPACE_CHAR);
    }

    /**
     * 获取中文空格 (宽度和中文字符一致), 在字符串中可以用 \u3000 表示
     *
     * @param num 空格数
     * @return 中文空格字符串
     */
    public static String getChineseSpaces(int num) {
        if (num < 100) {
            String spaces = "";
            for (int i = 0; i < num; i++) {
                spaces += CHINESE_SPACE_CHAR;
            }
            return spaces;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < num; i++) {
                builder.append(CHINESE_SPACE_CHAR);
            }
            return builder.toString();
        }
    }

    /**
     * 换行符, Android 为 {@code "\n"}, Windows 为 {@code "\r\n"}.
     *
     * @return 换行符
     */
    public static String lineSeparator() {
        return LINE_SEPARATOR;
    }
}
