package com.linsh.lshutils.utils;

import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

import java.io.File;

/**
 * Created by Senh Linsh on 17/6/14.
 */

public class LshFileProviderUtils {

    /**
     * 创建一个用于拍照图片输出路径的Uri (FileProvider)
     */
    public static Uri getUriForFile(File file) {
        return FileProvider.getUriForFile(LshApplicationUtils.getContext(), getFileProviderAuthority(), file);
    }

    public static String getFileProviderAuthority() {
        return LshAppUtils.getPackageName() + ".lshfileprovider";
    }
}
