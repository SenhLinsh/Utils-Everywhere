package com.linsh.lshutils.utils.Basic;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.SoftReference;

/**
 * Created by Senh Linsh on 16/11/11.
 */
public class LshSharedPreferenceUtils {

    private static SoftReference<SharedPreferences> sReference = new SoftReference<>(null);

    public static SharedPreferences getSharedPreferences() {
        SharedPreferences sp = sReference.get();
        if (sp == null) {
            Context context = LshApplicationUtils.getContext();
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
            sReference = new SoftReference<>(sp);
        }
        return sp;
    }

    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public static int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    public static long getLong(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public static long getLong(String key, long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }

    public static void putLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    public boolean contains(String key) {
        return getSharedPreferences().contains(key);
    }
}