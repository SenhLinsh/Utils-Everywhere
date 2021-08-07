package com.linsh.utilseverywhere.tools;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresApi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/12/04
 *    desc   : Shape 的构建辅助类
 * </pre>
 */
public class ShapeBuilder {

    @IntDef({GradientDrawable.RECTANGLE, GradientDrawable.OVAL, GradientDrawable.LINE, GradientDrawable.RING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape {
    }

    private final GradientDrawable mGradientDrawable;

    public ShapeBuilder() {
        mGradientDrawable = new GradientDrawable();
    }

    /**
     * 设置形状(shape), 默认为矩形 {@link GradientDrawable#RECTANGLE}
     *
     * @param shape 形状
     * @return ShapeBuilder
     */
    public ShapeBuilder setShape(@Shape int shape) {
        mGradientDrawable.setShape(shape);
        return this;
    }

    /**
     * 设置宽高
     *
     * @param width  宽
     * @param height 高
     * @return
     */
    public ShapeBuilder setSize(int width, int height) {
        mGradientDrawable.setSize(width, height);
        return this;
    }

    /**
     * 设置填充的颜色
     *
     * @param fillColor 颜色值
     * @return ShapeBuilder
     */
    public ShapeBuilder setColor(int fillColor) {
        mGradientDrawable.setColor(fillColor);
        return this;
    }

    /**
     * 设置填充的颜色
     *
     * @param colorStateList 颜色选择器
     * @return ShapeBuilder
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeBuilder setColor(ColorStateList colorStateList) {
        mGradientDrawable.setColor(colorStateList);
        return this;
    }

    /**
     * 设置渐变颜色
     *
     * @param colors 至少两种颜色
     * @return ShapeBuilder
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public ShapeBuilder setColors(int... colors) {
        mGradientDrawable.setColors(colors);
        return this;
    }

    /**
     * 设置描边
     *
     * @param strokeWidth 描边宽度
     * @param strokeColor 描边颜色值
     * @return ShapeBuilder
     */
    public ShapeBuilder setStroke(int strokeWidth, int strokeColor) {
        mGradientDrawable.setStroke(strokeWidth, strokeColor);
        return this;
    }

    /**
     * 设置描边
     *
     * @param width          描边宽度
     * @param colorStateList 描边颜色选择器
     * @return ShapeBuilder
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeBuilder setStroke(int width, ColorStateList colorStateList) {
        mGradientDrawable.setStroke(width, colorStateList);
        return this;
    }

    /**
     * 设置四个角的半径
     *
     * @param radius 半径
     * @return ShapeBuilder
     */
    public ShapeBuilder setCornerRadius(float radius) {
        mGradientDrawable.setCornerRadius(radius);
        return this;
    }

    /**
     * 设置四个角的半径, 排序为: 左上, 右上, 右下, 左下
     *
     * @param radius 长度大于等于 8 的数组, 包含了四个角的 X 和 Y 半径
     * @return ShapeBuilder
     */
    public ShapeBuilder setCornerRadii(float[] radius) {
        mGradientDrawable.setCornerRadii(radius);
        return this;
    }

    /**
     * 获取 shape, 以 GradientDrawable 的形式返回
     *
     * @return GradientDrawable 对象
     */
    public GradientDrawable getShape() {
        return mGradientDrawable;
    }
}