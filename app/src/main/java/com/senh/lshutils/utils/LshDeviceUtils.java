package com.senh.lshutils.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Senh Linsh on 16/10/21.
 */
public class LshDeviceUtils {

    /**
     * 判断是否平板设备
     * @param context
     * @return true:平板,false:手机
     */
    private static boolean isPhoneDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) <
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
