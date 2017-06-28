package com.linsh.lshutils.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.linsh.lshutils.utils.Basic.LshLogUtils;

/**
 * Created by Senh Linsh on 16/1/11.
 */
public class LshSystemUtils {

    /**
     * 隐藏状态栏和虚拟按键
     */
    public static void hideNavigationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else {
            LshLogUtils.w("SDK(>=19)版本过低,不支持: API=" + Build.VERSION.SDK_INT);
        }
    }

    /**
     * 隐藏状态栏
     */
    public static void hideStatusBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            view.setSystemUiVisibility(systemUiVisibility);
        } else {
            LshLogUtils.w("SDK(>=16)版本过低,不支持: API=" + Build.VERSION.SDK_INT);
        }
    }

    /**
     * 设置沉浸式状态栏, 为状态栏着色
     */
    public static void setTranslucentStatusBar(Activity activity, int statusBarColor) {
        setTranslucentStatusBar(activity, statusBarColor, false);
    }

    public static void setTranslucentStatusBarWithInsertion(Activity activity, int statusBarColor) {
        setTranslucentStatusBar(activity, statusBarColor, true);
    }

    private static void setTranslucentStatusBar(Activity activity, int statusBarColor, boolean insert) {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        
        ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView();
        int statusBarHeight = LshScreenUtils.getStatusBarHeight();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        if (!insert) {
            View statusBarBg = new View(activity);
            statusBarBg.setBackgroundColor(statusBarColor);
            contentView.addView(statusBarBg, params);
        } else {
            View statusBarBg = new View(activity);
            View statusBarFg = new View(activity);
            int translucentColor = Color.argb(0x33, 0, 0, 0);
            int toolbarColor = LshColorUtils.extractColor(statusBarColor, translucentColor);
            statusBarBg.setBackgroundColor(toolbarColor);
            statusBarFg.setBackgroundColor(translucentColor);
            contentView.addView(statusBarBg, 0, params);
            contentView.addView(statusBarFg, params);
        }
    }

}
