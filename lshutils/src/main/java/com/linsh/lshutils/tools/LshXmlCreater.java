package com.linsh.lshutils.tools;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;

import com.linsh.lshutils.utils.LshArrayUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;


public class LshXmlCreater {

    public static ShapeBuilder createShape() {
        return new ShapeBuilder();
    }

    public static ColorSelectorBuilder createColorSelector() {
        return new ColorSelectorBuilder();
    }

    public static DrawableSelectorBuilder createDrawableSelector() {
        return new DrawableSelectorBuilder();
    }

    public static class ShapeBuilder {

        @IntDef({GradientDrawable.RECTANGLE, GradientDrawable.OVAL, GradientDrawable.LINE, GradientDrawable.RING})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Shape {
        }

        private final GradientDrawable mGradientDrawable;

        public ShapeBuilder() {
            mGradientDrawable = new GradientDrawable();
        }

        public ShapeBuilder setShape(@Shape int shape) {
            mGradientDrawable.setShape(shape);
            return this;
        }

        public ShapeBuilder setColor(int fillColor) {
            mGradientDrawable.setColor(fillColor);
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ShapeBuilder setColor(ColorStateList colorStateList) {
            mGradientDrawable.setColor(colorStateList);
            return this;
        }

        /**
         * 设置渐变颜色
         *
         * @param colors 至少两种颜色
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public ShapeBuilder setColors(int... colors) {
            mGradientDrawable.setColors(colors);
            return this;
        }

        public ShapeBuilder setStroke(int strokeWidth, int strokeColor) {
            mGradientDrawable.setStroke(strokeWidth, strokeColor);
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ShapeBuilder setStroke(int width, ColorStateList colorStateList) {
            mGradientDrawable.setStroke(width, colorStateList);
            return this;
        }

        public ShapeBuilder setCornerRadius(float radius) {
            mGradientDrawable.setCornerRadius(radius);
            return this;
        }

        /**
         * 设置四个角的半径, 排序为: 左上, 右上, 右下, 左下
         *
         * @param radius 长度大于等于 8 的数组, 包含了四个角的 X 和 Y 半径
         */
        public ShapeBuilder setCornerRadii(float[] radius) {
            mGradientDrawable.setCornerRadii(radius);
            return this;
        }

        public GradientDrawable getShape() {
            return mGradientDrawable;
        }
    }

    public static class ColorSelectorBuilder {

        private ArrayList<int[]> mStates;
        private final ArrayList<Integer> mColors;

        public ColorSelectorBuilder() {
            mStates = new ArrayList<>();
            mColors = new ArrayList<>();
        }

        public ColorSelectorBuilder setOtherStateColor(int color) {
            mStates.add(new int[]{});
            mColors.add(color);
            return this;
        }

        public ColorSelectorBuilder setSelectedColor(int color) {
            mStates.add(new int[]{android.R.attr.state_selected});
            mColors.add(color);
            return this;
        }

        public ColorSelectorBuilder setEnabledColor(int color) {
            mStates.add(new int[]{android.R.attr.state_enabled});
            mColors.add(color);
            return this;
        }

        public ColorSelectorBuilder setPressedColor(int color) {
            mStates.add(new int[]{android.R.attr.state_pressed});
            mColors.add(color);
            return this;
        }

        public ColorSelectorBuilder addState(int[] stateSet, int color) {
            mStates.add(stateSet);
            mColors.add(color);
            return this;
        }

        public ColorStateList getSelector() {
            int[][] states = LshArrayUtils.toArray(mStates, int[].class);
            int[] colors = LshArrayUtils.toIntArray(mColors);
            return new ColorStateList(states, colors);
        }
    }

    public static class DrawableSelectorBuilder {

        private final StateListDrawable mStateListDrawable;

        public DrawableSelectorBuilder() {
            mStateListDrawable = new StateListDrawable();
        }

        public DrawableSelectorBuilder setOtherStateColor(int color) {
            addState(new int[]{}, color);
            return this;
        }

        public DrawableSelectorBuilder setSelectedColor(int color) {
            addState(new int[]{android.R.attr.state_selected}, color);
            return this;
        }

        public DrawableSelectorBuilder setEnabledColor(int color) {
            addState(new int[]{android.R.attr.state_enabled}, color);
            return this;
        }

        public DrawableSelectorBuilder setPressedColor(int color) {
            addState(new int[]{android.R.attr.state_pressed}, color);
            return this;
        }

        public DrawableSelectorBuilder setOtherStateDrawable(Drawable drawable) {
            addState(new int[]{}, drawable);
            return this;
        }

        public DrawableSelectorBuilder setSelectedDrawable(Drawable drawable) {
            addState(new int[]{android.R.attr.state_selected}, drawable);
            return this;
        }

        public DrawableSelectorBuilder setEnabledDrawable(Drawable drawable) {
            addState(new int[]{android.R.attr.state_enabled}, drawable);
            return this;
        }

        public DrawableSelectorBuilder setPressedDrawable(Drawable drawable) {
            addState(new int[]{android.R.attr.state_pressed}, drawable);
            return this;
        }

        public DrawableSelectorBuilder addState(int[] stateSet, int color) {
            mStateListDrawable.addState(stateSet, new ColorDrawable(color));
            return this;
        }

        public DrawableSelectorBuilder addState(int[] stateSet, Drawable drawable) {
            mStateListDrawable.addState(stateSet, drawable);
            return this;
        }

        public StateListDrawable getSelector() {
            return mStateListDrawable;
        }
    }
}
