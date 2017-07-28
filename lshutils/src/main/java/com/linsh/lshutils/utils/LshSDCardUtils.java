package com.linsh.lshutils.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.linsh.lshutils.module.unit.FileSize;
import com.linsh.lshutils.module.unit.Unit;

import java.io.File;

/**
 * Created by Senh Linsh on 17/1/8.
 * <p>
 * SD卡工具箱
 */
public class LshSDCardUtils {

    /**
     * 获取SD卡的状态
     */
    public static String getState() {
        return Environment.getExternalStorageState();
    }

    /**
     * SD卡是否可用
     */
    public static boolean isAvailable() {
        return getState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查SD卡是否存在
     */
    private static boolean checkSdCard() {
        return isAvailable();
    }

    /**
     * 获取SD卡的根目录
     *
     * @return null：不存在SD卡
     */
    public static File getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    /**
     * 获取 Download 文件夹
     */
    public static File getDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 获取 SD卡容量
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

    public static float getSdCardSize(@Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getSdCardSize(), unit);
    }

    /**
     * 获取 SD卡可用容量
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

    public static float getSdCardAvailableSize(@Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getSdCardAvailableSize(), unit);
    }

}
