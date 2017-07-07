package com.linsh.lshutils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Senh Linsh on 17/2/21.
 */

public class LshPhoneUtils {

    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) LshContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) LshContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取IMSI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) LshContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取ip地址
     */
    public static String getIp() {
        return LshNetworkUtils.getIPAddress();
    }

    /**
     * 获取设备厂商, 如Xiaomi
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机品牌
     */
    public static String getMobileBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
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
     * 获取SDK版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取SDK版本名称
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机的硬件信息
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
