package com.linsh.lshutils.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.linsh.lshutils.common.Config;
import com.linsh.lshutils.common.FileProperties;
import com.linsh.lshutils.utils_ex.FileUtils;

import java.io.File;

/**
 * Created by Senh Linsh on 16/10/22.
 */
public class LshPropertiesFileUtils {
    private static final String PropertyFilePath = Config.PropertyFilePath;

    public static void putProperties(FileProperties ojb) {
        if (ojb == null) return;
        String json = new Gson().toJson(ojb);
        putString(json);
    }

    private static void putString(String str) {
        File file = new File(PropertyFilePath);
        if (str != null && str.length() > 0) {
            FileUtils.writeFile(file, str, false);
        }
    }

    public static FileProperties getProperties() {
        FileProperties fileProperties = null;
        String json = getString();
        if (json == null || json.length() == 0)
            return null;
        try {
            fileProperties = new Gson().fromJson(json, FileProperties.class);
        } catch (Exception e) {
            Log.w("LshPropertiesFileUtils", "解析Json出错");
        }
        return fileProperties;
    }

    public static String getString() {
        File file = new File(PropertyFilePath);
        if (!file.exists()) return null;

        StringBuilder stringBuilder = FileUtils.readFile(file);
        return stringBuilder.toString();
    }
}
