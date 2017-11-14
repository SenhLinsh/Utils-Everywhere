package com.linsh.lshutils.utils;

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
public class LshViewUtils {

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
}
