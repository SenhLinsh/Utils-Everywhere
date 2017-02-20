package com.linsh.lshutils.utils.Basic;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Senh Linsh on 16/11/11.
 */
public class LshSharedPreferenceUtils {
    private static SharedPreferences sp;

    private static void getSharedPreferences() {
        if (sp == null) {
            Context context = LshApplicationUtils.getContext();
            sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        }
    }

    public static String getString(String key) {
        getSharedPreferences();
        return sp.getString(key, "");
    }

    public static String getString(String key, String defValue) {
        getSharedPreferences();
        return sp.getString(key, defValue);
    }

    public static void putString(String key, String value) {
        getSharedPreferences();
        sp.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        getSharedPreferences();
        return sp.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        getSharedPreferences();
        return sp.getBoolean(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPreferences();
        sp.edit().putBoolean(key, value).apply();
    }

    public static int getInt(String key) {
        getSharedPreferences();
        return sp.getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        getSharedPreferences();
        return sp.getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        getSharedPreferences();
        sp.edit().putInt(key, value).apply();
    }

    public static void remove(String key) {
        getSharedPreferences();
        sp.edit().remove(key).apply();
    }
}