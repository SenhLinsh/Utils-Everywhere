package com.linsh.lshutils.utils;

import android.webkit.MimeTypeMap;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: MimeType 相关
 * </pre>
 */
public class LshMimeTypeUtils {

    /**
     * 通过文件扩展名获取 Mime 类型
     *
     * @param extension 文件扩展名
     * @return Mime 类型, 无该 Mime 类型返回 null
     */
    public static String getMimeTypeFromExtension(String extension) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(extension)) {
            return mimeTypeMap.getMimeTypeFromExtension(extension);
        }
        return null;
    }
}
