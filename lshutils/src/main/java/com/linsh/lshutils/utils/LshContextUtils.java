package com.linsh.lshutils.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import java.io.File;

/**
 * Created by Senh Linsh on 17/7/6.
 */

public class LshContextUtils {

    public static Context get() {
        return LshApplicationUtils.getContext();
    }

    public static Resources getResources() {
        return get().getResources();
    }

    public static PackageManager getPackageManager() {
        return get().getPackageManager();
    }

    public static void startActivity(Intent intent) {
        get().startActivity(intent);
    }

    public static Object getSystemService(String name) {
        return get().getSystemService(name);
    }

    public static void startService(Intent intent) {
        get().startService(intent);
    }

    public static boolean stopService(Intent intent) {
        return get().stopService(intent);
    }

    public static File getCacheDir() {
        return get().getCacheDir();
    }

    public static File getFilesDir() {
        return get().getFilesDir();
    }

    public static File getExternalCacheDir() {
        return get().getExternalCacheDir();
    }
}
