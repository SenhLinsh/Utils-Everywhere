package com.linsh.lshutils.utils;

import android.database.Cursor;
import android.net.Uri;

import java.io.File;

/**
 * Created by Senh Linsh on 17/6/29.
 */

public class LshUriUtils {

    public static String getFilePathFromUri(Uri uri) {
        if ("content".equals(uri.getScheme())) {
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

    public static Uri getUriFromFile(File file) {
        return Uri.fromFile(file);
    }

    public static Uri getProviderUriFromFile(File file) {
        return LshFileProviderUtils.getUriForFile(file);
    }
}
