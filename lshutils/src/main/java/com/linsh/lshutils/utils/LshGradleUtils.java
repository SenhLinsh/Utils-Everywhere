package com.linsh.lshutils.utils;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

import java.lang.reflect.Field;

/**
 * Created by Senh Linsh on 17/2/22.
 */

public class LshGradleUtils {

    /**
     * 通过反射获取BuildConfig属性值
     * 注意1: 如果是当前module的BuildConfig, 可以直接获取, 此方法旨在从依赖库中获取app的BuildConfig值
     * 注意2: 如果gradle配置了applicationIdSuffix添加包名后缀, 会无法通过反射找到文件
     */
    public static Object getBuildConfigValue(String fieldName) {
        try {
            String packageName = LshApplicationUtils.getRealPackageName();
            Class<?> clazz = Class.forName(packageName + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getBuildConfigBooleanValue(String fieldName, boolean defValue) {
        Object configValue = getBuildConfigValue(fieldName);
        if (configValue instanceof Boolean) {
            return (boolean) configValue;
        }
        return defValue;
    }

    public static int getBuildConfigIntValue(String fieldName) {
        Object configValue = getBuildConfigValue(fieldName);
        if (configValue instanceof Integer) {
            return (int) configValue;
        }
        return 0;
    }

    public static String getBuildConfigStringValue(String fieldName) {
        Object configValue = getBuildConfigValue(fieldName);
        if (configValue instanceof String) {
            return (String) configValue;
        }
        return null;
    }
}
