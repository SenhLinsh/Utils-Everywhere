package com.senh.lshutils.utils;

import android.content.Context;
import android.widget.Toast;

/** 弹吐司工具类 */
public class LshToastUtils {

	public static void show(Context context, String text) {
		Toast.makeText(context, text, 0).show();
	}

	public static void showLong(Context context, String text) {
		Toast.makeText(context, text, 1).show();
	}
}