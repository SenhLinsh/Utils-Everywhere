package com.linsh.lshutils.utils;

import java.io.File;

/**
 * Created by Senh Linsh on 17/6/8.
 */

public class LshImageUtils {

    /**
     * @param outWidth    期望的输出图片的宽度
     * @param outHeight   期望的输出图片的高度
     * @param maxFileSize 期望的输出图片的最大占用的存储空间, 单位: Kb
     * @return 压缩成功与否
     */
    public static boolean compressImage(File input, File output, int outWidth, int outHeight, int maxFileSize) {
        return LshBitmapUtils.compressBitmap(input, output, outWidth, outHeight, maxFileSize);
    }
}
