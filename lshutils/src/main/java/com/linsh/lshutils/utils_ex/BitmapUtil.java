package com.linsh.lshutils.utils_ex;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

final class BitmapUtil {

    private static final boolean DEBUG = false;
    private static final String TAG = BitmapUtil.class.getSimpleName();

    private BitmapUtil() {
        throw new Error("Do not need instantiate!");
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

    /**
     * 获取一个指定大小的bitmap
     *
     * @param res       Resources
     * @param resId     图片ID
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public static Bitmap getBitmapFromResource(Resources res, int resId,
                                               int reqWidth, int reqHeight) {
        // BitmapFactory.Options options = new BitmapFactory.Options();
        // options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, resId, options);
        // options = BitmapHelper.calculateInSampleSize(options, reqWidth,
        // reqHeight);
        // return BitmapFactory.decodeResource(res, resId, options);

        // 通过JNI的形式读取本地图片达到节省内存的目的
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream is = res.openRawResource(resId);
        return getBitmapFromStream(is, null, reqWidth, reqHeight);
    }

    /**
     * 获取一个指定大小的bitmap
     *
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public static Bitmap getBitmapFromFile(String pathName, int reqWidth,
                                           int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeFile(pathName, options);
    }

    /**
     * 获取一个指定大小的bitmap
     *
     * @param data      Bitmap的byte数组
     * @param offset    image从byte数组创建的起始位置
     * @param length    the number of bytes, 从offset处开始的长度
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public static Bitmap getBitmapFromByteArray(byte[] data, int offset,
                                                int length, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, length, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeByteArray(data, offset, length, options);
    }

    /**
     * 把bitmap转化为bytes
     *
     * @param bitmap 源Bitmap
     * @return Byte数组
     */
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Stream转换成Byte
     *
     * @param inputStream InputStream
     * @return Byte数组
     */
    public static byte[] getBytesFromStream(InputStream inputStream) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    /**
     * 获取一个指定大小的bitmap
     *
     * @param b Byte数组
     * @return 需要的Bitmap
     */
    public static Bitmap getBitmapFromBytes(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     * 获取一个指定大小的bitmap
     *
     * @param is         从输入流中读取Bitmap
     * @param outPadding If not null, return the padding rect for the bitmap if it
     *                   exists, otherwise set padding to [-1,-1,-1,-1]. If no bitmap
     *                   is returned (null) then padding is unchanged.
     * @param reqWidth   目标宽度
     * @param reqHeight  目标高度
     */
    public static Bitmap getBitmapFromStream(InputStream is, Rect outPadding,
                                             int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, outPadding, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeStream(is, outPadding, options);
    }

    /**
     * 从View获取Bitmap
     *
     * @param view View
     * @return Bitmap
     */
    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        view.layout(view.getLeft(), view.getTop(), view.getRight(),
                view.getBottom());
        view.draw(canvas);

        return bitmap;
    }

    /**
     * 把一个View的对象转换成bitmap
     *
     * @param view View
     * @return Bitmap
     */
    public static Bitmap getBitmapFromView2(View view) {

        view.clearFocus();
        view.setPressed(false);

        // 能画缓存就返回false
        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);
        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            if (DEBUG) {
                Log.e(TAG, "failed getViewBitmap(" + view + ")",
                        new RuntimeException());
            }
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        // Restore the view
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable Drawable
     * @return Bitmap
     */
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                                                                   .getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                                                                                       : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * 合并Bitmap
     * @param background 背景Bitmap
     * @param foreground 前景Bitmap
     * @return 合成后的Bitmap
     */
    public static Bitmap combineImages(Bitmap background, Bitmap foreground) {
        Bitmap bmp;

        int width = background.getWidth() > foreground.getWidth() ? background.getWidth() : foreground
                .getWidth();
        int height = background.getHeight() > foreground.getHeight() ? background.getHeight() : foreground
                .getHeight();

        bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, 0, 0, paint);

        return bmp;
    }

    /**
     * 合并Bitmap到相同大小
     * @param bgd 后景Bitmap
     * @param fg 前景Bitmap
     * @return 合成后Bitmap
     */
    public static Bitmap combineImagesToSameSize(Bitmap bgd, Bitmap fg) {
        Bitmap bmp;

        int width = bgd.getWidth() < fg.getWidth() ? bgd.getWidth() : fg
                .getWidth();
        int height = bgd.getHeight() < fg.getHeight() ? bgd.getHeight() : fg
                .getHeight();

        if (fg.getWidth() != width && fg.getHeight() != height) {
            fg = zoom(fg, width, height);
        }
        if (bgd.getWidth() != width && bgd.getHeight() != height) {
            bgd = zoom(bgd, width, height);
        }

        bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);

        return bmp;
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap 源Bitmap
     * @param width 宽
     * @param height 高
     * @return 目标Bitmap
     */
    public static Bitmap zoom(Bitmap bitmap, int width, int height) {
        int bmWidth = bitmap.getWidth();
        int bmHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) width / bmWidth);
        float scaleHeight = ((float) height / bmHeight);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bmWidth, bmHeight,
                matrix, true);
        return newbmp;
    }

    /**
     * 获得圆角图片
     *
     * @param roundPx 圆角半径的像素大小
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /** 获得带倒影的图片 */
    public static Bitmap createReflectionBitmap(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /** 压缩图片大小 */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /** 将彩色图转换为灰度图 */
    public static Bitmap convertGreyImg(Bitmap image) {
        int width = image.getWidth(); // 获取位图的宽
        int height = image.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        image.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /** 转换图片成圆形 */
    public static Bitmap getRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /** 生成水印图片 水印在右下角 */
    public static Bitmap createWatermarkBitmap(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }

        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    /**
     * 重新编码Bitmap
     *
     * @param src     需要重新编码的Bitmap
     * @param format  编码后的格式（目前只支持png和jpeg这两种格式）
     * @param quality 重新生成后的bitmap的质量
     * @return 返回重新生成后的bitmap
     */
    public static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                               int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    /**
     * 图片压缩方法：（使用compress的方法）
     * <p/>
     * <br>
     * <b>说明</b> 如果bitmap本身的大小小于maxSize，则不作处理
     *
     * @param bitmap  要压缩的图片
     * @param maxSize 压缩后的大小，单位kb
     */
    public static void compress(Bitmap bitmap, double maxSize) {
        // 将bitmap放至数组中，意在获得bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 格式、质量、输出流
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 获取bitmap大小 是允许最大大小的多少倍
        double i = mid / maxSize;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (i > 1) {
            // 缩放图片 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （保持宽高不变，缩放后也达到了最大占用空间的大小）
            bitmap = scale(bitmap, bitmap.getWidth() / Math.sqrt(i),
                    bitmap.getHeight() / Math.sqrt(i));
        }
    }

    /**
     * 缩放图片
     *
     * @param src       ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     */
    public static Bitmap scale(Bitmap src, double newWidth, double newHeight) {
        // 记录src的宽高
        float width = src.getWidth();
        float height = src.getHeight();
        // 创建一个matrix容器
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 开始缩放
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建缩放后的图片
        return Bitmap.createBitmap(src, 0, 0, (int) width, (int) height,
                matrix, true);
    }

    /**
     * 缩放图片
     *
     * @param src    ：源图片资源
     * @param scaleX ：横向缩放比例
     * @param scaleY ：纵向缩放比例
     */
    public static Bitmap scale(Bitmap src, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
                matrix, true);
    }

    /**
     * 缩放图片
     * @param src   ：源图片资源
     * @param scale ：缩放比例
     */
    public static Bitmap scale(Bitmap src, float scale) {
        return scale(src, scale, scale);
    }

    /**
     * 旋转图片
     *
     * @param angle  旋转角度
     * @param bitmap 要旋转的图片
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * 水平翻转处理
     *
     * @param bitmap 原图
     * @return 水平翻转后的图片
     */
    public static Bitmap reverseByHorizontal(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, false);
    }

    /**
     * 垂直翻转处理
     *
     * @param bitmap 原图
     * @return 垂直翻转后的图片
     */
    public static Bitmap reverseByVertical(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, false);
    }
}