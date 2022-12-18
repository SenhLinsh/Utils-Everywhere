package com.linsh.utilseverywhere;

import android.content.res.ColorStateList;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
public class ColorSelectors {

    /**
     * 生成触碰状态下的颜色选择器
     *
     * @param pressedColor 触碰状态时显示的颜色
     * @param normalColor  其他状态时显示的颜色
     * @return 颜色状态选择器
     */
    public static ColorStateList pressed(int pressedColor, int normalColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_pressed}, new int[]{}};
        int[] colors = new int[]{pressedColor, normalColor};
        return new ColorStateList(states, colors);
    }

    /**
     * 生成选择状态下的颜色选择器
     *
     * @param selectedColor 选择状态时显示的颜色
     * @param normalColor   其他状态时显示的颜色
     * @return 颜色状态选择器
     */
    public static ColorStateList selected(int selectedColor, int normalColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_selected}, new int[]{}};
        int[] colors = new int[]{selectedColor, normalColor};
        return new ColorStateList(states, colors);
    }

    /**
     * 生成可用/不可用状态下的颜色选择器
     *
     * @param enabledColor  可用状态时显示的颜色
     * @param disabledColor 不可用状态时显示的颜色
     * @return 颜色状态选择器
     */
    public static ColorStateList enabled(int enabledColor, int disabledColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{}};
        int[] colors = new int[]{enabledColor, disabledColor};
        return new ColorStateList(states, colors);
    }
}
