package com.linsh.lshutils.utils;

import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: Android 7.0 File Provider 适配相关
 * </pre>
 */
public class FileProviderUtils {


    /**
     * Android N 以上获取文件 Uri (通过 FileProvider)
     *
     * @param file
     * @return
     */
    public static Uri getUriForFile(File file) {
        return FileProvider.getUriForFile(ApplicationUtils.getContext(), getFileProviderAuthority(), file);
    }

    /**
     * 获取本应用 FileProvider 授权
     * <p>LshUtils 会自动以 {@code PackageName + ".lshfileprovider"} 形式为 FileProvider 获取授权</p>
     *
     * @return
     */
    public static String getFileProviderAuthority() {
        return AppUtils.getPackageName() + ".lshfileprovider";
    }
}
