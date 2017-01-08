package com.senh.lshutils.utils;

import android.os.Environment;

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
     * 获取SD卡的根目录
     * @return null：不存在SD卡
     */
    public static File getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

}
