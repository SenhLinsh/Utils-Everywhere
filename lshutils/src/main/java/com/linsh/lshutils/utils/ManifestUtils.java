package com.linsh.lshutils.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 清单文件相关
 * </pre>
 */
public class ManifestUtils {

    /**
     * 获取清单文件中 MetaData 的 String 值
     *
     * @param key MetaData 的 key
     * @return MetaData 的 String 值
     */
    public static String getStringMetaData(String key) {
        Object metaData = getMetaData(key);
        if (metaData instanceof String) {
            return (String) metaData;
        }
        return null;
    }

    /**
     * 获取清单文件中 MetaData 的 int 值
     *
     * @param key MetaData 的 key
     * @return MetaData 的 int 值
     */
    public static int getIntMetaData(String key) {
        return getIntMetaData(key, 0);
    }

    /**
     * 获取清单文件中 MetaData 的 int 值
     *
     * @param key      MetaData 的 key
     * @param defValue 默认值
     * @return MetaData 的 int 值
     */
    public static int getIntMetaData(String key, int defValue) {
        Object metaData = getMetaData(key);
        if (metaData instanceof Integer) {
            return (int) metaData;
        }
        return defValue;
    }

    /**
     * 获取清单文件中 MetaData 的 boolean 值
     *
     * @param key MetaData 的 key
     * @return MetaData 的 boolean 值
     */
    public static boolean getBooleanMetaData(String key) {
        return getBooleanMetaData(key, false);
    }

    /**
     * 获取清单文件中 MetaData 的 boolean 值
     *
     * @param key      MetaData 的 key
     * @param defValue 默认值
     * @return MetaData 的 boolean 值
     */
    public static boolean getBooleanMetaData(String key, boolean defValue) {
        Object metaData = getMetaData(key);
        if (metaData instanceof Boolean) {
            return (boolean) metaData;
        }
        return defValue;
    }

    /**
     * 获取清单文件中 MetaData 的值
     *
     * @param key MetaData 的 key
     * @return MetaData 的值
     */
    public static Object getMetaData(String key) {
        try {
            ApplicationInfo aiApplicationInfo = ApplicationUtils.getContext().getPackageManager().getApplicationInfo(
                    ApplicationUtils.getContext().getPackageName(), PackageManager.GET_META_DATA);
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
