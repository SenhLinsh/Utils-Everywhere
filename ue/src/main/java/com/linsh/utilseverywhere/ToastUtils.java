package com.linsh.utilseverywhere;

import android.content.Context;
import android.widget.Toast;

import java.lang.ref.SoftReference;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 工具类: 吐司相关
 *             简介 :
 *              1.更加简练的方法调用, 不用传入 context
 *              2.即时更改 Toast 内容, 不会多次弹出 Toast
 *              3.为了避免 Toast 的内存消耗, 此处使用软引用保存 Toast 实例
 * </pre>
 */
public class ToastUtils {

    private ToastUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 使用软引用保存 Context 为 Application 的 Toast 的实例
     */
    private static SoftReference<Toast> sToast = new SoftReference<>(null);

    /**
     * 弹出 Toast, 默认短时间
     *
     * @param text 消息内容
     */
    public static void show(String text) {
        show(getContext(), text, Toast.LENGTH_SHORT);
    }

    /**
     * 弹出 Toast, 默认短时间
     *
     * @param context 指定 Context
     * @param text    消息内容
     */
    public static void show(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 弹出长时间 Toast
     *
     * @param text 消息内容
     */
    public static void showLong(String text) {
        show(getContext(), text, Toast.LENGTH_LONG);
    }

    /**
     * 弹出长时间 Toast
     *
     * @param context 指定 Context
     * @param text    消息内容
     */
    public static void showLong(Context context, String text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 弹出 Toast
     *
     * @param context  指定 Context
     * @param text     消息内容
     * @param duration 指定时长, {@link Toast#LENGTH_SHORT} 或 {@link Toast#LENGTH_LONG}
     */
    private static void show(Context context, String text, int duration) {
        Toast toast = sToast.get();
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, duration);
        sToast = new SoftReference<>(toast);
        toast.show();
    }
}
