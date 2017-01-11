package com.senh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/1/11.
 */
public class LshRegexUtils {
    // 全网IP
    public static final String IP_REGEX = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";
    // 手机号码  (支持130——139、150——153、155——159、180、183、185、186、188、189号段)
    public static final String PHONE_NUMBER_REGEX = "^1(3\\d|5[012356789]|8[035689])\\d{8}$";
    // 邮箱 ("www."可省略不写)
    public static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+(\\.\\w+)+$";
    // 汉字 (个数限制为一个或多个)
    public static final String CHINESE_REGEX = "^[\u4e00-\u9f5a]+$";
    // 身份证号
    public static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";

    /**
     * 匹配邮箱账号，"www."可省略不写
     */
    public static boolean isEmail(String string) {
        return string.matches(EMAIL_REGEX);
    }

    /**
     * 匹配手机号码
     * 支持130——139、150——153、155——159、180、183、185、186、188、189号段
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
}
