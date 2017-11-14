package com.linsh.lshutils.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 直接获取 Resources 资源类, 避免 Context 的获取已经多步调用的麻烦
 * </pre>
 */
public class LshResourceUtils {

    /**
     * 获取资源对象
     *
     * @return 资源对象
     */
    public static Resources getResources() {
        return LshApplicationUtils.getContext().getResources();
    }

    /**
     * 读取资源字符串
     *
     * @param resId 资源 id
     * @return 字符串
     */
    public static String getString(int resId) {
        return LshApplicationUtils.getContext().getResources().getString(resId);
    }

    /**
     * 读取资源字符串数组
     *
     * @param resId 资源 id
     * @return 字符串数组
     */
    public static String[] getStringArray(int resId) {
        return LshApplicationUtils.getContext().getResources().getStringArray(resId);
    }

    /**
     * 读取资源图片
     *
     * @param resId 资源 id
     * @return Drawable 对象
     */
    public static Drawable getDrawable(int resId) {
        return LshApplicationUtils.getContext().getResources().getDrawable(resId);
    }

    /**
     * 读取资源颜色
     *
     * @param resId 资源 id
     * @return 颜色值
     */
    public static int getColor(int resId) {
        return LshApplicationUtils.getContext().getResources().getColor(resId);
    }

    /**
     * 读取资源 Dimens
     *
     * @param resId 资源 id
     * @return Dimens 值
     */
    public static int getDimens(int resId) {
        return LshApplicationUtils.getContext().getResources().getDimensionPixelSize(resId);
    }
}
