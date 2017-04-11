package com.linsh.lshutils.utils;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

/**
 * Created by Senh Linsh on 17/2/20.
 */

public class LshUnitConverseUtils {

    public static int dp2px(int dp) {
        float density = LshApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public static int dp2px(float dp) {
        float density = LshApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public static int px2dp(int px) {
        float density = LshApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = LshApplicationUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(float spValue) {
        final float fontScale = LshApplicationUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
