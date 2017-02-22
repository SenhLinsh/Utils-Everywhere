package com.linsh.lshutils.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

/**
 * Created by Senh Linsh on 17/2/21.
 */

public class LshPhoneUtils {

    /**
     * 获取ip地址
     */
    public static String getIp() {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) LshApplicationUtils.getContext().getSystemService(Context.WIFI_SERVICE);
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
}
