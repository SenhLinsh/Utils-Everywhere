package com.linsh.lshutils.utils;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;

import com.linsh.lshutils.tools.LshXmlCreater;

/**
 * Created by Senh Linsh on 17/5/11.
 */

public class LshBackgroundUtils {

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static void addPressedEffect(View... views) {
        for (View view : views) {
            addPressedEffect(view, 0x33333333);
        }
    }

    public static void addPressedEffect(View view) {
        addPressedEffect(view, 0x33333333);
    }

    public static void addPressedEffect(View view, int color) {
        Drawable pressedDr = null;
        Drawable background = view.getBackground();
        if (Color.alpha(color) == 0xFF) {
            if (background instanceof StateListDrawable) {
                ((StateListDrawable) background).addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
                return;
            } else {
                pressedDr = new ColorDrawable(color);
            }
        } else {
            if (background == null) {
                pressedDr = new ColorDrawable(color);
            } else if (background instanceof ColorDrawable) {
                int fgColor = ((ColorDrawable) background).getColor();
                int pressedColor = LshColorUtils.coverColor(fgColor, color);
                pressedDr = new ColorDrawable(pressedColor);
            } else if (background instanceof BitmapDrawable) {
                Bitmap fgBitmap = ((BitmapDrawable) background).getBitmap();
                Bitmap pressedBitmap = LshBitmapUtils.addColorMask(fgBitmap, color, false);
                pressedDr = LshBitmapUtils.bitmap2Drawable(pressedBitmap);
            } else if (background instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) background;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    ColorStateList stateList = gradientDrawable.getColor();
                    if (stateList != null) {
                        int normalColor = stateList.getColorForState(new int[]{}, Color.TRANSPARENT);
                        int enabledColor = stateList.getColorForState(new int[]{android.R.attr.state_enabled}, Color.TRANSPARENT);
                        int selectedColor = stateList.getColorForState(new int[]{android.R.attr.state_selected}, Color.TRANSPARENT);
                        int pressedColor = LshColorUtils.coverColor(normalColor, color);
                        gradientDrawable.setColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_selected},
                                new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_enabled}, new int[]{}},
                                new int[]{selectedColor, pressedColor, enabledColor, normalColor}));
                    } else {
                        gradientDrawable.setColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_selected}}, new int[]{color}));
                    }
                    return;
                } else {
                    // TODO: 17/9/14  可以通过反射来获取 GradientState ?
                }
            } else if (background instanceof StateListDrawable) {
                StateListDrawable stateListDrawable = (StateListDrawable) background;

                stateListDrawable.setState(new int[]{});
                Drawable normalStateDr = stateListDrawable.getCurrent();
                pressedDr = LshDrawableUtils.addColorMask(normalStateDr, color);

                StateListDrawable newDrawable = new StateListDrawable();
                stateListDrawable.setState(new int[]{android.R.attr.state_selected});
                newDrawable.addState(new int[]{android.R.attr.state_selected}, stateListDrawable.getCurrent());
                newDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDr);
                stateListDrawable.setState(new int[]{android.R.attr.state_enabled});
                newDrawable.addState(new int[]{android.R.attr.state_enabled}, stateListDrawable.getCurrent());
                newDrawable.addState(new int[]{}, normalStateDr);
                setBackground(view, newDrawable);
                return;
            }
        }
        if (pressedDr != null) {
            StateListDrawable selector = LshXmlCreater.createDrawableSelector()
                    .setPressedDrawable(pressedDr)
                    .setOtherStateDrawable(background)
                    .getSelector();
            setBackground(view, selector);
        }
    }

    private static Drawable addPressedState(Drawable background, int color) {
        Drawable pressedBg = null;
        if (Color.alpha(color) == 0xFF) {
            if (background instanceof StateListDrawable) {
                ((StateListDrawable) background).addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
                return background;
            } else {
                pressedBg = new ColorDrawable(color);
            }
        } else {
            if (background == null) {
                pressedBg = new ColorDrawable(color);
            } else if (background instanceof ColorDrawable) {
                int fgColor = ((ColorDrawable) background).getColor();
                int pressedColor = LshColorUtils.coverColor(fgColor, color);
                pressedBg = new ColorDrawable(pressedColor);
            } else if (background instanceof BitmapDrawable) {
                Bitmap fgBitmap = ((BitmapDrawable) background).getBitmap();
                Bitmap pressedBitmap = LshBitmapUtils.addColorMask(fgBitmap, color, false);
                pressedBg = LshBitmapUtils.bitmap2Drawable(pressedBitmap);
//            } else if (background instanceof GradientDrawable) {
            } else if (background instanceof StateListDrawable) {
                StateListDrawable stateListDrawable = (StateListDrawable) background;
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
                int[] oldState = stateListDrawable.getState();
                stateListDrawable.setState(new int[]{});
                Drawable current = stateListDrawable.getCurrent();
                addPressedState(current, color);
                return background;
            }
        }
        if (pressedBg != null) {
            return LshXmlCreater.createDrawableSelector()
                    .setPressedDrawable(pressedBg)
                    .setOtherStateDrawable(background)
                    .getSelector();
        }
        return background;
    }
}
