package com.linsh.utilseverywhere;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 剪贴板相关
 * </pre>
 */
public class ClipboardUtils {

    private ClipboardUtils() {
    }

    /**
     * 将文本复制到剪切板中
     *
     * @param text 需要复制的文字
     */
    public static void putText(String text) {
        ClipboardManager manager = (ClipboardManager) ContextUtils.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, text.trim()));
    }

    /**
     * 获取剪切板中的文字
     *
     * @return 剪切板中的文字, 当剪贴板没有东西时或者是其他类型的数据时返回 null
     */
    public static String getText() {
        ClipboardManager manager = (ClipboardManager) ContextUtils.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = manager.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            CharSequence text = clip.getItemAt(0).getText();
            return text == null ? null : text.toString();
        }
        return null;
    }
}
