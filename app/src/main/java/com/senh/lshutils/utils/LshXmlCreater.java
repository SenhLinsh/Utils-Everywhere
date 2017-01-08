package com.senh.lshutils.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 代码生成XML文件或属性的辅助类
 * <p/>
 * Created by Senh Linsh on 16/12/21.
 */
public class LshXmlCreater {

    /**
     * 代码生成矩形
     *
     * @param fillColor   内部填充颜色
     * @param roundRadius 圆角半径
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable createRectangleShape(int fillColor, float roundRadius, int strokeWidth, int strokeColor) {
        GradientDrawable gradientDrawable = createRectangleShape(fillColor, roundRadius);
        gradientDrawable.setStroke(strokeWidth, strokeColor); //边框宽度, 边框颜色
        return gradientDrawable;
    }

    public static GradientDrawable createRectangleShape(int fillColor, float roundRadius) {
        GradientDrawable gradientDrawable = createRectangleShape(fillColor);
        gradientDrawable.setCornerRadius(roundRadius); // 圆角半径
        return gradientDrawable;
    }

    public static GradientDrawable createRectangleShape(int fillColor) {
        GradientDrawable gradientDrawable = createRectangleShape();
        gradientDrawable.setColor(fillColor); // 内部填充颜色
        return gradientDrawable;
    }

    private static GradientDrawable createRectangleShape() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形边框
     *
     * @param roundRadius 圆角半径
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @return
     */
    public static GradientDrawable createRectangleBorder(float roundRadius, int strokeWidth, int strokeColor) {
        GradientDrawable gradientDrawable = createRectangleShape();
        gradientDrawable.setCornerRadius(roundRadius);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }

    /**
     * 生成背景选择器
     */
    public static StateListDrawable createPressedSelector(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    public static StateListDrawable createPressedSelector(int pressedColor, int normalColor) {
        return createPressedSelector(new ColorDrawable(pressedColor), new ColorDrawable(normalColor));
    }

    /**
     * 生成背景选择器
     */
    public static StateListDrawable createSelectedSelector(Drawable selectedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    public static StateListDrawable createSelectedSelector(int selectedColor, int normalColor) {
        return createPressedSelector(new ColorDrawable(selectedColor), new ColorDrawable(normalColor));
    }

    /**
     * 生成背景选择器
     */
    public static StateListDrawable createEnabledSelector(Drawable enabledDrawable, Drawable disabledDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, enabledDrawable);
        stateListDrawable.addState(new int[]{}, disabledDrawable);
        return stateListDrawable;
    }

    public static StateListDrawable createEnabledSelector(int enabledColor, int disabledColor) {
        return createPressedSelector(new ColorDrawable(enabledColor), new ColorDrawable(disabledColor));
    }

    /**
     * 生成颜色选择器
     */
    public static ColorStateList createPressedColorSelector(int pressedColor, int normalColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{}};
        int[] colors = new int[]{pressedColor, normalColor};
        return new ColorStateList(states, colors);
    }

    /**
     * 生成颜色选择器
     */
    public static ColorStateList createSelectedColorSelector(int selectedColor, int normalColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{}};
        int[] colors = new int[]{selectedColor, normalColor};
        return new ColorStateList(states, colors);
    }

    /**
     * 生成颜色选择器
     */
    public static ColorStateList createEnabledColorSelector(int enabledColor, int disabledColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{}};
        int[] colors = new int[]{enabledColor, disabledColor};
        return new ColorStateList(states, colors);
    }
}
