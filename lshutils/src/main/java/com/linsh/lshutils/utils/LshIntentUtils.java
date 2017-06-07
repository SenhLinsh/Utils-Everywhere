package com.linsh.lshutils.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

import java.io.File;

/**
 * Created by Senh Linsh on 17/6/7.
 */

public class LshIntentUtils {

    public static void gotoPickFile(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickFile(Activity activity, int requestCode, String fileExtension) {
        String type = LshMimeTypeUtils.getMimeTypeFromExtension(fileExtension);
        if (type == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickPhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickVideo(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickAudio(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoTakePhoto(Activity activity, int requestCode, File outputFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoCropPhotoAsAvartar(Activity activity, int requestCode, File inputFile, File outputFile) {
        gotoCropPhoto(activity, requestCode, inputFile, outputFile, 1, 1, 512, 512);
    }

    public static void gotoCropPhoto(Activity activity, int requestCode, File inputFile, File outputFile,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        gotoCropPhoto(activity, requestCode, Uri.fromFile(inputFile), Uri.fromFile(outputFile), aspectX, aspectY, outputX, outputY);
    }

    public static void gotoCropPhoto(Activity activity, int requestCode, Uri inputUri, Uri outputUri,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("crop", "true");
        // 指定输出宽高比
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // 指定输出宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        // 指定输出路径和文件类型
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, requestCode);
    }

    public static String getFilePathFromResult(Intent data) {
        if (data == null) return null;

        Uri uri = data.getData();
        if ("file".equals(uri.getScheme())) {
            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};
            try {
                cursor = LshApplicationUtils.getContext().getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
