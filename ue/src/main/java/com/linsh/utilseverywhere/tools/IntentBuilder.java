package com.linsh.utilseverywhere.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.linsh.utilseverywhere.ContextUtils;

import java.io.Serializable;

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

    private static final String INTENT_EXTRA_PREFIX = "extra_";
    private Context context;
    private Intent intent;
    private int[] indexes = new int[8];

    public IntentBuilder() {
        intent = new Intent();
    }

    public IntentBuilder(Intent intent) {
        this.intent = intent;
    }

    public IntentBuilder(Class<?> target) {
        intent = new Intent(ContextUtils.get(), target);
    }

    public IntentBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public IntentBuilder target(Class<? extends Activity> activity) {
        intent.setClass(ContextUtils.get(), activity);
        return this;
    }

    public IntentBuilder target(String className) {
        intent.setClassName(ContextUtils.get(), className);
        return this;
    }

    public IntentBuilder target(String packageName, String className) {
        intent.setClassName(packageName, className);
        return this;
    }

    public IntentBuilder putExtra(String value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "String@" + indexes[0]++, value);
        return this;
    }

    public IntentBuilder putExtras(String... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "String@" + indexes[0]++, values[i]);
        }
        return this;
    }

    public String getStringExtra() {
        return getStringExtra(0);
    }

    public String getStringExtra(int index) {
        return intent.getStringExtra(INTENT_EXTRA_PREFIX + "String@" + index);
    }

    public IntentBuilder putExtra(int value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "int@" + indexes[1]++, value);
        return this;
    }

    public IntentBuilder putExtras(int... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "int@" + indexes[1]++, values[i]);
        }
        return this;
    }

    public int getIntExtra() {
        return getIntExtra(0);
    }

    public int getIntExtra(int index) {
        return intent.getIntExtra(INTENT_EXTRA_PREFIX + "int@" + index, 0);
    }

    public IntentBuilder putExtra(long value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "long@" + indexes[2]++, value);
        return this;
    }

    public IntentBuilder putExtras(long... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "long@" + indexes[2]++, values[i]);
        }
        return this;
    }

    public long getLongExtra() {
        return getLongExtra(0);
    }

    public long getLongExtra(int index) {
        return intent.getLongExtra(INTENT_EXTRA_PREFIX + "long@" + index, 0);
    }

    public IntentBuilder putExtra(float value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "float@" + indexes[3]++, value);
        return this;
    }

    public IntentBuilder putExtras(float... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "float@" + indexes[3]++, values[i]);
        }
        return this;
    }

    public float getFloatExtra() {
        return getFloatExtra(0);
    }

    public float getFloatExtra(int index) {
        return intent.getFloatExtra(INTENT_EXTRA_PREFIX + "float@" + index, 0);
    }

    public IntentBuilder putExtra(double value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "double@" + indexes[4]++, value);
        return this;
    }

    public IntentBuilder putExtras(double... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "double@" + indexes[4]++, values[i]);
        }
        return this;
    }

    public double getDoubleExtra() {
        return getDoubleExtra(0);
    }

    public double getDoubleExtra(int index) {
        return intent.getDoubleExtra(INTENT_EXTRA_PREFIX + "double@" + index, 0);
    }

    public IntentBuilder putExtra(boolean value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "boolean@" + indexes[5]++, value);
        return this;
    }

    public IntentBuilder putExtras(boolean... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "boolean@" + indexes[5]++, values[i]);
        }
        return this;
    }

    public boolean getBooleanExtra() {
        return getBooleanExtra(0);
    }

    public boolean getBooleanExtra(int index) {
        return intent.getBooleanExtra(INTENT_EXTRA_PREFIX + "boolean@" + index, false);
    }

    public IntentBuilder putExtra(Serializable value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "Serializable@" + indexes[6]++, value);
        return this;
    }

    public IntentBuilder putExtras(Serializable... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "Serializable@" + indexes[6]++, values[i]);
        }
        return this;
    }

    public Serializable getSerializableExtra() {
        return getSerializableExtra(0);
    }

    public Serializable getSerializableExtra(int index) {
        return intent.getSerializableExtra(INTENT_EXTRA_PREFIX + "Serializable@" + index);
    }

    public IntentBuilder putExtra(Parcelable value) {
        intent.putExtra(INTENT_EXTRA_PREFIX + "Parcelable@" + indexes[7]++, value);
        return this;
    }

    public IntentBuilder putExtras(Parcelable... values) {
        for (int i = 0; i < values.length; i++) {
            intent.putExtra(INTENT_EXTRA_PREFIX + "Parcelable@" + indexes[7]++, values[i]);
        }
        return this;
    }

    public Parcelable getParcelableExtra() {
        return getParcelableExtra(0);
    }

    public Parcelable getParcelableExtra(int index) {
        return intent.getParcelableExtra(INTENT_EXTRA_PREFIX + "Parcelable@" + index);
    }

    public IntentBuilder putExtra(String key, Object value) {
        if (value instanceof String)
            intent.putExtra(key, (String) value);
        else if (value instanceof Integer)
            intent.putExtra(key, (int) value);
        else if (value instanceof Long)
            intent.putExtra(key, (long) value);
        else if (value instanceof Float)
            intent.putExtra(key, (float) value);
        else if (value instanceof Double)
            intent.putExtra(key, (double) value);
        else if (value instanceof Parcelable)
            intent.putExtra(key, (Parcelable) value);
        else if (value instanceof Serializable)
            intent.putExtra(key, (Serializable) value);
        else
            throw new IllegalArgumentException("不支持该类型的 Extra : " + value.getClass());
        return this;
    }

    public IntentBuilder setData(Uri data) {
        intent.setData(data);
        return this;
    }

    public Uri getData() {
        return intent.getData();
    }

    public IntentBuilder setPackage(String packageName) {
        intent.setPackage(packageName);
        return this;
    }

    public IntentBuilder setAction(String action) {
        intent.setAction(action);
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
        if (context == null) {
            context = ContextUtils.get();
            newTask();
        } else if (!(context instanceof Activity)) {
            newTask();
        }
        context.startActivity(intent);
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
        if (context == null) {
            context = ContextUtils.get();
            newTask();
        }
        context.startService(intent);
    }
}
