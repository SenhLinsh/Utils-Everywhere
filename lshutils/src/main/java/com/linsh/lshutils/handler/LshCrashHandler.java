package com.linsh.lshutils.handler;

import com.linsh.lshutils.utils.LshAppUtils;

public abstract class LshCrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler oldHandler;

    private LshCrashHandler() {
        // FbjyCrashHandler 不处理异常, 只是有异常的时候执行打印, 所有的异常使用之前的 DefaultUncaughtExceptionHandler 来处理
        Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        if (handler != null && !handler.getClass().getName().equals(LshCrashHandler.class.getName())) {
            oldHandler = handler;
        }
    }

    public abstract LshCrashHandler getInstance();

    @Override
    public abstract void uncaughtException(Thread thread, Throwable thr);

    public void exitApp() {
        LshAppUtils.killCurrentProcess();
    }

    public void handleByDefaultHandler(Thread thread, Throwable thr) {
        if (oldHandler != null) {
            oldHandler.uncaughtException(thread, thr);
        }
    }

    public static void init(LshCrashHandler handler) {
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }
}