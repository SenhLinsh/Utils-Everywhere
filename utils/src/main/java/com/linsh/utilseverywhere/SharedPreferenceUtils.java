package com.linsh.utilseverywhere;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.SoftReference;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: SharedPreference 相关
 * </pre>
 */
public class SharedPreferenceUtils {

    private SharedPreferenceUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * SharedPreferences 软引用
     */
    private static SoftReference<SharedPreferences> sReference = new SoftReference<>(null);

    public static SharedPreferences getSharedPreferences() {
        SharedPreferences sp = sReference.get();
        if (sp == null) {
            sp = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
            sReference = new SoftReference<>(sp);
        }
        return sp;
    }

    /**
     * 获取 String 类型配置
     *
     * @param key key
     * @return String 值
     */
    public static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    /**
     * 获取 String 类型配置
     *
     * @param key      key
     * @param defValue 默认值
     * @return String 值
     */
    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    /**
     * 保存 String 类型配置
     *
     * @param key   key
     * @param value 值
     */
    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    /**
     * 获取 boolean 类型配置
     *
     * @param key key
     * @return boolean 值
     */
    public static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    /**
     * 获取 boolean 类型配置
     *
     * @param key      key
     * @param defValue 默认值
     * @return boolean 值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 保存 boolean 类型配置
     *
     * @param key   key
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    /**
     * 获取 int 类型配置
     *
     * @param key key
     * @return int 值
     */
    public static int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    /**
     * 获取 int 类型配置
     *
     * @param key      key
     * @param defValue 默认值
     * @return int 值
     */
    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    /**
     * 保存 int 类型配置
     *
     * @param key   key
     * @param value 值
     */
    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    /**
     * 获取 long 类型配置
     *
     * @param key key
     * @return long 值
     */
    public static long getLong(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    /**
     * 获取 long 类型配置
     *
     * @param key      key
     * @param defValue 默认值
     * @return long 值
     */
    public static long getLong(String key, long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }

    /**
     * 保存 long 类型配置
     *
     * @param key   key
     * @param value 值
     */
    public static void putLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    /**
     * 移除配置
     *
     * @param key key
     */
    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    /**
     * 判断是否存在该配置
     *
     * @param key key
     * @return 是否存在
     */
    public boolean contains(String key) {
        return getSharedPreferences().contains(key);
    }
}