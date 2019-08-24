package com.linsh.utilseverywhere;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 手机相关
 *
 *            该类已过时, 相关方法将转移到 {@link DeviceUtils}
 * </pre>
 */
@Deprecated
public class PhoneUtils {

    private PhoneUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * see {@link DeviceUtils#isTelephonyDevice()}
     */
    public static boolean isPhone() {
        return DeviceUtils.isTelephonyDevice();
    }

    /**
     * see {@link DeviceUtils#getIMEI()}
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        return DeviceUtils.getIMEI();
    }

    /**
     * see {@link DeviceUtils#getIMSI()}
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI() {
        return DeviceUtils.getIMSI();
    }

    /**
     * see {@link DeviceUtils#getManufacturer()}
     */
    public static String getManufacturer() {
        return DeviceUtils.getManufacturer();
    }

    /**
     * see {@link DeviceUtils#getMobileBrand()}
     */
    public static String getMobileBrand() {
        return DeviceUtils.getMobileBrand();
    }

    /**
     * see {@link DeviceUtils#getMobileModel()}
     */
    public static String getMobileModel() {
        return DeviceUtils.getMobileModel();
    }

    /**
     * see {@link DeviceUtils#getDeviceInfo()}
     */
    public static List<String> getMobileInfo() {
        return DeviceUtils.getDeviceInfo();
    }

    /**
     * 获取 SDK 版本号
     *
     * @return SDK 版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取 SDK 版本名称
     *
     * @return SDK 版本名称
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }
}
