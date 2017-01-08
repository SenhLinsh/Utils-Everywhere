package com.senh.lshutils.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Senh Linsh on 16/11/2.
 */
public class LshViewUtils {

    /**
     * 将该View及其所有子View设置selected状态
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
