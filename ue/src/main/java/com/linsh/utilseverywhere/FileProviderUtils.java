package com.linsh.utilseverywhere;

import android.content.Context;
import android.net.Uri;

import androidx.core.content.FileProvider;

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

    private FileProviderUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * Android N 以上获取文件 Uri (通过 FileProvider)
     */
    public static Uri getUriForFile(File file) {
        return UEFileProvider.getUriForFile(getContext(), getFileProviderAuthority(), file);
    }

    /**
     * 获取本应用 FileProvider 授权
     * <p> UE 会自动以 {@code PackageName + ".file.provider"} 形式为 FileProvider 获取授权</p>
     *
     * @return
     */
    public static String getFileProviderAuthority() {
        return getContext().getPackageName() + ".file.provider";
    }

    public static class UEFileProvider extends FileProvider {
    }
}
