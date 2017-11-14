package com.linsh.lshutils.utils;

import android.os.Environment;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 应用文件管理工具
 * </pre>
 */
public class LshFileManagerUtils {

    /**
     * 本应用文件夹
     */
    private static File sAppDir = new File(Environment.getExternalStorageDirectory(), LshAppUtils.getPackageName());

    /**
     * 初始化本应用文件夹
     *
     * @param file 文件夹对象
     */
    public static void initAppDir(File file) {
        sAppDir = file;
    }

    /**
     * 获取本应用文件夹
     */
    public static File getAppDir() {
        if (LshFileUtils.checkPermission()) {
            if (!sAppDir.exists()) {
                sAppDir.mkdirs();
            }
            return sAppDir;
        }
        return LshContextUtils.getFilesDir();
    }

    /**
     * 获取应用数据文件夹, 优先获取外部应用数据文件夹, 在没有权限时将获取内部应用数据文件夹
     *
     * @return 应用数据文件夹
     */
    public static File getDataDir() {
        if (LshFileUtils.checkPermission()) {
            return LshContextUtils.get().getExternalFilesDir(null);
        }
        return LshContextUtils.get().getFilesDir();
    }

    /**
     * 获取本应用文件夹下的文件或文件夹对象
     *
     * @param fileName 文件或文件夹名
     * @return 文件或文件夹对象
     */
    public static File getFile(String fileName) {
        return new File(getAppDir(), fileName);
    }

    /**
     * 获取指定文件夹下的文件或文件夹对象
     *
     * @param parentDir 指定的父文件夹
     * @param fileName  文件或文件夹名
     * @return 文件或文件夹对象
     */
    public static File getFile(File parentDir, String fileName) {
        parentDir.mkdirs();
        return new File(parentDir, fileName);
    }

    /**
     * 获取本应用文件夹下的文件夹对象, 如果该文件夹不存在将自动创建
     *
     * @param dirName 文件夹名
     * @return 文件夹对象
     */
    public static File getDir(String dirName) {
        return getDir(getAppDir(), dirName);
    }

    /**
     * 获取指定文件夹下的文件夹对象, 如果该文件夹不存在将自动创建
     *
     * @param parentDir 指定的父文件夹
     * @param dirName   文件夹名
     * @return 文件夹对象
     */
    public static File getDir(File parentDir, String dirName) {
        File dir = new File(parentDir, dirName);
        dir.mkdirs();
        return dir;
    }
}
