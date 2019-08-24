package com.linsh.utilseverywhere.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.interfaces.Convertible;

import java.io.Serializable;

import androidx.fragment.app.Fragment;

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

    public IntentBuilder(Intent intent) {
        this.intent = intent;
    }

    public IntentBuilder(Class<?> cls) {
        intent = new Intent(getContext(), cls);
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
        return getStringExtra(activity.getIntent());
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 String 数据
     */
    public static String getStringExtra(Intent intent) {
        return intent.getStringExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_string");
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 String 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 String 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 String 数据
     */
    public static String getStringExtra(Activity activity, int index) {
        return getStringExtra(activity.getIntent(), index);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 String 数据
     */
    public static String getStringExtra(Intent intent, int index) {
        return intent.getStringExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_string" + index);
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
        return getIntExtra(activity.getIntent());
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 int 数据
     */
    public static int getIntExtra(Intent intent) {
        return intent.getIntExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_int", 0);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 int 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 int 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 int 数据
     */
    public static int getIntExtra(Activity activity, int index) {
        return getIntExtra(activity.getIntent(), index);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 int 数据
     */
    public static int getIntExtra(Intent intent, int index) {
        return intent.getIntExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_int" + index, 0);
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
        return getBooleanExtra(activity.getIntent(), defValue);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 boolean 数据
     */
    public static boolean getBooleanExtra(Intent intent, boolean defValue) {
        return intent.getBooleanExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_boolean", defValue);
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
        return getBooleanExtra(activity.getIntent(), index, defValue);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 boolean 数据
     */
    public static boolean getBooleanExtra(Intent intent, int index, boolean defValue) {
        return intent.getBooleanExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_boolean" + index, defValue);
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

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 long 数据
     *
     * @param activity 当前 Activity
     * @return 当前 Activity Intent 中的 long 数据
     */
    public static long getLongExtra(Activity activity) {
        return getLongExtra(activity.getIntent());
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 long 数据
     */
    public static long getLongExtra(Intent intent) {
        return intent.getLongExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_long", 0);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 long 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 long 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 long 数据
     */
    public static long getLongExtra(Activity activity, int index) {
        return getLongExtra(activity.getIntent(), index);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 long 数据
     */
    public static long getLongExtra(Intent intent, int index) {
        return intent.getLongExtra(IntentBuilder.INTENT_EXTRA_PREFIX + "_long" + index, 0);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 JsonBean 数据
     *
     * @param activity 当前 Activity
     * @param clazz    JsonBean 的类, 用于 Json 的解析和转换
     * @return 当前 Activity Intent 中的 JsonBean 数据
     */
    public static <T> T getJsonBeanExtra(Activity activity, Convertible<String, T> convertible, Class<T> clazz) {
        return getJsonBeanExtra(activity, convertible, INTENT_EXTRA_PREFIX + "_" + clazz.getSimpleName());
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 JsonBean 数据
     *
     * @param activity    当前 Activity
     * @param convertible 用于解析 json 为 Bean 类
     * @param clazz       JsonBean 的类, 用于 Json 的解析和转换
     * @param index       如果传递多个 JsonBean 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 JsonBean 数据
     */
    public static <T> T getJsonBeanExtra(Activity activity, Convertible<String, T> convertible, Class<?> clazz, int index) {
        return getJsonBeanExtra(activity, convertible, INTENT_EXTRA_PREFIX + "_" + clazz.getSimpleName() + index);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 JsonBean 数据
     *
     * @param activity    当前 Activity
     * @param convertible 用于解析 json 为 Bean 类
     * @param key         指定的 key
     * @return 当前 Activity Intent 中的 JsonBean 数据
     */
    public static <T> T getJsonBeanExtra(Activity activity, Convertible<String, T> convertible, String key) {
        String stringExtra = activity.getIntent().getStringExtra(key);
        if (stringExtra == null) return null;
        return convertible.convert(stringExtra);
    }

    public IntentBuilder setClass(Class<?> cls) {
        intent.setClass(getContext(), cls);
        return this;
    }

    public IntentBuilder putExtra(String value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_string", value);
        return this;
    }

    public String getStringExtra() {
        return intent.getStringExtra(INTENT_EXTRA_PREFIX + "_string");
    }

    public IntentBuilder putExtra(String value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_string" + index, value);
        return this;
    }

    public String getStringExtra(int index) {
        return intent.getStringExtra(INTENT_EXTRA_PREFIX + "_string" + index);
    }

    public IntentBuilder putExtra(String value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public String getStringExtra(String key) {
        return intent.getStringExtra(key);
    }

    public IntentBuilder putExtra(boolean value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_boolean", value);
        return this;
    }

    public boolean getBooleanExtra(boolean defaultValue) {
        return intent.getBooleanExtra(INTENT_EXTRA_PREFIX + "_boolean", defaultValue);
    }

    public IntentBuilder putExtra(boolean value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_boolean" + index, value);
        return this;
    }

    public boolean getBooleanExtra(int index, boolean defaultValue) {
        return intent.getBooleanExtra(INTENT_EXTRA_PREFIX + "_boolean" + index, defaultValue);
    }

    public IntentBuilder putExtra(boolean value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public boolean getBooleanExtra(String key, boolean defaultValue) {
        return intent.getBooleanExtra(key, defaultValue);
    }

    public IntentBuilder putExtra(int value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_int", value);
        return this;
    }

    public int getIntExtra() {
        return intent.getIntExtra(INTENT_EXTRA_PREFIX + "_int", 0);
    }

    public IntentBuilder putExtra(int value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_int" + index, value);
        return this;
    }

    public int getIntExtra(int index) {
        return intent.getIntExtra(INTENT_EXTRA_PREFIX + "_int" + index, 0);
    }

    public IntentBuilder putExtra(int value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public int getIntExtra(String key) {
        return intent.getIntExtra(key, 0);
    }

    public IntentBuilder putExtra(long value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_long", value);
        return this;
    }

    public long getLongExtra() {
        return intent.getLongExtra(INTENT_EXTRA_PREFIX + "_long", 0);
    }

    public IntentBuilder putExtra(long value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_long" + index, value);
        return this;
    }

    public long getLongExtra(int index) {
        return intent.getLongExtra(INTENT_EXTRA_PREFIX + "_long" + index, 0);
    }

    public IntentBuilder putExtra(long value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public long getLongExtra(String key) {
        return intent.getLongExtra(key, 0);
    }

    public IntentBuilder putExtra(float value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_float", value);
        return this;
    }

    public IntentBuilder putExtra(float value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_float" + index, value);
        return this;
    }

    public IntentBuilder putExtra(float value, String key) {
        intent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(double value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_double", value);
        return this;
    }

    public IntentBuilder putExtra(double value, int index) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_double" + index, value);
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

    public IntentBuilder putExtra(Serializable serializable) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_" + serializable.getClass().getSimpleName(), serializable);
        return this;
    }

    public IntentBuilder putExtra(Parcelable parcelable) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_" + parcelable.getClass().getSimpleName(), parcelable);
        return this;
    }

    public <T> IntentBuilder putJsonExtra(T bean, Convertible<T, String> convertible) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "_" + bean.getClass().getSimpleName(), convertible.convert(bean));
        return this;
    }

    public <T> IntentBuilder putJsonExtra(T bean, Convertible<T, String> convertible, String key) {
        intent.putExtra(key, convertible.convert(bean));
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

    public IntentBuilder setClass(Context packageContext, Class<?> cls) {
        intent.setClass(packageContext, cls);
        return this;
    }

    public IntentBuilder setClassName(String packageName, String className) {
        intent.setClassName(packageName, className);
        return this;
    }

    public IntentBuilder setType(String type) {
        intent.setType(type);
        return this;
    }

    public IntentBuilder setDataAndType(Uri data, String type) {
        intent.setDataAndType(data, type);
        return this;
    }

    public IntentBuilder addFlags(int flags) {
        intent.addFlags(flags);
        return this;
    }

    public IntentBuilder addCategory(String category) {
        intent.addCategory(category);
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
        newTask();
        getContext().startActivity(intent);
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

    public void startService() {
        getContext().startService(intent);
    }

    private static Context getContext() {
        return ContextUtils.get();
    }
}
