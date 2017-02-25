package com.linsh.lshutils.utils;

import android.content.res.Configuration;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

/**
 * Created by Senh Linsh on 16/10/21.
 */
public class LshDeviceUtils {

    /**
     * 判断是否平板设备
     *
     * @return true:平板,false:手机
     */
    public static boolean isPhoneDevice() {
        return (LshApplicationUtils.getContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
