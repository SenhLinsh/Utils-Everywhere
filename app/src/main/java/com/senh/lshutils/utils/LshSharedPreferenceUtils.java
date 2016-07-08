package com.senh.lshutils.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LshSharedPreferenceUtils {
	private static SharedPreferences sp;

	private static void getSharedPreferences(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		}
	}

	public static String getString(Context context, String key) {
		getSharedPreferences(context);
		return sp.getString(key, "");
	}

	public static String getString(Context context, String key, String defValue) {
		getSharedPreferences(context);
		return sp.getString(key, defValue);
	}

	public static void putString(Context context, String key, String value) {
		getSharedPreferences(context);
		sp.edit().putString(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key) {
		getSharedPreferences(context);
		return sp.getBoolean(key, false);
	}

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		getSharedPreferences(context);
		return sp.getBoolean(key, defValue);
	}

	public static void putBoolean(Context context, String key, Boolean value) {
		getSharedPreferences(context);
		sp.edit().putBoolean(key, value).commit();
	}

	public static int getInt(Context context, String key) {
		getSharedPreferences(context);
		return sp.getInt(key, 0);
	}

	public static int getInt(Context context, String key, int defValue) {
		getSharedPreferences(context);
		return sp.getInt(key, defValue);
	}

	public static void putInt(Context context, String key, int value) {
		getSharedPreferences(context);
		sp.edit().putInt(key, value).commit();
	}

	public static void remove(Context context, String key) {
		getSharedPreferences(context);
		sp.edit().remove(key).commit();
	}
}