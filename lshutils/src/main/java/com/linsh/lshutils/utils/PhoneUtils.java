package com.linsh.lshutils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 手机相关
 * </pre>
 */
public class PhoneUtils {

    /**
     * 判断当前设备是否为手机
     *
     * @return 是否为手机
     */
    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) ContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取手机 IMEI 码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI 码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) ContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取 IMSI 码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMSI 码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) ContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取 IP 地址
     *
     * @return IP 地址
     */
    public static String getIp() {
        return NetworkUtils.getIPAddress();
    }


    /**
     * 获取设备厂商, 如: Xiaomi
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备品牌
     *
     * @return 设备品牌
     */
    public static String getMobileBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    public static String getMobileModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim();
        } else {
            model = "";
        }
        return model;
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

    /**
     * 获取设备硬件信息
     *
     * @return 硬件信息
     */
    public static List<String> getMobileInfo() {
        ArrayList<String> list = new ArrayList<>();
        // 通过反射获取系统的硬件信息
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                //暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                list.add(name + "=" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
