package com.linsh.utilseverywhere;

import android.Manifest;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 判断是否为通讯设备, 即是否可以打电话
     *
     * @return 是否为通讯设备
     */
    public static boolean isTelephonyDevice() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取屏幕尺寸类型
     * <p>
     * {@link Configuration#SCREENLAYOUT_SIZE_SMALL} : 至少约为 320x426 dp
     * {@link Configuration#SCREENLAYOUT_SIZE_NORMAL} : 至少约为 320x470 dp
     * {@link Configuration#SCREENLAYOUT_SIZE_LARGE} : 至少约为 480x640 dp
     * {@link Configuration#SCREENLAYOUT_SIZE_XLARGE} : 至少约为 720x960 dp
     *
     * @return 屏幕尺寸类型
     */
    public static int getScreenSizeType() {
        return getContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
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
        return Build.MODEL;
    }

    /**
     * 获取手机 IMEI 码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI 码
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String res = tm.getImei();
            if (!TextUtils.isEmpty(res))
                return res;
            res = tm.getMeid();
            if (!TextUtils.isEmpty(res))
                return res;
            return tm.getDeviceId();
        } else {
            return tm.getDeviceId();
        }
    }

    /**
     * 获取 IMSI 码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMSI 码
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取序列号, 如果存在的话
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getSerial() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Build.getSerial();
        } else {
            return Build.SERIAL;
        }
    }

    /**
     * 获取设备硬件信息
     *
     * @return 硬件信息
     */
    public static List<String> getDeviceInfo() {
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
