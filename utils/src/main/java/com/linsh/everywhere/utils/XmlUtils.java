package com.linsh.everywhere.utils;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 工具类: XML 相关
 *             API  : 代码生成XML文件或属性 等
 * </pre>
 */
public class XmlUtils {

    /**
     * 代码生成矩形 Drawable
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

    /**
     * 代码生成矩形 Drawable
     *
     * @param fillColor   内部填充颜色
     * @param roundRadius 圆角半径
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable createRectangleShape(int fillColor, float roundRadius) {
        GradientDrawable gradientDrawable = createRectangleShape(fillColor);
        gradientDrawable.setCornerRadius(roundRadius); // 圆角半径
        return gradientDrawable;
    }

    /**
     * 代码生成矩形 Drawable
     *
     * @param fillColor 内部填充颜色
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable createRectangleShape(int fillColor) {
        GradientDrawable gradientDrawable = createRectangleShape();
        gradientDrawable.setColor(fillColor); // 内部填充颜色
        return gradientDrawable;
    }

    /**
     * 代码生成矩形 Drawable
     *
     * @return 矩形对象, 其他参数可自行设定
     */
    private static GradientDrawable createRectangleShape() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形边框 Drawable
     *
     * @param radii 圆角半径, 长度大于等于 8 的数组, 包含 4 个角的 X 和 Y 的半径, 顺序分别为: 左上, 右上, 右下, 左下
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable createRectangleCorner(float[] radii) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(radii);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形边框 Drawable
     *
     * @param radii     圆角半径, 长度大于等于 8 的数组, 包含 4 个角的 X 和 Y 的半径, 顺序分别为: 左上, 右上, 右下, 左下
     * @param fillColor 内部填充颜色
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable createRectangleCorner(float[] radii, int fillColor) {
        GradientDrawable rectangleCorner = createRectangleCorner(radii);
        rectangleCorner.setColor(fillColor);
        return rectangleCorner;
    }

    /**
     * 代码生成矩形边框 Drawable
     *
     * @param radii 圆角半径, 长度大于等于 8 的数组, 包含 4 个角的 X 和 Y 的半径, 顺序分别为: 左上, 右上, 右下, 左下
     * @param color 内部填充颜色
     * @return 矩形对象, 其他参数可自行设定
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static GradientDrawable createRectangleCorner(float[] radii, ColorStateList color) {
        GradientDrawable rectangleCorner = createRectangleCorner(radii);
        rectangleCorner.setColor(color);
        return rectangleCorner;
    }

    /**
     * 代码生成矩形边框 Drawable
     *
     * @param roundRadius 圆角半径
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable createRectangleBorder(float roundRadius, int strokeWidth, int strokeColor) {
        GradientDrawable gradientDrawable = createRectangleShape();
        gradientDrawable.setCornerRadius(roundRadius);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }

    /**
     * 生成触碰状态下的背景选择器
     *
     * @param pressedDrawable 触碰状态时显示的图片
     * @param normalDrawable  其他状态时显示的图片
     * @return 状态选择图片
     */
    public static StateListDrawable createPressedSelector(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    /**
     * 生成触碰状态下的背景选择器
     *
     * @param pressedColor 触碰状态时显示的颜色
     * @param normalColor  其他状态时显示的颜色
     * @return 状态选择图片
     */
    public static StateListDrawable createPressedSelector(int pressedColor, int normalColor) {
        return createPressedSelector(new ColorDrawable(pressedColor), new ColorDrawable(normalColor));
    }

    /**
     * 生成选择状态下的背景选择器
     *
     * @param selectedDrawable 选择状态时显示的图片
     * @param normalDrawable   其他状态时显示的图片
     * @return 状态选择图片
     */
    public static StateListDrawable createSelectedSelector(Drawable selectedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    /**
     * 生成选择状态下的背景选择器
     *
     * @param selectedColor 选择状态时显示的颜色
     * @param normalColor   其他状态时显示的颜色
     * @return 状态选择图片
     */
    public static StateListDrawable createSelectedSelector(int selectedColor, int normalColor) {
        return createSelectedSelector(new ColorDrawable(selectedColor), new ColorDrawable(normalColor));
    }

    /**
     * 生成可用/不可用状态下的背景选择器
     *
     * @param enabledDrawable  可用状态时显示的图片
     * @param disabledDrawable 不可用状态时显示的图片
     * @return 状态选择图片
     */
    public static StateListDrawable createEnabledSelector(Drawable enabledDrawable, Drawable disabledDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, enabledDrawable);
        stateListDrawable.addState(new int[]{}, disabledDrawable);
        return stateListDrawable;
    }

    /**
     * 生成可用/不可用状态下的背景选择器
     *
     * @param enabledColor  可用状态时显示的颜色
     * @param disabledColor 不可用状态时显示的颜色
     * @return 状态选择图片
     */
    public static StateListDrawable createEnabledSelector(int enabledColor, int disabledColor) {
        return createPressedSelector(new ColorDrawable(enabledColor), new ColorDrawable(disabledColor));
    }

    /**
     * 生成触碰状态下的颜色选择器
     *
     * @param pressedColor 触碰状态时显示的颜色
     * @param normalColor  其他状态时显示的颜色
     * @return 颜色状态选择器
     */
    public static ColorStateList createPressedColorSelector(int pressedColor, int normalColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{}};
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
    public static ColorStateList createSelectedColorSelector(int selectedColor, int normalColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{}};
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
    public static ColorStateList createEnabledColorSelector(int enabledColor, int disabledColor) {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}, new int[]{}};
        int[] colors = new int[]{enabledColor, disabledColor};
        return new ColorStateList(states, colors);
    }
}
