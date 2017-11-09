package com.linsh.lshutils.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.lang.ref.SoftReference;

/**
 * Created by Senh Linsh on 17/1/11.
 * <p>
 * 1.更加简练的方法调用, 不用传入 context
 * 2.即时更改 Toast 内容, 不会多次弹出 Toast
 * 3.为了避免 Toast 的内存消耗, 此处使用软引用保存 Toast 实例
 */
public class LshToastUtils {

    // 使用软引用保存 Context 为 Application 的 Toast 的实例
    private static SoftReference<Toast> sToast = new SoftReference<>(null);

    public static void show(String text) {
        show(LshApplicationUtils.getContext(), text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showLong(String text) {
        show(LshApplicationUtils.getContext(), text, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, String text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    public static void showNew(String text) {
        showNew(LshContextUtils.get(), text, Toast.LENGTH_SHORT);
    }

    public static void showNew(Context context, String text) {
        showNew(context, text, Toast.LENGTH_SHORT);
    }

    public static void showNewLong(String text) {
        showNew(LshContextUtils.get(), text, Toast.LENGTH_LONG);
    }

    public static void showNewLong(Context context, String text) {
        showNew(context, text, Toast.LENGTH_LONG);
    }

    private static void show(Context context, String text, int duration) {
        Toast toast = getToast(context, text, duration);
        toast.setText(text);
        toast.show();
    }

    private static void showNew(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    @SuppressLint("ShowToast")
    private static Toast getToast(Context context, String text, int duration) {
        Toast toast;
        if (context instanceof Application) {
            toast = sToast.get();
            if (toast == null) {
                toast = Toast.makeText(context, text, duration);
                sToast = new SoftReference<>(toast);
            } else {
                toast.setDuration(duration);
            }
        } else {
            toast = Toast.makeText(context, text, duration);
        }
        return toast;
    }
}
