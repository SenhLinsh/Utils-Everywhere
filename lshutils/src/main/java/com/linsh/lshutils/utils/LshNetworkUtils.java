package com.linsh.lshutils.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

/**
 * Created by Senh Linsh on 17/4/11.
 */

public class LshNetworkUtils {

    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable() {
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isAvailable();
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

    private static NetworkInfo getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) LshApplicationUtils.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
