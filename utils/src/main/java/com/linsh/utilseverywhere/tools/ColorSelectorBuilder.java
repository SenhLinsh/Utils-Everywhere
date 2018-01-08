package com.linsh.utilseverywhere.tools;

import android.content.res.ColorStateList;

import com.linsh.utilseverywhere.ListUtils;

import java.util.ArrayList;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/12/04
 *    desc   : 颜色选择器 构建辅助类
 * </pre>
 */
public class ColorSelectorBuilder {

    private ArrayList<int[]> mStates;
    private final ArrayList<Integer> mColors;

    public ColorSelectorBuilder() {
        mStates = new ArrayList<>();
        mColors = new ArrayList<>();
    }

    /**
     * 设置选择状态下的颜色
     *
     * @param color 颜色值
     * @return ColorSelectorBuilder
     */
    public ColorSelectorBuilder setSelectedColor(int color) {
        mStates.add(new int[]{android.R.attr.state_selected});
        mColors.add(color);
        return this;
    }

    /**
     * 设置可用状态下的颜色
     *
     * @param color 颜色值
     * @return ColorSelectorBuilder
     */
    public ColorSelectorBuilder setEnabledColor(int color) {
        mStates.add(new int[]{android.R.attr.state_enabled});
        mColors.add(color);
        return this;
    }

    /**
     * 设置按下状态下的颜色
     *
     * @param color 颜色值
     * @return ColorSelectorBuilder
     */
    public ColorSelectorBuilder setPressedColor(int color) {
        mStates.add(new int[]{android.R.attr.state_pressed});
        mColors.add(color);
        return this;
    }

    /**
     * 设置其余状态下的颜色
     *
     * @param color 颜色值
     * @return ColorSelectorBuilder
     */
    public ColorSelectorBuilder setOtherStateColor(int color) {
        mStates.add(new int[]{});
        mColors.add(color);
        return this;
    }

    /**
     * 设置指定状态下的颜色
     *
     * @param color 颜色值
     * @return ColorSelectorBuilder
     */
    public ColorSelectorBuilder addState(int[] stateSet, int color) {
        mStates.add(stateSet);
        mColors.add(color);
        return this;
    }

    /**
     * 获取构建好的颜色选择器
     *
     * @return ColorStateList
     */
    public ColorStateList getSelector() {
        int[][] states = ListUtils.toArray(mStates, int[].class);
        int[] colors = ListUtils.toIntArray(mColors);
        return new ColorStateList(states, colors);
    }
}