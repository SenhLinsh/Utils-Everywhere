package com.linsh.utilseverywhere;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.linsh.utilseverywhere.tools.DrawableSelectorBuilder;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   :
 * </pre>
 */
public class DrawableSelectors {

    private DrawableSelectors() {
    }

    public static DrawableSelectorBuilder build() {
        return new DrawableSelectorBuilder();
    }

    /**
     * 生成触碰状态下的背景选择器
     *
     * @param pressedDrawable 触碰状态时显示的图片
     * @param normalDrawable  其他状态时显示的图片
     * @return 状态选择图片
     */
    public static StateListDrawable pressed(Drawable pressedDrawable, Drawable normalDrawable) {
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
    public static StateListDrawable pressed(int pressedColor, int normalColor) {
        return pressed(new ColorDrawable(pressedColor), new ColorDrawable(normalColor));
    }

    /**
     * 生成选择状态下的背景选择器
     *
     * @param selectedDrawable 选择状态时显示的图片
     * @param normalDrawable   其他状态时显示的图片
     * @return 状态选择图片
     */
    public static StateListDrawable selected(Drawable selectedDrawable, Drawable normalDrawable) {
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
    public static StateListDrawable selected(int selectedColor, int normalColor) {
        return selected(new ColorDrawable(selectedColor), new ColorDrawable(normalColor));
    }

    /**
     * 生成可用/不可用状态下的背景选择器
     *
     * @param enabledDrawable  可用状态时显示的图片
     * @param disabledDrawable 不可用状态时显示的图片
     * @return 状态选择图片
     */
    public static StateListDrawable enabled(Drawable enabledDrawable, Drawable disabledDrawable) {
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
    public static StateListDrawable enabled(int enabledColor, int disabledColor) {
        return enabled(new ColorDrawable(enabledColor), new ColorDrawable(disabledColor));
    }
}
