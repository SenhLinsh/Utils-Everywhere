package com.linsh.lshutils.utils;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 处理 Drawable 相关
 *             如果需要处理非 Drawable 但与图像相关的方法, 请前往 {@link LshBitmapUtils} 或 {@link LshImageUtils} 查看是否有相应的 API;
 * </pre>
 */
public class LshDrawableUtils {

    /**
     * 资源图片 转 Drawable
     *
     * @param resId 资源 id
     * @return Drawable 对象
     */
    public static Drawable getDrawable(int resId) {
        return LshResourceUtils.getDrawable(resId);
    }

    /**
     * Bitmap 转 Drawable
     *
     * @param bitmap Bitmap 对象
     * @return Drawable 对象
     */
    public static Drawable getDrawable(Bitmap bitmap) {
        return LshBitmapUtils.toDrawable(bitmap);
    }

    /**
     * Drawable 转 Bitmap
     *
     * @param drawable Drawable 对象
     * @return Bitmap 对象
     */
    public static Bitmap toBitmap(Drawable drawable) {
        return LshBitmapUtils.getBitmap(drawable);
    }

    /**
     * 为 Drawable 添加颜色蒙层, 可用于改变状态颜色等
     *
     * @param background 背景 Drawable
     * @param color      颜色蒙层
     * @return 添加蒙层后的新的 Drawable 对象, 处理失败返回 null
     */
    public static Drawable addColorMask(Drawable background, int color) {
        if (background == null || Color.alpha(color) == 0xFF) {
            return new ColorDrawable(color);
        } else if (background instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) background;
            int fgColor = colorDrawable.getColor();
            int pressedColor = LshColorUtils.coverColor(fgColor, color);
            return new ColorDrawable(pressedColor);
        } else if (background instanceof BitmapDrawable) {
            Bitmap fgBitmap = ((BitmapDrawable) background).getBitmap();
            Bitmap pressedBitmap = LshBitmapUtils.addColorMask(fgBitmap, color, false);
            return LshBitmapUtils.toDrawable(pressedBitmap);
        } else if (background instanceof GradientDrawable) {
            GradientDrawable pressedDr = copyGradientDrawable((GradientDrawable) background);
            if (pressedDr != null) {
                try {
                    Object gradientState = LshClassUtils.getField(pressedDr, "mGradientState");
                    if (gradientState == null) {
                        pressedDr.setColor(color);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ColorStateList solidColors = pressedDr.getColor();
                        if (solidColors != null) {
                            int normalColor = solidColors.getColorForState(new int[]{}, Color.TRANSPARENT);
                            pressedDr.setColor(LshColorUtils.coverColor(normalColor, color));
                        } else {
                            int[] gradientColors = pressedDr.getColors();
                            if (gradientColors != null) {
                                for (int i = 0; i < gradientColors.length; i++) {
                                    gradientColors[i] = LshColorUtils.coverColor(gradientColors[i], color);
                                }
                                pressedDr.setColors(gradientColors);
                            } else {
                                pressedDr.setColor(color);
                            }
                        }
                    } else {
                        ColorStateList solidColors = (ColorStateList) LshClassUtils.getField(gradientState, "mSolidColors");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (solidColors != null) {
                                int normalColor = solidColors.getColorForState(new int[]{}, Color.TRANSPARENT);
                                pressedDr.setColor(LshColorUtils.coverColor(normalColor, color));
                            } else {
                                int[] gradientColors = (int[]) LshClassUtils.getField(gradientState, "mGradientColors");
                                if (gradientColors != null) {
                                    for (int i = 0; i < gradientColors.length; i++) {
                                        gradientColors[i] = LshColorUtils.coverColor(gradientColors[i], color);
                                    }
                                    pressedDr.setColors(gradientColors);
                                } else {
                                    pressedDr.setColor(color);
                                }
                            }
                        } else {
                            if (solidColors != null) {
                                pressedDr.setColor(LshColorUtils.coverColor((solidColors).getColorForState(new int[0], 0), color));
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    int[] gradientColors = (int[]) LshClassUtils.getField(gradientState, "mGradientColors");
                                    if (gradientColors != null) {
                                        for (int i = 0; i < gradientColors.length; i++) {
                                            gradientColors[i] = LshColorUtils.coverColor(gradientColors[i], color);
                                        }
                                        pressedDr.setColors(gradientColors);
                                    }
                                }
                            }
                        }
                    }
                    return pressedDr;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 拷贝 GradientDrawable  TODO: 17/9/15  need test case
     *
     * @param drawable 源 GradientDrawable 对象
     * @return 拷贝后的新的 GradientDrawable 对象
     */
    static GradientDrawable copyGradientDrawable(GradientDrawable drawable) {
        try {
            GradientDrawable copy = new GradientDrawable();
            Object gradientState = LshClassUtils.getField(drawable, "mGradientState");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                copy.setOrientation(drawable.getOrientation());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                copy.setAlpha(drawable.getAlpha());
                copy.setStroke((Integer) LshClassUtils.getField(gradientState, "mStrokeWidth"),
                        (ColorStateList) LshClassUtils.getField(gradientState, "mStrokeColors"),
                        (Float) LshClassUtils.getField(gradientState, "mStrokeDashWidth"),
                        (Float) LshClassUtils.getField(gradientState, "mStrokeDashGap"));
            } else {
                copy.setAlpha((Integer) LshClassUtils.getField(drawable, "mAlpha"));
                copy.setStroke((Integer) LshClassUtils.getField(gradientState, "mStrokeWidth"),
                        ((ColorStateList) LshClassUtils.getField(gradientState, "mStrokeColors")).getColorForState(new int[0], 0),
                        (Float) LshClassUtils.getField(gradientState, "mStrokeDashWidth"),
                        (Float) LshClassUtils.getField(gradientState, "mStrokeDashGap"));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                float[] cornerRadii = null;
                try {
                    cornerRadii = drawable.getCornerRadii(); // 没有设置 CornerRadii 时会导致空指针异常
                } catch (Exception ignored) {
                }
                if (cornerRadii != null) {
                    copy.setCornerRadii(cornerRadii);
                } else {
                    copy.setCornerRadius(drawable.getCornerRadius());
                }
                ColorStateList solidColors = drawable.getColor();
                if (solidColors != null) {
                    copy.setColor(solidColors);
                } else {
                    copy.setColors(drawable.getColors());
                }
                copy.setGradientRadius(drawable.getGradientRadius());
                copy.setGradientType(drawable.getGradientType());
                copy.setShape(drawable.getShape());
            } else {
                float[] cornerRadii = (float[]) LshClassUtils.getField(gradientState, "mRadiusArray");
                if (cornerRadii != null) {
                    copy.setCornerRadii(cornerRadii);
                } else {
                    copy.setCornerRadius((Float) LshClassUtils.getField(gradientState, "mRadius"));
                }
                LshClassUtils.invokeMethod(copy, "setGradientType", (Integer) LshClassUtils.getField(gradientState, "mGradient"));
                LshClassUtils.invokeMethod(copy, "setShape", (Integer) LshClassUtils.getField(gradientState, "mShape"));

                ColorStateList solidColors = (ColorStateList) LshClassUtils.getField(gradientState, "mSolidColors");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (solidColors != null) {
                        copy.setColor(solidColors);
                    } else {
                        copy.setColors((int[]) LshClassUtils.getField(gradientState, "mGradientColors"));
                    }
                } else {
                    if (solidColors != null) {
                        copy.setColor((solidColors).getColorForState(new int[0], 0));
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            Object gradientColors = LshClassUtils.getField(gradientState, "mGradientColors");
                            if (gradientColors != null) {
                                copy.setColors((int[]) gradientColors);
                            }
                        }
                    }
                }
            }
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LshLog", "copyGradientDrawable: ", e);
        }
        return null;
    }
}
