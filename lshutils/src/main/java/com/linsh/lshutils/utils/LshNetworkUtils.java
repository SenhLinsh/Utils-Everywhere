package com.linsh.lshutils.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Senh Linsh on 17/4/11.
 */

public class LshNetworkUtils {

    /**
     * 检查是否有网络
     */
    public static boolean isAvailable() {
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected() {
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 判断移动数据是否打开
     */
    public static boolean getDataEnabled() {
        try {
            TelephonyManager tm = (TelephonyManager) LshContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查是否是WIFI
     */
    public static boolean isWifi() {
        NetworkInfo info = getNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否是移动网络
     */
    public static boolean isMobile() {
        NetworkInfo info = getNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMobile4G() {
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 判断wifi是否打开
     */
    public static boolean isWifiEnabled() {
        WifiManager wifiManager = (WifiManager) LshContextUtils.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    /**
     * 判断wifi是否连接状态
     */
    public static boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) LshContextUtils.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开或关闭wifi
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>}</p>
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public static void setWifiEnabled(final boolean enabled) {
        WifiManager wifiManager = (WifiManager) LshContextUtils.getSystemService(Context.WIFI_SERVICE);
        if (enabled) {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
        } else {
            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }
        }
    }

    /**
     * 获取网络运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) LshContextUtils.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 获取IP地址
     */
    public static String getIPAddress() {
        return getIPAddress(true);
    }

    /**
     * 获取IP地址
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NetworkInfo getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) LshContextUtils.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
