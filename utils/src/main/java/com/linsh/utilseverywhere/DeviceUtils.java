package com.linsh.utilseverywhere;

import android.content.res.Configuration;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 设备相关
 * </pre>
 */
public class DeviceUtils {

    private DeviceUtils() {
    }

    /**
     * 判断是否为手机设备
     *
     * @return true 为手机, false 为平板
     */
    public static boolean isPhoneDevice() {
        return (ContextUtils.get().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
