package com.linsh.lshutils.utils;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 图片相关
 *             如果需要处理 Bitmap 图片相关的方法, 请前往 {@link LshBitmapUtils} 查看是否有相应的 API;
 *             如果需要处理 Drawable 图片相关的方法, 请前往 {@link LshDrawableUtils} 查看是否有相应的 API;
 * </pre>
 */
public class LshImageUtils {

    /**
     * 压缩指定的图片文件
     *
     * @param input       源图片文件
     * @param output      输出的图片文件
     * @param outWidth    期望的输出图片的宽度
     * @param outHeight   期望的输出图片的高度
     * @param maxFileSize 期望的输出图片的最大占用的存储空间, 单位: Kb
     * @return 压缩成功与否
     */
    public static boolean compressImage(File input, File output, int outWidth, int outHeight, int maxFileSize) {
        return LshBitmapUtils.compressBitmap(input, output, outWidth, outHeight, maxFileSize);
    }
}
