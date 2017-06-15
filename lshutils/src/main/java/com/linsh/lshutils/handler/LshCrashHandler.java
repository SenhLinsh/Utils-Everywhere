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

    private static final String KEY_LASTED_CRASH = "key_lasted_crash";
    private static LshCrashHandler mHandler;
    private Thread.UncaughtExceptionHandler mOldHandler;

    public static void install(Application application, LshCrashHandler handler) {
        if (mHandler != null) {
            return;
        }
        mHandler = handler;
        Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (handler != null && !handler.getClass().getName().equals(LshCrashHandler.class.getName())) {
            mHandler.mOldHandler = oldHandler;
        }

        LshActivityLifecycleUtils.init(application);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable thr) {
                mHandler.onCatchException(thread, thr);

                if (mHandler.isHandleByDefaultHandler(thread, thr) || isStackTraceLikelyConflictive(thr) || isCrashInLastSeconds()) {
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

                    Class<? extends Activity> restartActivity = mHandler.onRestartAppIfNeeded();
                    if (restartActivity != null) {
                        try {
                            Intent intent = new Intent(LshApplicationUtils.getContext(), restartActivity);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            LshApplicationUtils.getContext().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            handleByDefaultHandler(thread, thr);
                            return;
                        }
                    }
                }
                LshAppUtils.killCurrentProcess();
            }
        });
    }

    protected abstract void onCatchException(Thread thread, Throwable thr);

    protected abstract boolean isHandleByDefaultHandler(Thread thread, Throwable thr);

    /**
     * 是否重启应用
     *
     * @return 重启应用需要返回一个需要打开的界面, 如果返回 null 则不重启, 直接杀死进程
     */
    protected abstract Class<? extends Activity> onRestartAppIfNeeded();

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
        } else {
            LshAppUtils.killCurrentProcess();
        }
    }
}