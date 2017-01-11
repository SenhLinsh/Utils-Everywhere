package com.senh.lshutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.senh.lshutils.utils.Basic.LshApplicationUtils;
import com.senh.lshutils.utils.base.JsonBean;

/**
 * Created by Senh Linsh on 16/12/23.
 */
public class LshActivityUtils {

    private static final String INTENT_EXTRA_PREFIX = "activity_utils_intent_extra";

//================================================ Activity跳转相关 ================================================//

    public static void startActivity(Activity context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }

    public static void startActivityWithNewTask(Class<?> activity) {
        Context context = LshApplicationUtils.getContext();
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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

    public static String getIntentStringFirst(Activity activity) {
        return getIntentString(activity, 0);
    }

    public static String getIntentStringSecond(Activity activity) {
        return getIntentString(activity, 1);
    }

    public static String getIntentString(Activity activity, int index) {
        return activity.getIntent().getStringExtra(INTENT_EXTRA_PREFIX + index);
    }

    public static <T extends JsonBean> T getIntentBeanFirst(Activity activity, Class<T> clazz) {
        return getIntentBean(activity, clazz, 0);
    }

    public static <T extends JsonBean> T getIntentBeanSecond(Activity activity, Class<T> clazz) {
        return getIntentBean(activity, clazz, 1);
    }

    public static <T extends JsonBean> T getIntentBean(Activity activity, Class<T> clazz, int index) {
        String stringExtra = activity.getIntent().getStringExtra(INTENT_EXTRA_PREFIX + index);
        try {
            return new Gson().fromJson(stringExtra, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //================================================ Activity相关方法 ================================================//

}
