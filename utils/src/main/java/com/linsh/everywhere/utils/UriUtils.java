package com.linsh.everywhere.utils;

import android.database.Cursor;
import android.net.Uri;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 工具类: Uri 相关
 * </pre>
 */
public class UriUtils {

    /**
     * 从 Uri 中获取文件路径
     *
     * @param uri Uri
     * @return 文件路径
     */
    public static String getFilePathFromUri(Uri uri) {
        if ("content".equals(uri.getScheme())) {
            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};
            try {
                cursor = ApplicationUtils.getContext().getContentResolver().query(uri, projection, null, null, null);
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

    /**
     * 获取指定文件的 Uri
     *
     * @param file 指定文件
     * @return Uri
     */
    public static Uri getUriFromFile(File file) {
        return Uri.fromFile(file);
    }

    /**
     * Android 7.0 获取指定文件的 Provider Uri
     *
     * @param file 指定文件
     * @return Uri
     */
    public static Uri getProviderUriFromFile(File file) {
        return FileProviderUtils.getUriForFile(file);
    }
}
