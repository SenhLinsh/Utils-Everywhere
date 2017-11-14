package com.linsh.lshutils.utils;

import android.widget.EditText;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 处理 EditText 相关
 * </pre>
 */
public class LshEditTextUtils {

    /**
     * 将 EditText 的光标移动至所显示文字的末尾
     *
     * @param editText EditText
     */
    public static void moveCursorToLast(EditText editText) {
        editText.setSelection(editText.getText().length());
    }

    /**
     * 关闭 EditText 的输入 & 编辑功能
     *
     * @param editText EditText
     */
    public static void disableEditState(EditText editText) {
        editText.clearFocus();
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }

    /**
     * 打开 EditText 的输入 & 编辑功能
     *
     * @param editText    EditText
     * @param focusNeeded 是否需要获取光标
     */
    public static void enableEditState(EditText editText, boolean focusNeeded) {
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        if (focusNeeded) {
            editText.requestFocus();
            moveCursorToLast(editText);
        }
    }
}
