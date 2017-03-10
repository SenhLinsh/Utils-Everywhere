package com.linsh.lshutils.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Senh Linsh on 17/3/1.
 */

public class LshKeyboardUtils {
    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Activity activity) {
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            LshKeyboardUtils.hideKeyboard(focusView);
        }
    }

    /**
     * 显示键盘
     */
    public static void showKeyboard(View view) {
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 清除所有焦点, 隐藏键盘
     */
    public static void clearFocusAndHideKeyboard(Activity activity) {
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            LshKeyboardUtils.hideKeyboard(focusView);
            focusView.clearFocus();
        }
    }
}
