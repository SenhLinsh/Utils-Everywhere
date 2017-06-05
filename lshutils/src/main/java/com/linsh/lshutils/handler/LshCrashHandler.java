package com.linsh.lshutils.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;
import com.linsh.lshutils.utils.Basic.LshSharedPreferenceUtils;
import com.linsh.lshutils.utils.LshActivityLifecycleUtils;
import com.linsh.lshutils.utils.LshAppUtils;

import java.util.ArrayList;

public abstract class LshCrashHandler {

    public static final String KEY_LASTED_CRASH = "key_lasted_crash";

    private static LshCrashHandler mHandler;
    private Thread.UncaughtExceptionHandler mOldHandler;
    private String mRestartActivity;

    public LshCrashHandler(Class<? extends Activity> restartActivity) {
        if (restartActivity != null) {
            this.mRestartActivity = restartActivity.getName();
        }
    }

    public static void install(Application application, LshCrashHandler handler) {
        if (mHandler != null) {
            return;
        }
        mHandler = handler;
        // FbjyCrashHandler 不处理异常, 只是有异常的时候执行打印, 所有的异常使用之前的 DefaultUncaughtExceptionHandler 来处理
        Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (handler != null && !handler.getClass().getName().equals(LshCrashHandler.class.getName())) {
            mHandler.mOldHandler = oldHandler;
        }

        LshActivityLifecycleUtils.init(application);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable thr) {
                mHandler.onCatchException(thread, thr);

                if (isStackTraceLikelyConflictive(thr) && isCrashInLastSeconds()) {
                    refreshCrashTime();
                    handleByDefaultHandler(thread, thr);
                    return;
                }
                refreshCrashTime();

                if (!LshActivityLifecycleUtils.isBackGround()) {
                    ArrayList<Activity> activities = LshActivityLifecycleUtils.getCreatedActivities();
                    for (int i = activities.size() - 1; i >= 0; i--) {
                        Activity activity = activities.get(i);
                        if (activity != null) {
                            activity.finish();
                        }
                    }
                    if (mHandler.mRestartActivity != null) {
                        try {
                            Class<?> clazz = Class.forName(mHandler.mRestartActivity);
                            Intent intent = new Intent(LshApplicationUtils.getContext(), clazz);
                            mHandler.onStartActivity(intent);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            LshApplicationUtils.getContext().startActivity(intent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            handleByDefaultHandler(thread, thr);
                            return;
                        }
                    } else {
                        handleByDefaultHandler(thread, thr);
                        return;
                    }
                }
                mHandler.onKillProcess();
                LshAppUtils.killCurrentProcess();
            }
        });
    }

    protected abstract void onCatchException(Thread thread, Throwable thr);

    protected abstract void onStartActivity(Intent intent);

    protected abstract void onKillProcess();

    private static boolean isStackTraceLikelyConflictive(@NonNull Throwable throwable) {
        do {
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if ((element.getClassName().equals("android.app.ActivityThread") && element.getMethodName().equals("handleBindApplication"))) {
                    return true;
                }
            }
        } while ((throwable = throwable.getCause()) != null);
        return false;
    }

    /**
     * 是否在几秒内发生过崩溃
     */
    private static boolean isCrashInLastSeconds() {
        long lastCrashTime = LshSharedPreferenceUtils.getLong(KEY_LASTED_CRASH);
        return System.currentTimeMillis() - lastCrashTime < 3000;
    }

    @SuppressLint("ApplySharedPref")
    private static void refreshCrashTime() {
        LshSharedPreferenceUtils.getSharedPreferences().edit().putLong(KEY_LASTED_CRASH, System.currentTimeMillis()).commit();
    }

    private static void handleByDefaultHandler(Thread thread, Throwable thr) {
        if (mHandler != null) {
            mHandler.mOldHandler.uncaughtException(thread, thr);
        }
    }
}