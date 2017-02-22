package com.linsh.lshutils.utils;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by Senh Linsh on 16/7/7.
 */
public class LshAnimationUtils {

    private static ObjectAnimator moveObjY(Object view, String propertyName, int time, float... value) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, propertyName, value);
        oa.setDuration(time);
        return oa;
    }

    public static ObjectAnimator moveY(View view, int time, float... value) {
        return moveObjY(view, "TranslationY", time, value);
    }

    public static ObjectAnimator moveX(View view, int time, float... value) {
        return moveObjY(view, "TranslationX", time, value);
    }
}
