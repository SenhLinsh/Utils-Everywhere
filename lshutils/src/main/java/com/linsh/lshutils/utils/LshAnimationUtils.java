package com.linsh.lshutils.utils;

import android.animation.ObjectAnimator;
import android.view.View;


/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 动画相关
 * </pre>
 */
public class LshAnimationUtils {

    /**
     * 通过 ObjectAnimator 控制指定属性的 float 值的变化
     *
     * @param target       指定的对象
     * @param propertyName 属性名
     * @param time         持续时间
     * @param value        控制变化的值的集合
     * @return ObjectAnimator 对象
     */
    private static ObjectAnimator animateFloatProperty(Object target, String propertyName, int time, float... value) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(target, propertyName, value);
        oa.setDuration(time);
        return oa;
    }

    /**
     * 控制 View 对象的 Y 方向移动
     *
     * @param view  指定的 View 对象
     * @param time  持续时间
     * @param value 变化的值集合
     * @return ObjectAnimator 对象
     */
    public static ObjectAnimator moveY(View view, int time, float... value) {
        return animateFloatProperty(view, "TranslationY", time, value);
    }

    /**
     * 控制 View 对象的 X 方向移动
     *
     * @param view  指定的 View 对象
     * @param time  持续时间
     * @param value 变化的值集合
     * @return ObjectAnimator 对象
     */
    public static ObjectAnimator moveX(View view, int time, float... value) {
        return animateFloatProperty(view, "TranslationX", time, value);
    }
}
