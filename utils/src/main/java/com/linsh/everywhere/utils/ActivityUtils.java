package com.linsh.everywhere.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Activity 相关
 * </pre>
 */
public class ActivityUtils {

    private static final String INTENT_EXTRA_PREFIX = "activity_utils_intent_extra";

//================================================ Activity跳转相关 ================================================//

    /**
     * 创建 IntentBuilder 构建 Intent
     *
     * @return IntentBuilder
     */
    public static IntentBuilder newIntent() {
        return new IntentBuilder();
    }

    /**
     * 创建 IntentBuilder 构建 Intent
     *
     * @param activity 需要启动的 Activity 类
     * @return IntentBuilder
     */
    public static IntentBuilder newIntent(Class<?> activity) {
        return new IntentBuilder(activity);
    }

    /**
     * 启动 Activity
     *
     * @param context  当前 Activity
     * @param activity 需要启动的 Activity 类
     */
    public static void startActivity(Activity context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }

    /**
     * 启动 Activity, 并传递 String 类型数据
     *
     * @param context  当前 Activity
     * @param activity 需要启动的 Activity 类
     * @param data     需要传递的 String 类型数据, 获取数据时使用 {@link #getStringExtra(Activity, int)} , 传入对应的 index 即可
     */
    public static void startActivityWithData(Activity context, Class<?> activity, String... data) {
        Intent intent = new Intent(context, activity);
        for (int i = 0; i < data.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "_string" + i, data[i]);
        }
        context.startActivity(intent);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 String 数据
     *
     * @param activity 当前 Activity
     * @return 当前 Activity Intent 中的 String 数据
     */
    public static String getStringExtra(Activity activity) {
        return activity.getIntent().getStringExtra(INTENT_EXTRA_PREFIX + "_string");
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 String 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 String 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 String 数据
     */
    public static String getStringExtra(Activity activity, int index) {
        return activity.getIntent().getStringExtra(INTENT_EXTRA_PREFIX + "_string" + index);
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
        return activity.getIntent().getIntExtra(INTENT_EXTRA_PREFIX + "_int", 0);
    }

    /**
     * 获取通过 {@link IntentBuilder} 传递过来的 int 数据
     *
     * @param activity 当前 Activity
     * @param index    如果传递多个 int 数据或者指定了 index, 则需要在这里指定与传递时一致的 index
     * @return 当前 Activity Intent 中的 int 数据
     */
    public static int getIntExtra(Activity activity, int index) {
        return activity.getIntent().getIntExtra(INTENT_EXTRA_PREFIX + "_int" + index, 0);
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
        return activity.getIntent().getBooleanExtra(INTENT_EXTRA_PREFIX + "_boolean", defValue);
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
        return activity.getIntent().getBooleanExtra(INTENT_EXTRA_PREFIX + "_boolean" + index, defValue);
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
     * 1. 使用链式编程简化 Intent 的创建/管理和跳转<br/>
     * 2. 可主动帮助隐藏传递数据时需要传入的 key 值, 简化操作<br/>
     */
    public static class IntentBuilder {

        private Intent intent;

        public IntentBuilder() {
            intent = new Intent();
        }

        public IntentBuilder(Class<?> activity) {
            intent = new Intent(ApplicationUtils.getContext(), activity);
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

        public void startActivity(Context activity) {
            activity.startActivity(intent);
        }

        public void startActivityForResult(Activity activity, int requestCode) {
            activity.startActivityForResult(intent, requestCode);
        }
    }


    //================================================ Activity相关方法 ================================================//

    /**
     * 获取栈顶 Activity 的名称
     */
    public static String getTopActivityName() {
        return AppUtils.getTopActivityName();
    }

}
