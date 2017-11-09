package com.linsh.lshutils.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by Senh Linsh on 17/1/11.
 */
public class LshResourceUtils {

    public static Resources getResources() {
        return LshApplicationUtils.getContext().getResources();
    }

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
