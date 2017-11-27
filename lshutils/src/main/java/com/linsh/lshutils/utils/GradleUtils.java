package com.linsh.lshutils.utils;

import java.lang.reflect.Field;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: Gradle 相关
 * </pre>
 */
public class GradleUtils {

    /**
     * 通过反射获取 BuildConfig 指定属性的值
     * 注意1: 如果是当前 module 的 BuildConfig, 可以直接获取, 此方法旨在从依赖库中获取 app 的 BuildConfig 值
     * 注意2: 如果 gradle 配置了 applicationIdSuffix 添加包名后缀, 会无法通过反射找到文件
     *
     * @param fieldName 字段名
     * @return 字段值
     */
    public static Object getBuildConfigValue(String fieldName) {
        try {
            String packageName = ApplicationUtils.getPackageName();
            Class<?> clazz = Class.forName(packageName + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过反射获取 BuildConfig 指定属性的 boolean 值
     *
     * @param fieldName 字段名
     * @param defValue  默认值 (获取失败的情况下使用)
     * @return 字段值
     */
    public static boolean getBuildConfigBooleanValue(String fieldName, boolean defValue) {
        Object configValue = getBuildConfigValue(fieldName);
        if (configValue instanceof Boolean) {
            return (boolean) configValue;
        }
        return defValue;
    }

    /**
     * 通过反射获取 BuildConfig 指定属性的 int 值
     *
     * @param fieldName 字段名
     * @return 字段值
     */
    public static int getBuildConfigIntValue(String fieldName) {
        Object configValue = getBuildConfigValue(fieldName);
        if (configValue instanceof Integer) {
            return (int) configValue;
        }
        return 0;
    }

    /**
     * 通过反射获取 BuildConfig 指定属性的 String 值
     *
     * @param fieldName 字段名
     * @return 字段值
     */
    public static String getBuildConfigStringValue(String fieldName) {
        Object configValue = getBuildConfigValue(fieldName);
        if (configValue instanceof String) {
            return (String) configValue;
        }
        return null;
    }
}
