package com.linsh.lshutils.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

import static com.linsh.lshutils.utils.LshScreenUtils.Orientation.LANDSCAPE;
import static com.linsh.lshutils.utils.LshScreenUtils.Orientation.PORTRAIT;

/**
 * Created by Senh Linsh on 17/3/30.
 */

public class LshScreenUtils {

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) LshApplicationUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) LshApplicationUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenShortSize() {
        DisplayMetrics outMetrics = LshApplicationUtils.getContext().getResources().getDisplayMetrics();
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    public static int getScreenLongSize() {
        DisplayMetrics outMetrics = LshApplicationUtils.getContext().getResources().getDisplayMetrics();
        return Math.max(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    public static int getScreenRotation() {
        WindowManager wm = (WindowManager) LshApplicationUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getRotation();
    }

    public static Orientation getScreenOritation() {
        int rotation = getScreenRotation();
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            return PORTRAIT;
        } else {
            return LANDSCAPE;
        }
    }

    public enum Orientation {
        LANDSCAPE, PORTRAIT
    }
}
