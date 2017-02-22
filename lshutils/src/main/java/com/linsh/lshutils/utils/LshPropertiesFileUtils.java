package com.linsh.lshutils.utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.linsh.lshutils.utils.Basic.LshFileUtils;

import java.io.File;

/**
 * Created by Senh Linsh on 16/10/22.
 */
public class LshPropertiesFileUtils {
    private static final String PropertyFilePath = Environment.getExternalStorageDirectory().toString()
            + "/" + LshAppUtils.getPackageName() + "/properties";

    public static void putObject(Object ojb) {
        if (ojb == null) return;
        String json = new Gson().toJson(ojb);
        putString(ojb.getClass().getSimpleName(), json);
    }

    public static void putString(String fileName, String content) {
        File file = new File(PropertyFilePath);
        if (content != null && content.length() > 0) {
            try {
                LshFileUtils.writeFile(file, content, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T getObject(Class<T> classOfT) {
        T t = null;
        String json = getString(classOfT.getSimpleName());
        if (json == null || json.length() == 0)
            return null;
        try {
            t = new Gson().fromJson(json, classOfT);
        } catch (Exception e) {
            Log.w("LshPropertiesFileUtils", "解析Json出错");
        }
        return t;
    }

    public static String getString(String fileName) {
        File file = new File(PropertyFilePath);
        if (!file.exists()) return null;
        StringBuilder stringBuilder = LshFileUtils.readFile(file);
        return stringBuilder.toString();
    }
}
