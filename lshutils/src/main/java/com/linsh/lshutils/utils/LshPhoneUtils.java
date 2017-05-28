package com.linsh.lshutils.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Senh Linsh on 17/2/21.
 */

public class LshPhoneUtils {

    /**
     * 获取ip地址
     */
    public static String getIp() {
        Context context = LshApplicationUtils.getContext().getApplicationContext();
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 获取IP
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        String no_ip = "";
        if (no_ip.equals(ip)) {
            ip = "0.0.0.0";
        }
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
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
        return Build.MODEL;
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
