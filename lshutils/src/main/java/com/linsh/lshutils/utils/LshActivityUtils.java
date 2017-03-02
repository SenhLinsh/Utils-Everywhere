package com.linsh.lshutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.linsh.lshutils.utils.Basic.LshApplicationUtils;
import com.linsh.lshutils.utils.base.JsonBean;

/**
 * Created by Senh Linsh on 16/12/23.
 */
public class LshActivityUtils {

    private static final String INTENT_EXTRA_PREFIX = "activity_utils_intent_extra";

//================================================ Activity跳转相关 ================================================//

    public static void startActivity(Activity context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }

    public static IntentBuilder newIntent(Class<?> activity) {
        return new IntentBuilder(activity);
    }

    public static void startActivityWithData(Activity context, Class<?> activity, String... data) {
        Intent intent = new Intent(context, activity);
        for (int i = 0; i < data.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + i, data[i]);
        }
        context.startActivity(intent);
    }

    public static void startActivityWithJsonBean(Activity context, Class<?> activity, JsonBean... data) {
        Intent intent = new Intent(context, activity);
        for (int i = 0; i < data.length; i++) {
            JsonBean jsonBean = data[i];
            String json = new Gson().toJson(jsonBean);
            intent.putExtra(INTENT_EXTRA_PREFIX + i, json);
        }
        context.startActivity(intent);
    }

    public static String getStringExtra(Activity activity) {
        return activity.getIntent().getStringExtra(INTENT_EXTRA_PREFIX + "_string");
    }

    public static String getStringExtra(Activity activity, int index) {
        return activity.getIntent().getStringExtra(INTENT_EXTRA_PREFIX + "_string" + index);
    }

    public static int getIntExtra(Activity activity) {
        return activity.getIntent().getIntExtra(INTENT_EXTRA_PREFIX + "_int", 0);
    }

    public static int getIntExtra(Activity activity, int index) {
        return activity.getIntent().getIntExtra(INTENT_EXTRA_PREFIX + "_int" + index, 0);
    }

    public static boolean getBooleanExtra(Activity activity, boolean defValue) {
        return activity.getIntent().getBooleanExtra(INTENT_EXTRA_PREFIX + "_boolean", defValue);
    }

    public static boolean getBooleanExtra(Activity activity, int index, boolean defValue) {
        return activity.getIntent().getBooleanExtra(INTENT_EXTRA_PREFIX + "_boolean" + index, defValue);
    }

    public static <T extends JsonBean> T getJsonBeanExtra(Activity activity, Class<T> clazz) {
        return getJsonBeanExtra(activity, clazz, INTENT_EXTRA_PREFIX + "_" + clazz.getSimpleName());
    }

    public static <T extends JsonBean> T getJsonBeanExtra(Activity activity, Class<T> clazz, int index) {
        return getJsonBeanExtra(activity, clazz, INTENT_EXTRA_PREFIX + "_" + clazz.getSimpleName() + index);
    }

    public static <T extends JsonBean> T getJsonBeanExtra(Activity activity, Class<T> clazz, String key) {
        String stringExtra = activity.getIntent().getStringExtra(key);
        try {
            return new Gson().fromJson(stringExtra, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为启用链式编程而创建用来管理intent的类
     */
    public static class IntentBuilder {
        private Intent intent;

        public IntentBuilder(Class<?> activity) {
            intent = new Intent(LshApplicationUtils.getContext(), activity);
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

        public IntentBuilder putExtra(JsonBean bean) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "_" + bean.getClass().getSimpleName(), new Gson().toJson(bean));
            return this;
        }

        public IntentBuilder putExtra(JsonBean bean, int index) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "_" + bean.getClass().getSimpleName() + index, new Gson().toJson(bean));
            return this;
        }

        public IntentBuilder putExtra(JsonBean bean, String key) {
            intent.putExtra(key, new Gson().toJson(bean));
            return this;
        }

        public IntentBuilder newTask() {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return this;
        }

        public void startActivity(Context activity) {
            activity.startActivity(intent);
        }

        public void startActivityForResult(Activity activity, int requestCode) {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    //================================================ Activity相关方法 ================================================//

}
