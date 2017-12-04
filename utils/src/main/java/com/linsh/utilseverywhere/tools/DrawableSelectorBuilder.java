package com.linsh.utilseverywhere.tools;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/12/04
 *    desc   : 图像选择器 的构建辅助类
 * </pre>
 */
public class DrawableSelectorBuilder {

    private final StateListDrawable mStateListDrawable;

    public DrawableSelectorBuilder() {
        mStateListDrawable = new StateListDrawable();
    }

    /**
     * 设置选择状态下的颜色
     *
     * @param color 颜色值
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setSelectedColor(int color) {
        addState(new int[]{android.R.attr.state_selected}, color);
        return this;
    }

    /**
     * 设置可用状态下的颜色
     *
     * @param color 颜色值
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setEnabledColor(int color) {
        addState(new int[]{android.R.attr.state_enabled}, color);
        return this;
    }

    /**
     * 设置触按状态下的颜色
     *
     * @param color 颜色值
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setPressedColor(int color) {
        addState(new int[]{android.R.attr.state_pressed}, color);
        return this;
    }

    /**
     * 设置其余状态下的颜色
     *
     * @param color 颜色值
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setOtherStateColor(int color) {
        addState(new int[]{}, color);
        return this;
    }

    /**
     * 设置选择状态下的图像
     *
     * @param drawable Drawable 对象
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setSelectedDrawable(Drawable drawable) {
        addState(new int[]{android.R.attr.state_selected}, drawable);
        return this;
    }

    /**
     * 设置可用状态下的图像
     *
     * @param drawable Drawable 对象
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setEnabledDrawable(Drawable drawable) {
        addState(new int[]{android.R.attr.state_enabled}, drawable);
        return this;
    }

    /**
     * 设置触按状态下的图像
     *
     * @param drawable Drawable 对象
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setPressedDrawable(Drawable drawable) {
        addState(new int[]{android.R.attr.state_pressed}, drawable);
        return this;
    }

    /**
     * 设置其余状态下的图像
     *
     * @param drawable Drawable 对象
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder setOtherStateDrawable(Drawable drawable) {
        addState(new int[]{}, drawable);
        return this;
    }

    /**
     * 添加指定状态下的颜色
     *
     * @param stateSet 指定状态集合
     * @param color    颜色值
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder addState(int[] stateSet, int color) {
        mStateListDrawable.addState(stateSet, new ColorDrawable(color));
        return this;
    }

    /**
     * 添加指定状态下的图像
     *
     * @param stateSet 指定状态集合
     * @param drawable Drawable 对象
     * @return DrawableSelectorBuilder
     */
    public DrawableSelectorBuilder addState(int[] stateSet, Drawable drawable) {
        mStateListDrawable.addState(stateSet, drawable);
        return this;
    }

    /**
     * 获取图像选择器
     *
     * @return StateListDrawable
     */
    public StateListDrawable getSelector() {
        return mStateListDrawable;
    }
}