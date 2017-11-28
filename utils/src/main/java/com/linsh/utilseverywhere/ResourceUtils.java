package com.linsh.utilseverywhere;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 直接获取 Resources 资源类, 避免 Context 的获取已经多步调用的麻烦
 * </pre>
 */
public class ResourceUtils {

    private ResourceUtils() {
    }

    /**
     * 获取资源对象
     *
     * @return 资源对象
     */
    public static Resources getResources() {
        return ContextUtils.get().getResources();
    }

    /**
     * 读取资源字符串
     *
     * @param resId 资源 id
     * @return 字符串
     */
    public static String getString(int resId) {
        return ContextUtils.get().getResources().getString(resId);
    }

    /**
     * 读取资源字符串数组
     *
     * @param resId 资源 id
     * @return 字符串数组
     */
    public static String[] getStringArray(int resId) {
        return ContextUtils.get().getResources().getStringArray(resId);
    }

    /**
     * 读取资源图片
     *
     * @param resId 资源 id
     * @return Drawable 对象
     */
    public static Drawable getDrawable(int resId) {
        return ContextUtils.get().getResources().getDrawable(resId);
    }

    /**
     * 读取资源颜色
     *
     * @param resId 资源 id
     * @return 颜色值
     */
    public static int getColor(int resId) {
        return ContextUtils.get().getResources().getColor(resId);
    }

    /**
     * 读取资源 Dimens
     *
     * @param resId 资源 id
     * @return Dimens 值
     */
    public static int getDimens(int resId) {
        return ContextUtils.get().getResources().getDimensionPixelSize(resId);
    }

    /**
     * 从 Assets 目录里读取文件的文本
     *
     * @param fileName 文件名
     * @return 文本内容
     */
    public static String getTextFromAssets(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        StringBuilder s = new StringBuilder("");
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(ContextUtils.get().getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从 Assets 目录里读取文件的文本字符串集合 (每行占一个元素)
     *
     * @param fileName 文件名
     * @return 文本字符串集合
     */
    public static List<String> geTextListFromAssets(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        List<String> fileContent = new ArrayList<>();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(ContextUtils.get().getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从 Raw 目录里读取文件的文本
     *
     * @param resId 资源 Id
     * @return 文本内容
     */
    public static String geTextFromRaw(int resId) {
        StringBuilder s = new StringBuilder();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(ContextUtils.get().getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从 Raw 目录里读取文件的文本字符串集合
     *
     * @param resId 资源 Id
     * @return 文本字符串集合
     */
    public static List<String> geFileToListFromRaw(int resId) {
        List<String> fileContent = new ArrayList<String>();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(ContextUtils.get().getResources().openRawResource(resId));
            BufferedReader reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
