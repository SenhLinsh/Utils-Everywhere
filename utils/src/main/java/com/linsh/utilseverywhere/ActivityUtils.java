package com.linsh.utilseverywhere;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Activity 相关
 * </pre>
 */
public class ActivityUtils {

    private ActivityUtils() {
    }

    /**
     * 获取栈顶 Activity 的名称
     */
    public static String getTopActivityName() {
        return AppUtils.getTopActivityName();
    }

}
