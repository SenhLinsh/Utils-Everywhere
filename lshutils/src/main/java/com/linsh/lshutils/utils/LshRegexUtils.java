package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/1/11.
 */
public class LshRegexUtils {
    // 全网IP
    public static final String IP_REGEX = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";
    // 手机号码
    public static final String PHONE_NUMBER_REGEX = "^1\\d{10}$";
    // 邮箱 ("www."可省略不写)
    public static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+(\\.\\w+)+$";
    // 汉字 (个数限制为一个或多个)
    public static final String CHINESE_REGEX = "^[\u4e00-\u9f5a]+$";
    // 身份证号
    public static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
    // 网址
    public static final String URL = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))://[\\w.-]+\\.\\w{2,4}((/.*)?|(\\?.+))$";

    /**
     * 匹配邮箱账号，"www."可省略不写
     */
    public static boolean isEmail(String string) {
        return string.matches(EMAIL_REGEX);
    }

    /**
     * 匹配手机号码 (由于号码段资源非常丰富, 只对 1 开头 11 位数字作为限制条件)
     */
    public static boolean isMobilePhoneNumber(String string) {
        return string.matches(PHONE_NUMBER_REGEX);
    }

    /**
     * 匹配全网IP
     */
    public static boolean isIp(String string) {
        return string.matches(IP_REGEX);
    }

    /**
     * 是否全部由汉子组成
     */
    public static boolean isChinese(String string) {
        return string.matches(CHINESE_REGEX);
    }

    /**
     * 匹配身份证号
     */
    public static boolean isIdCard(String string) {
        return string.matches(ID_CARD);
    }

    /**
     * 验证给定的字符串是否是URL，仅支持http、https、ftp
     */
    public static boolean isURL(String string) {
        return string.matches(URL);
    }
}
