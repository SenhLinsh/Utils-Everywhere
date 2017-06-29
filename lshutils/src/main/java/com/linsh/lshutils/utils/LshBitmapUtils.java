package com.linsh.lshutils.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.linsh.lshutils.utils.Basic.LshFileUtils;
import com.linsh.lshutils.utils.Basic.LshIOUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Senh Linsh on 17/6/29.
 */

public class LshBitmapUtils {

    /**
     * @param outWidth    期望的输出图片的宽度
     * @param outHeight   期望的输出图片的高度
     * @param maxFileSize 期望的输出图片的最大占用的存储空间, 单位: Kb
     * @return 压缩成功与否
     */
    public static boolean compressBitmap(File input, File output, int outWidth, int outHeight, int maxFileSize) {
        //进行大小缩放来达到压缩的目的
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(input.getAbsolutePath(), options);
        //根据原始图片的宽高比和期望的输出图片的宽高比计算最终输出的图片的宽和高
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        float maxWidth = outWidth;
        float maxHeight = outHeight;
        float srcRatio = srcWidth / srcHeight;
        float outRatio = maxWidth / maxHeight;
        float actualOutWidth = srcWidth;
        float actualOutHeight = srcHeight;

        if (srcWidth > maxWidth || srcHeight > maxHeight) {
            if (srcRatio < outRatio) {
                actualOutHeight = maxHeight;
                actualOutWidth = actualOutHeight * srcRatio;
            } else if (srcRatio > outRatio) {
                actualOutWidth = maxWidth;
                actualOutHeight = actualOutWidth / srcRatio;
            } else {
                actualOutWidth = maxWidth;
                actualOutHeight = maxHeight;
            }
        }
        options.inSampleSize = computeSampleSize(options, actualOutWidth, actualOutHeight);
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = null;
        try {
            scaledBitmap = BitmapFactory.decodeFile(input.getAbsolutePath(), options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (scaledBitmap == null) {
            return false;
        }
        // 生成最终输出的bitmap
        Bitmap actualOutBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int) actualOutWidth, (int) actualOutHeight, true);
        if (actualOutBitmap != scaledBitmap)
            scaledBitmap.recycle();
        return saveBitmap(actualOutBitmap, output, maxFileSize, true);
    }

    public static boolean saveBitmap(Bitmap bitmap, File output) {
        return saveBitmap(bitmap, output, Integer.MAX_VALUE, false);
    }

    public static boolean saveBitmap(Bitmap bitmap, File output, boolean recycleBitmap) {
        return saveBitmap(bitmap, output, Integer.MAX_VALUE, recycleBitmap);
    }

    public static boolean saveBitmap(Bitmap bitmap, File output, int maxFileSize) {
        return saveBitmap(bitmap, output, maxFileSize, false);
    }

    public static boolean saveBitmap(Bitmap bitmap, File output, int maxFileSize, boolean recycleBitmap) {
        // 进行有损压缩
        int quality = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

        while (baos.toByteArray().length / 1024 > maxFileSize) { // 循环判断如果压缩后图片是否大于maxMemmorrySize,大于继续压缩
            baos.reset(); // 重置baos即让下一次的写入覆盖之前的内容
            quality = Math.max(0, quality - 10);//图片质量每次减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos); // 将压缩后的图片保存到baos中
            if (quality == 0) // 如果图片的质量已降到最低则，不再进行压缩
                break;
        }
        if (recycleBitmap) {
            bitmap.recycle();
        }

        // 将bitmap保存到指定路径
        FileOutputStream fos = null;
        try {
            LshFileUtils.makeParentDirs(output);
            fos = new FileOutputStream(output);
            // 包装缓冲流,提高写入速度
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
            bufferedOutputStream.write(baos.toByteArray());
            bufferedOutputStream.flush();
        } catch (Exception e) {
            return false;
        } finally {
            LshIOUtils.close(baos, fos);
        }
        return true;
    }

    private static int computeSampleSize(BitmapFactory.Options options, float reqWidth, float reqHeight) {
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int sampleSize = 1;
        if (srcWidth > reqWidth || srcHeight > reqHeight) {
            int withRatio = Math.round(srcWidth / reqWidth);
            int heightRatio = Math.round(srcHeight / reqHeight);
            sampleSize = Math.min(withRatio, heightRatio);
        }
        return sampleSize;
    }
}
