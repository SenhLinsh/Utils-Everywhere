package com.linsh.lshutils.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by Senh Linsh on 17/2/20.
 */
public class LshManifestUtils {

    public static String getStringMetaData(String key) {
        Object metaData = getMetaData(key);
        if (metaData instanceof String) {
            return (String) metaData;
        }
        return null;
    }

    public static int getIntMetaData(String key) {
        return getIntMetaData(key, 0);
    }

    public static int getIntMetaData(String key, int defValue) {
        Object metaData = getMetaData(key);
        if (metaData instanceof Integer) {
            return (int) metaData;
        }
        return defValue;
    }

    public static boolean getBooleanMetaData(String key) {
        return getBooleanMetaData(key, false);
    }

    public static boolean getBooleanMetaData(String key, boolean defValue) {
        Object metaData = getMetaData(key);
        if (metaData instanceof Boolean) {
            return (boolean) metaData;
        }
        return defValue;
    }

    public static Object getMetaData(String key) {
        try {
            ApplicationInfo aiApplicationInfo = LshApplicationUtils.getContext().getPackageManager().getApplicationInfo(
                    LshApplicationUtils.getContext().getPackageName(), PackageManager.GET_META_DATA);
            if (null != aiApplicationInfo) {
                if (null != aiApplicationInfo.metaData) {
                    return aiApplicationInfo.metaData.get(key);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
