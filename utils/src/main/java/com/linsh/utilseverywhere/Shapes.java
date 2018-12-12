package com.linsh.utilseverywhere;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import com.linsh.utilseverywhere.tools.ShapeBuilder;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/24
 *    desc   : 生成 Shape
 * </pre>
 */
public class Shapes {

    private Shapes() {
    }

    public static ShapeBuilder build() {
        return new ShapeBuilder();
    }

    /**
     * 代码生成矩形 Drawable
     *
     * @return 矩形对象, 其他参数可自行设定
     */
    private static GradientDrawable rectangle() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形 Drawable
     *
     * @param fillColor 内部填充颜色
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable rectangle(int fillColor) {
        GradientDrawable gradientDrawable = rectangle();
        // 内部填充颜色
        gradientDrawable.setColor(fillColor);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形 Drawable
     *
     * @param fillColor   内部填充颜色
     * @param roundRadius 圆角半径
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable rectangle(int fillColor, float roundRadius) {
        GradientDrawable gradientDrawable = rectangle(fillColor);
        // 圆角半径
        gradientDrawable.setCornerRadius(roundRadius);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形 Drawable
     *
     * @param fillColor   内部填充颜色
     * @param roundRadius 圆角半径
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable rectangle(int fillColor, float roundRadius, int strokeWidth, int strokeColor) {
        GradientDrawable gradientDrawable = rectangle(fillColor, roundRadius);
        //边框宽度, 边框颜色
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形边框 Drawable
     *
     * @param radii 圆角半径, 长度大于等于 8 的数组, 包含 4 个角的 X 和 Y 的半径, 顺序分别为: 左上, 右上, 右下, 左下
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable rectangleBorder(float[] radii) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(radii);
        return gradientDrawable;
    }

    /**
     * 代码生成矩形边框 Drawable
     *
     * @param fillColor 内部填充颜色
     * @param radii     圆角半径, 长度大于等于 8 的数组, 包含 4 个角的 X 和 Y 的半径, 顺序分别为: 左上, 右上, 右下, 左下
     * @return 矩形对象, 其他参数可自行设定
     */
    public static GradientDrawable rectangleBorder(int fillColor, float[] radii) {
        GradientDrawable rectangleCorner = rectangleBorder(radii);
        rectangleCorner.setColor(fillColor);
        return rectangleCorner;
    }

    /**
     * 代码生成矩形边框 Drawable
     *
     * @param color 内部填充颜色
     * @param radii 圆角半径, 长度大于等于 8 的数组, 包含 4 个角的 X 和 Y 的半径, 顺序分别为: 左上, 右上, 右下, 左下
     * @return 矩形对象, 其他参数可自行设定
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static GradientDrawable rectangleBorder(ColorStateList color, float[] radii) {
        GradientDrawable rectangleCorner = rectangleBorder(radii);
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
    public static GradientDrawable rectangleBorder(float roundRadius, int strokeWidth, int strokeColor) {
        GradientDrawable gradientDrawable = rectangle();
        gradientDrawable.setCornerRadius(roundRadius);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }
}
