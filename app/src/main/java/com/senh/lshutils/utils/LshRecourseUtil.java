package com.senh.lshutils.utils;

import android.graphics.drawable.Drawable;

import com.senh.lshutils.utils.Basic.LshApplicationUtils;

/**
 * Created by Senh Linsh on 17/1/11.
 */
public class LshRecourseUtil {

    public static String getString(int resId) {
        return LshApplicationUtils.getContext().getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return LshApplicationUtils.getContext().getResources().getStringArray(resId);
    }

    public static Drawable getDrawable(int resId) {
        return LshApplicationUtils.getContext().getResources().getDrawable(resId);
    }

    public static int getColor(int resId) {
        return LshApplicationUtils.getContext().getResources().getColor(resId);
    }

    public static int getDimens(int resId) {
        return LshApplicationUtils.getContext().getResources().getDimensionPixelSize(resId);
    }
}
