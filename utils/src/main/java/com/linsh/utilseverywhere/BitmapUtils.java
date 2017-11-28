package com.linsh.utilseverywhere;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.FloatRange;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Bitmap 相关;
 *             API 包括 Bitmap 对象的转化、Bitmap 对象的处理、Bitmap 对象获取和保存等;
 *             如果需要处理非 Bitmap 但与图像相关的方法, 请前往 {@link DrawableUtils} 或 {@link ImageUtils} 查看是否有相应的 API;
 *
 *             注: 部分 API 直接参考或使用 https://github.com/Blankj/AndroidUtilCode 中 ImageUtils 类里面的方法
 * </pre>
 */
public class BitmapUtils {

    private BitmapUtils() {
    }

    /**
     * 读取资源文件中图片
     *
     * @param resId 资源 Id
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(ContextUtils.getResources(), resId);
    }

    /**
     * Drawable 转 Bitmap
     *
     * @param drawable Drawable 对象
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * byte 数组 转 Bitmap
     *
     * @param bytes byte 数组
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * byte数组 转 Bitmap
     *
     * @param bytes     byte 数组
     * @param offset    image 从 byte 数组创建的起始位置
     * @param length    从 offset 处开始的长度
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(byte[] bytes, int offset, int length, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, offset, length, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeByteArray(bytes, offset, length, options);
    }

    /**
     * View 转 Bitmap
     *
     * @param view View 对象
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * 读取图片文件生成 Bitmap
     *
     * @param file 图片文件
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(File file) {
        if (file == null || !file.exists() || !file.isFile())
            return null;

        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给定最长宽和最长高, 读取图片文件生成 Bitmap
     * <p> 可防止图片过大导致 OOM 等
     *
     * @param file      图片文件
     * @param maxWidth  最长宽
     * @param maxHeight 最长高
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(File file, int maxWidth, int maxHeight) {
        if (file == null || !file.exists() || !file.isFile())
            return null;

        InputStream is = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            is = new BufferedInputStream(new FileInputStream(file));
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = computeSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给定最长宽和最长高, 读取输入流生成 Bitmap
     *
     * @param is        输入流
     * @param maxWidth  最长宽
     * @param maxHeight 最长高
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(InputStream is, int maxWidth, int maxHeight) {
        if (is == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = computeSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * Bitmap 转 byte 数组, 默认格式: JPEG
     *
     * @param bitmap Bitmap 对象
     * @return byte 数组
     */
    public static byte[] toBytes(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap 转 byte 数组
     * <p>优化压缩质量的智能计算, 以获取最接近最大尺寸的图片数据</p>
     *
     * @param bitmap  Bitmap 对象
     * @param maxSize 最大尺寸
     * @return byte 数组
     */
    public static byte[] toBytes(Bitmap bitmap, int maxSize) {
        return toBytes(bitmap, Bitmap.CompressFormat.JPEG, maxSize);
    }

    /**
     * Bitmap 转 byte 数组
     *
     * @param bitmap Bitmap 对象
     * @param format 格式: JPEG / PNG / WEBP
     * @return byte 数组
     */
    public static byte[] toBytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap 转 byte 数组
     * <p>优化压缩质量的智能计算, 以获取最接近最大尺寸的图片数据</p>
     *
     * @param bitmap  Bitmap 对象
     * @param format  格式: JPEG / PNG / WEBP
     * @param maxSize 最大尺寸
     * @return byte 数组
     */
    public static byte[] toBytes(Bitmap bitmap, Bitmap.CompressFormat format, int maxSize) {
        if (bitmap == null) return null;
        int quality = 100;
        int decrease;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, quality, baos);

        int length = baos.toByteArray().length;
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
            bitmap.compress(format, quality, baos);

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
        }
        return baos.toByteArray();
    }

    /**
     * Bitmap 转 Drawable
     *
     * @param bitmap Bitmap 对象
     * @return Drawable 对象
     */
    public static Drawable toDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(ContextUtils.getResources(), bitmap);
    }

    /**
     * 缩放图片
     *
     * @param src       源 Bitmap 对象
     * @param newWidth  新的宽度
     * @param newHeight 新的高度
     * @return 缩放处理后生成的新的 Bitmap 对象
     */
    public static Bitmap scale(Bitmap src, int newWidth, int newHeight) {
        return scale(src, newWidth, newHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param src       源 Bitmap 对象
     * @param newWidth  新的宽度
     * @param newHeight 新的高度
     * @param recycle   是否回收所处理的原 Bitmap 对象
     * @return 缩放处理后生成的新的 Bitmap 对象
     */
    public static Bitmap scale(Bitmap src, int newWidth, int newHeight, boolean recycle) {
        if (isEmptyBitmap(src)) return null;

        Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 缩放图片
     *
     * @param src         源 Bitmap 对象
     * @param scaleWidth  宽的缩放比例
     * @param scaleHeight 高的缩放比例
     * @return 缩放处理后生成的新的 Bitmap 对象
     */
    public static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param src         源 Bitmap 对象
     * @param scaleWidth  宽的缩放比例
     * @param scaleHeight 高的缩放比例
     * @param recycle     是否回收所处理的原 Bitmap 对象
     * @return 缩放处理后生成的新的 Bitmap 对象
     */
    public static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }


    /**
     * 裁剪图片
     *
     * @param src    源 Bitmap 对象
     * @param x      开始坐标x
     * @param y      开始坐标y
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return 裁剪后生成的新的 Bitmap 对象
     */
    public static Bitmap clip(Bitmap src, int x, int y, int width, int height) {
        return clip(src, x, y, width, height, false);
    }

    /**
     * 裁剪图片
     *
     * @param src     源 Bitmap 对象
     * @param x       开始坐标x
     * @param y       开始坐标y
     * @param width   裁剪宽度
     * @param height  裁剪高度
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 裁剪后生成的新的 Bitmap 对象
     */
    public static Bitmap clip(Bitmap src, int x, int y, int width, int height, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 旋转图片
     *
     * @param src     源 Bitmap 对象
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @return 旋转后生成的新的 Bitmap 对象
     */
    public static Bitmap rotate(Bitmap src, int degrees, float px, float py) {
        return rotate(src, degrees, px, py, false);
    }

    /**
     * 旋转图片
     *
     * @param src     源 Bitmap 对象
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 旋转后生成的新的 Bitmap 对象
     */
    public static Bitmap rotate(Bitmap src, int degrees, float px, float py, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        if (degrees == 0) return src;
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }


    /**
     * 获取图片文件的旋转角度
     *
     * @param filePath 图片文件路径
     * @return 旋转角度
     */
    public static int getRotateDegree(String filePath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                default:
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 生成圆形图片
     *
     * @param src 源 Bitmap 对象
     * @return 圆形图片
     */
    public static Bitmap toRound(Bitmap src) {
        return toRound(src, false);
    }

    /**
     * 生成圆形图片
     *
     * @param src     源 Bitmap 对象
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 圆形图片
     */
    public static Bitmap toRound(final Bitmap src, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int radius = Math.min(width, height) >> 1;
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        Paint paint = new Paint();
        Canvas canvas = new Canvas(ret);
        Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(width >> 1, height >> 1, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, rect, rect, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 生成圆角图片
     *
     * @param src    源 Bitmap 对象
     * @param radius 圆角的度数
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius) {
        return toRoundCorner(src, radius, false);
    }

    /**
     * 生成圆角图片
     *
     * @param src     源 Bitmap 对象
     * @param radius  圆角的度数
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius, final boolean recycle) {
        if (null == src) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        Paint paint = new Paint();
        Canvas canvas = new Canvas(ret);
        Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(new RectF(rect), radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, rect, rect, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 快速模糊
     * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
     *
     * @param src    源 Bitmap 对象
     * @param scale  缩放比例(0...1)
     * @param radius 模糊半径
     * @return 模糊后的图片
     */
    public static Bitmap fastBlur(final Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) final float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius) {
        return fastBlur(src, scale, radius, false);
    }

    /**
     * 快速模糊图片
     * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
     *
     * @param src     源 Bitmap 对象
     * @param scale   缩放比例(0...1)
     * @param radius  模糊半径(0...25)
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 模糊后的图片
     */
    public static Bitmap fastBlur(final Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) final float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius,
                                  boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int scaleWidth = (int) (width * scale + 0.5f);
        int scaleHeight = (int) (height * scale + 0.5f);
        if (scaleWidth == 0 || scaleHeight == 0) return null;
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(src, scaleWidth, scaleHeight, true);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas();
        PorterDuffColorFilter filter = new PorterDuffColorFilter(
                Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);
        canvas.scale(scale, scale);
        canvas.drawBitmap(scaleBitmap, 0, 0, paint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            scaleBitmap = renderScriptBlur(scaleBitmap, radius);
        } else {
            scaleBitmap = stackBlur(scaleBitmap, (int) radius, recycle);
        }
        if (scale == 1) return scaleBitmap;
        Bitmap ret = Bitmap.createScaledBitmap(scaleBitmap, width, height, true);
        if (scaleBitmap != null && !scaleBitmap.isRecycled()) scaleBitmap.recycle();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * renderScript 模糊图片
     * <p>API大于17</p>
     *
     * @param src    源 Bitmap 对象
     * @param radius 模糊半径(0...25)
     * @return 模糊后的图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap renderScriptBlur(final Bitmap src,
                                          @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius) {
        if (isEmptyBitmap(src)) return null;
        RenderScript rs = null;
        try {
            rs = RenderScript.create(ContextUtils.get());
            rs.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation input = Allocation.createFromBitmap(rs, src, Allocation.MipmapControl.MIPMAP_NONE, Allocation
                    .USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            blurScript.setInput(input);
            blurScript.setRadius(radius);
            blurScript.forEach(output);
            output.copyTo(src);
        } finally {
            if (rs != null) {
                rs.destroy();
            }
        }
        return src;
    }

    /**
     * stack 模糊图片
     *
     * @param src     源 Bitmap 对象
     * @param radius  模糊半径
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 模糊后的图片
     */
    public static Bitmap stackBlur(final Bitmap src, final int radius, final boolean recycle) {
        Bitmap ret;
        if (recycle) {
            ret = src;
        } else {
            ret = src.copy(src.getConfig(), true);
        }

        if (radius < 1) {
            return null;
        }

        int w = ret.getWidth();
        int h = ret.getHeight();

        int[] pix = new int[w * h];
        ret.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        ret.setPixels(pix, 0, w, 0, 0, w, h);
        return ret;
    }

    /**
     * 添加颜色边框
     *
     * @param src         源 Bitmap 对象
     * @param borderWidth 边框宽度
     * @param color       边框的颜色值
     * @return 带颜色边框图
     */
    public static Bitmap addFrame(final Bitmap src, final int borderWidth, final int color) {
        return addFrame(src, borderWidth, color, false);
    }

    /**
     * 添加颜色边框
     *
     * @param src         源 Bitmap 对象
     * @param borderWidth 边框宽度
     * @param color       边框的颜色值
     * @param recycle     是否回收所处理的原 Bitmap 对象
     * @return 带颜色边框图
     */
    public static Bitmap addFrame(final Bitmap src, final int borderWidth, final int color, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int doubleBorder = borderWidth << 1;
        int newWidth = src.getWidth() + doubleBorder;
        int newHeight = src.getHeight() + doubleBorder;
        Bitmap ret = Bitmap.createBitmap(newWidth, newHeight, src.getConfig());
        Canvas canvas = new Canvas(ret);
        //noinspection SuspiciousNameCombination
        canvas.drawBitmap(src, borderWidth, borderWidth, null);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        // setStrokeWidth是居中画的，所以要两倍的宽度才能画，否则有一半的宽度是空的
        paint.setStrokeWidth(doubleBorder);
        Rect rect = new Rect(0, 0, newWidth, newHeight);
        canvas.drawRect(rect, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 添加文字水印
     *
     * @param src      源 Bitmap 对象
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(final Bitmap src,
                                          final String content,
                                          final int textSize,
                                          final int color,
                                          final float x,
                                          final float y) {
        return addTextWatermark(src, content, textSize, color, x, y, false);
    }

    /**
     * 添加文字水印
     *
     * @param src      源 Bitmap 对象
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @param recycle  是否回收所处理的原 Bitmap 对象
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(final Bitmap src,
                                          final String content,
                                          final float textSize,
                                          final int color,
                                          final float x,
                                          final float y,
                                          final boolean recycle) {
        if (isEmptyBitmap(src) || content == null) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y + textSize, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 添加图片水印
     *
     * @param src       源 Bitmap 对象
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(final Bitmap src, final Bitmap watermark, final int x, final int y, final int alpha) {
        return addImageWatermark(src, watermark, x, y, alpha, false);
    }

    /**
     * 添加图片水印
     *
     * @param src       源 Bitmap 对象
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @param recycle   是否回收所处理的原 Bitmap 对象
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(final Bitmap src, final Bitmap watermark, final int x, final int y, final int alpha, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        if (!isEmptyBitmap(watermark)) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Canvas canvas = new Canvas(ret);
            paint.setAlpha(alpha);
            canvas.drawBitmap(watermark, x, y, paint);
        }
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 盖印颜色蒙层
     *
     * @param src     源 Bitmap 对象
     * @param color   盖印颜色
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 盖印后的图片
     */
    public static Bitmap addColorMask(Bitmap src, int color, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Canvas canvas = new Canvas(ret);
        canvas.drawColor(color);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 转为灰度图片
     *
     * @param src 源 Bitmap 对象
     * @return 灰度图
     */
    public static Bitmap toGray(final Bitmap src) {
        return toGray(src, false);
    }

    /**
     * 转为灰度图片
     *
     * @param src     源 Bitmap 对象
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return 灰度图
     */
    public static Bitmap toGray(final Bitmap src, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap grayBitmap = Bitmap.createBitmap(src.getWidth(),
                src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(grayBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(src, 0, 0, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return grayBitmap;
    }

    /**
     * 压缩图片
     *
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

    /**
     * 合并(叠加)图片
     *
     * @param background 前景图片
     * @param foreground 背景图片
     * @return 合并后图片
     */
    public static Bitmap combineBitmaps(Bitmap background, Bitmap foreground) {
        Bitmap bmp;

        int width = background.getWidth() > foreground.getWidth() ? background.getWidth() : foreground
                .getWidth();
        int height = background.getHeight() > foreground.getHeight() ? background.getHeight() : foreground
                .getHeight();
        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, 0, 0, paint);

        return bmp;
    }

    /**
     * 复制图片
     *
     * @param src 源图片
     * @return 复制后生成的新的图片
     */
    public static Bitmap copy(Bitmap src) {
        if (isEmptyBitmap(src)) return src;
        return src.copy(src.getConfig(), true);
    }

    /**
     * 将 Bitmap 保存为图片文件
     *
     * @param bitmap Bitmap 图片
     * @param output 图片文件
     * @return true 为保存成功; false 为失败
     */
    public static boolean saveBitmap(Bitmap bitmap, File output) {
        return saveBitmap(bitmap, output, Integer.MAX_VALUE, false);
    }

    /**
     * 将 Bitmap 保存为图片文件
     *
     * @param bitmap  Bitmap 图片
     * @param output  图片文件
     * @param recycle 是否回收所处理的原 Bitmap 对象
     * @return true 为保存成功; false 为失败
     */
    public static boolean saveBitmap(Bitmap bitmap, File output, boolean recycle) {
        return saveBitmap(bitmap, output, Integer.MAX_VALUE, recycle);
    }

    /**
     * 将 Bitmap 保存为图片文件, 如果尺寸过大, 将对图片进行压缩处理
     *
     * @param bitmap      Bitmap 图片
     * @param output      图片文件
     * @param maxFileSize 最大文件尺寸
     * @return true 为保存成功; false 为失败
     */
    public static boolean saveBitmap(Bitmap bitmap, File output, int maxFileSize) {
        return saveBitmap(bitmap, output, maxFileSize, false);
    }

    /**
     * 将 Bitmap 保存为图片文件, 如果尺寸过大, 将对图片进行压缩处理
     *
     * @param bitmap      Bitmap 图片
     * @param output      图片文件
     * @param maxFileSize 最大文件尺寸
     * @param recycle     是否回收所处理的原 Bitmap 对象
     * @return true 为保存成功; false 为失败
     */
    public static boolean saveBitmap(Bitmap bitmap, File output, int maxFileSize, boolean recycle) {
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
        if (recycle) {
            bitmap.recycle();
        }

        // 将bitmap保存到指定路径
        FileOutputStream fos = null;
        try {
            FileUtils.makeParentDirs(output);
            fos = new FileOutputStream(output);
            // 包装缓冲流,提高写入速度
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
            bufferedOutputStream.write(baos.toByteArray());
            bufferedOutputStream.flush();
        } catch (Exception e) {
            return false;
        } finally {
            IOUtils.close(baos, fos);
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

    /**
     * Bitmap 对象是否为空 (为 null 或者宽和高其中一项为0)
     *
     * @param src Bitmap 对象
     * @return true 为空; false 不为空
     */
    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 图片压缩处理（使用Options的方法）
     * <p/>
     * <br>
     * <b>说明</b> 使用方法：
     * 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片 。
     * 然后将Options连同期望的宽度和高度一起传递到到本方法中。
     * 之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
     * <p/>
     * <br>
     * <b>说明</b> BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
     * ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
     * ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
     * ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
     * outHeight和outMimeType属性都会被赋值。
     *
     * @param reqWidth  目标宽度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
     * @param reqHeight 目标高度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
     */
    public static BitmapFactory.Options calculateInSampleSize(
            final BitmapFactory.Options options, final int reqWidth,
            final int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > 400 || width > 450) {
            if (height > reqHeight || width > reqWidth) {
                // 计算出实际宽高和目标宽高的比率
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width
                        / (float) reqWidth);
                // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高。
                inSampleSize = heightRatio < widthRatio ? heightRatio
                        : widthRatio;
            }
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }
}
