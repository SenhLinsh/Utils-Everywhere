package com.linsh.utilseverywhere.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.linsh.utilseverywhere.ContextUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/29
 *    desc   : Intent 构建辅助类
 *
 *             1. 使用链式编程简化 Intent 的创建/管理和跳转<br/>
 *             2. 可主动帮助隐藏传递数据时需要传入的 key 值, 简化操作<br/>
 * </pre>
 */
public class IntentBuilder {

    private static final String INTENT_EXTRA_PREFIX = "intent_extra_prefix";
    private Intent intent;

    public IntentBuilder() {
        intent = new Intent();
    }

    public IntentBuilder(Class<?> cls) {
        intent = new Intent(ContextUtils.get(), cls);
    }

    public static IntentBuilder build() {
        return new IntentBuilder();
    }

    public static IntentBuilder build(Class<?> cls) {
        return new IntentBuilder(cls);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 String 数据
     *
     * @param activity 当前 Activity
     * @return 当前 Activity Intent 中的 String 数据
     */
    public static String getStringExtra(Activity activity) {
        return activity.getIntent().getStringExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_string");
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 String 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 String 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 String 数据
     */
    public static String getStringExtra(Activity activity, int index) {
        return activity.getIntent().getStringExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_string" + index);
    }

    /**
     * 获取当前 Activity Intent 中的 String 数据
     *
     * @param activity 当前 Activity
     * @param key      指定的 key
     * @return 当前 Activity Intent 中的 String 数据
     */
    public static String getStringExtra(Activity activity, String key) {
        return activity.getIntent().getStringExtra(key);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 int 数据
     *
     * @param activity 当前 Activity
     * @return 当前 Activity Intent 中的 int 数据
     */
    public static int getIntExtra(Activity activity) {
        return activity.getIntent().getIntExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_int", 0);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 int 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 int 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 int 数据
     */
    public static int getIntExtra(Activity activity, int index) {
        return activity.getIntent().getIntExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_int" + index, 0);
    }

    /**
     * 获取当前 Activity Intent 中的 int 数据
     *
     * @param activity 当前 Activity
     * @param key      指定的 key
     * @return 当前 Activity Intent 中的 int 数据
     */
    public static int getIntExtra(Activity activity, String key) {
        return activity.getIntent().getIntExtra(key, 0);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 boolean 数据
     *
     * @param activity 当前 Activity
     * @param defValue 无数据时的默认值
     * @return 当前 Activity Intent 中的 boolean 数据
     */
    public static boolean getBooleanExtra(Activity activity, boolean defValue) {
        return activity.getIntent().getBooleanExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_boolean", defValue);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 boolean 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 boolean 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @param defValue 无数据时的默认值
     * @return 当前 Activity Intent 中的 boolean 数据
     */
    public static boolean getBooleanExtra(Activity activity, int index, boolean defValue) {
        return activity.getIntent().getBooleanExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_boolean" + index, defValue);
    }

    /**
     * 获取当前 Activity Intent 中的 boolean 数据
     *
     * @param activity 当前 Activity
     * @param key      指定的 key
     * @param defValue 无数据时的默认值
     * @return 当前 Activity Intent 中的 boolean 数据
     */
    public static boolean getBooleanExtra(Activity activity, String key, boolean defValue) {
        return activity.getIntent().getBooleanExtra(key, defValue);
    }

    public IntentBuilder setClass(Class<?> cls) {
        intent.setClass(ContextUtils.get(), cls);
        return this;
    }

    public IntentBuilder putExtra(String value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_string", value);
        return this;
    }

    public IntentBuilder putExtra(String value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_string" + index, value);
        return this;
    }

    public IntentBuilder putExtra(String value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(boolean value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_boolean", value);
        return this;
    }

    public IntentBuilder putExtra(boolean value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_boolean" + index, value);
        return this;
    }

    public IntentBuilder putExtra(boolean value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(int value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_int", value);
        return this;
    }

    public IntentBuilder putExtra(int value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_int" + index, value);
        return this;
    }

    public IntentBuilder putExtra(int value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(long value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(float value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(double value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String[] values, String key) {
        intent.putExtra(key, values);
        return this;
    }

    public IntentBuilder setData(Uri data) {
        intent.setData(data);
        return this;
    }

    public IntentBuilder setAction(String action) {
        intent.setAction(action);
        return this;
    }

    public IntentBuilder addFlags(int flags) {
        intent.addFlags(flags);
        return this;
    }

    public IntentBuilder newTask() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return this;
    }

    public Intent getIntent() {
        return intent;
    }

    public void startActivity() {
        ContextUtils.get().startActivity(intent);
    }

    public void startActivity(Context context) {
        context.startActivity(intent);
    }

    public void startActivity(Fragment fragment) {
        fragment.startActivity(intent);
    }

    public void startActivity(android.app.Fragment fragment) {
        fragment.startActivity(intent);
    }

    public void startActivityForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(android.app.Fragment fragment, int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
    }
}
