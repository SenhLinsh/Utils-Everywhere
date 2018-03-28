package com.linsh.utilseverywhere;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 屏幕相关
 *             API  : 获取屏幕尺寸, 获取屏幕宽高尺寸, 判断屏幕方向, 截屏, 获取 View 再屏幕中的位置 等
 * </pre>
 */
public class ScreenUtils {

    private ScreenUtils() {
    }

    /**
     * 获取屏幕尺寸
     *
     * @return DisplayMetrics 对象
     */
    public static DisplayMetrics getScreenSize() {
        WindowManager wm = (WindowManager) ContextUtils.get().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获取屏幕像素宽
     *
     * @return 屏幕宽
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) ContextUtils.get().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕像素高
     *
     * @return 屏幕高
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) ContextUtils.get().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取屏幕短边尺寸
     *
     * @return 短边尺寸
     */
    public static int getScreenShortSize() {
        DisplayMetrics outMetrics = ContextUtils.get().getResources().getDisplayMetrics();
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * 获取屏幕长边尺寸
     *
     * @return 长边尺寸
     */
    public static int getScreenLongSize() {
        DisplayMetrics outMetrics = ContextUtils.get().getResources().getDisplayMetrics();
        return Math.max(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * 判断是否横屏
     *
     * @return 是否横屏
     */
    public static boolean isLandscape() {
        return ContextUtils.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return 是否竖屏
     */
    public static boolean isPortrait() {
        return ContextUtils.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     * <p>0 为正常竖屏, 180 为倒置竖屏, 90 270 为横屏</p>
     *
     * @return 屏幕选择角度
     */
    public static int getScreenRotation() {
        WindowManager wm = (WindowManager) ContextUtils.get().getSystemService(Context.WINDOW_SERVICE);
        int rotation = wm != null ? wm.getDefaultDisplay().getRotation() : 0;
        switch (rotation) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 获取屏幕状态栏高度
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        Resources resources = ResourceUtils.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取当前屏幕截图 (包含状态栏)
     *
     * @param activity Activity
     * @return 屏幕截图
     */
    public static Bitmap getScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图 (不包含状态栏)
     *
     * @param activity Activity
     * @return 屏幕截图
     */
    public static Bitmap getScreenShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();

        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth();
        int height = getScreenHeight();

        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 判断屏幕是否锁屏
     *
     * @return 是否锁屏
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) ContextUtils.getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置屏幕方向
     *
     * @param activity    Activity
     * @param orientation 屏幕方向, 详见 {@link android.content.pm.ActivityInfo#screenOrientation ActivityInfo.screenOrientation}.
     */
    public static void setScreenOrientation(Activity activity, int orientation) {
        activity.setRequestedOrientation(orientation);
    }

    /**
     * see {@link ViewUtils#getLocationOnScreen(View)}
     */
    @Deprecated
    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * see {@link ViewUtils#getLocationXOnScreen(View)}
     */
    @Deprecated
    public static int getLocationXOnScreen(View view) {
        return getLocationOnScreen(view)[0];
    }

    /**
     * see {@link ViewUtils#getLocationYOnScreen(View)}
     */
    @Deprecated
    public static int getLocationYOnScreen(View view) {
        return getLocationOnScreen(view)[1];
    }
}
