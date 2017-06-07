package com.linsh.lshutils.utils;

import android.webkit.MimeTypeMap;

/**
 * Created by Senh Linsh on 17/6/7.
 */

public class LshMimeTypeUtils {

    public static String getMimeTypeFromExtension(String extension) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(extension)) {
            return mimeTypeMap.getMimeTypeFromExtension(extension);
        }
        return null;
    }
}
