package com.linsh.lshutils.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Senh Linsh on 17/6/20.
 */

public class LshClipboardUtils {

    /**
     * 复制文本
     */
    public static void putText(String text) {
        // 得到剪贴板管理器
        ClipboardManager manager = (ClipboardManager) LshContextUtils.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, text.trim()));
    }

    /**
     * 粘贴文本
     *
     * @return 当剪贴板没有东西时或者是其他类型的数据时返回 false
     */
    public static String getText() {
        ClipboardManager manager = (ClipboardManager) LshContextUtils.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = manager.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            CharSequence text = clip.getItemAt(0).getText();
            return text == null ? null : text.toString();
        }
        return null;
    }
}
