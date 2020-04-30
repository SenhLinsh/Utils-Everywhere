package com.linsh.utilseverywhere;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/04/30
 *    desc   : 字符相关
 * </pre>
 */
public final class CharacterUtils {

    /**
     * 是否全部字符都是小写字母
     *
     * @param text 需要进行匹配字符串
     */
    public static boolean isAllLowerCase(String text) {
        if (text == null || text.length() == 0)
            return false;
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isLowerCase(text.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * 是否其中一个字符是小写字母
     *
     * @param text 需要进行匹配字符串
     */
    public static boolean isAnyLowerCase(String text) {
        if (text == null || text.length() == 0)
            return false;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLowerCase(text.charAt(i)))
                return true;
        }
        return false;
    }

    /**
     * 是否不包含小写字母
     *
     * @param text 需要进行匹配字符串
     */
    public static boolean isNoLowerCase(String text) {
        if (text == null || text.length() == 0)
            return true;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLowerCase(text.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * 是否全部字符都是大写字母
     *
     * @param text 需要进行匹配字符串
     */
    public static boolean isAllUpperCase(String text) {
        if (text == null || text.length() == 0)
            return false;
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isUpperCase(text.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * 是否其中一个字符是大写字母
     *
     * @param text 需要进行匹配字符串
     */
    public static boolean isAnyUpperCase(String text) {
        if (text == null || text.length() == 0)
            return false;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i)))
                return true;
        }
        return false;
    }

    /**
     * 是否不包含大写字母
     *
     * @param text 需要进行匹配字符串
     */
    public static boolean isNoUpperCase(String text) {
        if (text == null || text.length() == 0)
            return true;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i)))
                return false;
        }
        return true;
    }
}
