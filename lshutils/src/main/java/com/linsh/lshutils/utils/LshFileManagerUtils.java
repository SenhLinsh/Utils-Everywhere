package com.linsh.lshutils.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Senh Linsh on 17/8/15.
 */

public class LshFileManagerUtils {

    private static File sAppDir = new File(Environment.getExternalStorageDirectory(), LshAppUtils.getPackageName());

    public static void initAppDir(File file) {
        sAppDir = file;
    }

    public static File getAppDir() {
        if (LshFileUtils.checkPermission()) {
            if (!sAppDir.exists()) {
                sAppDir.mkdirs();
            }
            return sAppDir;
        }
        return LshContextUtils.getFilesDir();
    }

    public static File getDataDir() {
        if (LshFileUtils.checkPermission()) {
            return LshContextUtils.get().getExternalFilesDir(null);
        }
        return LshContextUtils.get().getFilesDir();
    }

    public static File getFile(String fileName) {
        return new File(getAppDir(), fileName);
    }

    public static File getFile(File parentDir, String fileName) {
        parentDir.mkdirs();
        return new File(parentDir, fileName);
    }

    public static File getDir(String dirName) {
        return getDir(getAppDir(), dirName);
    }

    public static File getDir(File parentDir, String dirName) {
        File dir = new File(parentDir, dirName);
        dir.mkdirs();
        return dir;
    }
}
