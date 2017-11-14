package com.linsh.lshutils.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.linsh.lshutils.module.unit.FileSize;
import com.linsh.lshutils.module.unit.Unit;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 外部储存相关
 *             API  : 判断是否存在/可用, 获取状态, 获取根目录, 获取容量 等
 * </pre>
 */
public class LshSDCardUtils {

    /**
     * 获取 SD卡的状态
     *
     * @return SD卡状态, 为以下其中一种: <br/>
     * {@link Environment#MEDIA_UNKNOWN}, {@link Environment#MEDIA_REMOVED},
     * {@link Environment#MEDIA_UNMOUNTED}, {@link Environment#MEDIA_CHECKING},
     * {@link Environment#MEDIA_NOFS}, {@link Environment#MEDIA_MOUNTED},
     * {@link Environment#MEDIA_MOUNTED_READ_ONLY}, {@link Environment#MEDIA_SHARED},
     * {@link Environment#MEDIA_BAD_REMOVAL}, {@link Environment#MEDIA_UNMOUNTABLE}.
     */
    public static String getState() {
        return Environment.getExternalStorageState();
    }

    /**
     * 判断 SD卡是否可用
     *
     * @return 是否可用
     */
    public static boolean isAvailable() {
        return getState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查 SD卡是否存在
     *
     * @return 是否存在
     */
    private static boolean checkSdCard() {
        return isAvailable();
    }

    /**
     * 获取 SD卡的根目录
     *
     * @return 根目录文件对象, 不存在 SD卡返回 null
     */
    public static File getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    /**
     * 获取 Download 文件夹
     *
     * @return 系统级 Download 文件夹对象
     */
    public static File getDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 获取 SD卡容量
     *
     * @return SD卡容量, 单位: B
     */
    public static long getSdCardSize() {
        if (!isAvailable()) return 0;

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
        } else {
            return statFs.getBlockCount() * statFs.getBlockSize();
        }
    }

    /**
     * 指定单位下, 获取 SD卡容量
     *
     * @param unit 单位
     * @return 指定单位下的 SD卡容量
     */
    public static float getSdCardSize(@Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getSdCardSize(), unit);
    }

    /**
     * 获取 SD卡可用容量
     *
     * @return SD卡可用容量, 单位: B
     */
    public static long getSdCardAvailableSize() {
        if (!isAvailable()) return 0;

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } else {
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        }
    }

    /**
     * 指定单位下, 获取 SD卡可用容量
     *
     * @param unit 单位
     * @return 指定单位下的 SD卡可用容量
     */
    public static float getSdCardAvailableSize(@Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getSdCardAvailableSize(), unit);
    }

}
