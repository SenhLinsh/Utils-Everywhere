package com.linsh.lshutils.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import static com.linsh.lshutils.others.BitmapUtil.calculateInSampleSize;

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

    public static Drawable resource2Drawable(int resId) {
        return LshResourceUtils.getDrawable(resId);
    }

    public static Bitmap resource2Bitmap(int resId) {
        return drawable2Bitmap(resource2Drawable(resId));
    }

    public static Bitmap file2Bitmap(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static Bitmap bytes2Bitmap(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * byte数组 转 Bitmap
     *
     * @param data      Bitmap的byte数组
     * @param offset    image从byte数组创建的起始位置
     * @param length    the number of bytes, 从offset处开始的长度
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public static Bitmap bytes2Bitmap(byte[] data, int offset, int length, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, length, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeByteArray(data, offset, length, options);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 优化过的 Bitmap 转 数组, 优化压缩质量的智能计算, 以获取最接近最大尺寸的图片数据
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxSize) {
        if (bitmap == null) return null;
        int quality = 100;
        int decrease;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

        int length = baos.toByteArray().length;
        Log.v("LshLog", "bitmap2Bytes: length: " + length);
        long percent = maxSize * 100L / length;
        if (percent >= 90) {
            decrease = 1;
        } else if (percent >= 70) {
            decrease = 2;
        } else if (percent >= 50) {
            decrease = 5;
        } else if (percent >= 10) {
            decrease = 10;
        } else {
            quality = 60;
            decrease = 10;
        }
        while (length > maxSize) {
            quality = Math.max(0, quality - decrease);
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

            if (quality == 0) break;

            int newLength = baos.toByteArray().length;
            long compressPercent = newLength * 100L / length;
            if (compressPercent >= 99 || quality < 50) {
                quality = Math.max(quality, 40);
                decrease = 20;
            } else if (compressPercent > 95 || quality < 80) {
                decrease = decrease >= 10 ? 20 : 10;
            }
            length = newLength;
            Log.v("LshLog", "bitmap2Bytes: length = " + length + "   quality = " + quality + "   decrease = " + decrease);
        }
        return baos.toByteArray();
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap stream2Bitmap(InputStream is, Rect outPadding, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, outPadding, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeStream(is, outPadding, options);
    }

    public static Bitmap view2Bitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return bitmap;
    }
}
