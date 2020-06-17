package com.linsh.utilseverywhere;

import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/17
 *    desc   :
 * </pre>
 */
public class ExceptionUtils {

    /**
     * 检查对象为空时抛异常
     *
     * @param object  检查对象
     * @param message 异常信息
     */
    public static <T> T checkNotNull(@Nullable T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
