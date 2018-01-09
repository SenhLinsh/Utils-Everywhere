package com.linsh.utilseverywhere;

import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 工具类: View 相关
 * </pre>
 */
public class ViewUtils {

    private ViewUtils() {
    }

    /**
     * 为指定的 View 及其所有子 View 都设置 selected 状态
     *
     * @param view     指定 View
     * @param selected 是否为 selected
     */
    public static void setSelectedWithChildView(View view, boolean selected) {
        if (view == null) return;

        view.setSelected(selected);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setSelectedWithChildView(viewGroup.getChildAt(i), selected);
            }
        }
    }

    /**
     * 获取指定的 View 再屏幕中的位置
     * <p>该位置为 View 左上角像素相对于屏幕左上角的位置
     *
     * @param view View
     * @return 包含 x 和 y 坐标的数组, [0] 为 x 坐标, [1] 为 y 坐标
     */
    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取指定的 View 再屏幕中 x 方向的位置
     * <p>该位置为 View 左上角像素相对于屏幕左上角的位置
     *
     * @param view View
     * @return x 坐标
     */
    public static int getLocationXOnScreen(View view) {
        return getLocationOnScreen(view)[0];
    }

    /**
     * 获取指定的 View 再屏幕中 y 方向的位置
     * <p>该位置为 View 左上角像素相对于屏幕左上角的位置
     *
     * @param view View
     * @return y 坐标
     */
    public static int getLocationYOnScreen(View view) {
        return getLocationOnScreen(view)[1];
    }

    /**
     * 获取指定的 View 再屏幕中的位置, 分别为: 左/上/右/下
     *
     * @param view View
     * @return 包含该 View 左上右下在屏幕中的位置
     */
    public static int[] getLocationsOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] locations = new int[4];
        locations[0] = location[0];
        locations[1] = location[1];
        locations[2] = location[0] + view.getWidth();
        locations[3] = location[1] + view.getHeight();
        return locations;
    }
}
