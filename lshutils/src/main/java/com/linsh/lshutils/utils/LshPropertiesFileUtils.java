package com.linsh.lshutils.utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.linsh.lshutils.utils.Basic.LshFileUtils;
import com.linsh.lshutils.utils.base.JsonBean;

import java.io.File;

/**
 * Created by Senh Linsh on 16/10/22.
 * <p>
 * 快速存储配置信息到外部储存的工具类
 * 注意: 如果存储的是JsonBean, 则需要对其进行免混淆, 否则会解析错误无法存取
 */
public class LshPropertiesFileUtils {

    private static final String PropertyDir = Environment.getExternalStorageDirectory().toString()
            + "/" + LshAppUtils.getPackageName() + "/properties";

    public static <T extends JsonBean> void putObject(T jsonBean) {
        if (jsonBean == null) return;
        String json = new Gson().toJson(jsonBean);
        putString(jsonBean.getClass().getSimpleName(), json);
    }

    public static void putString(String fileName, String content) {
        File file = new File(PropertyDir, fileName);
        if (content != null && content.length() > 0) {
            try {
                LshFileUtils.writeFile(file, content, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <T extends JsonBean> T getObject(Class<T> classOfT) {
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
        File file = new File(PropertyDir, fileName);
        if (!file.exists()) return null;
        StringBuilder stringBuilder = LshFileUtils.readFile(file);
        return stringBuilder.toString();
    }
}
