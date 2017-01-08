package com.senh.lshutils.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Senh Linsh on 16/11/22.
 * <p/>
 * 专注于提供各项APP优化方案的方法类
 */
public class LshOptimizationUtils {

    public static void loadImageView(int resId, int resizeWidth, int resizeHeight, ImageView imageView) {
        Picasso.with(imageView.getContext())
                .load(resId)
                .resize(resizeWidth, resizeHeight)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageView(String url, int resizeWidth, int resizeHeight, ImageView imageView) {
        Picasso.with(imageView.getContext())
                .load(url)
                .resize(resizeWidth, resizeHeight)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageView(int resId, int placeholderResId, int resizeWidth, int resizeHeight, ImageView imageView) {
        Picasso.with(imageView.getContext())
                .load(resId)
                .placeholder(placeholderResId)
                .resize(resizeWidth, resizeHeight)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageView(String url, int placeholderResId, int resizeWidth, int resizeHeight, ImageView imageView) {
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(placeholderResId)
                .resize(resizeWidth, resizeHeight)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(imageView);
    }
}
