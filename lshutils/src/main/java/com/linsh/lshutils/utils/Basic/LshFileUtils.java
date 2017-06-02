package com.linsh.lshutils.utils.Basic;

import android.os.Environment;
import android.text.format.Formatter;

import com.linsh.lshutils.module.unit.FileSize;
import com.linsh.lshutils.module.unit.Unit;
import com.linsh.lshutils.utils.LshPermissionUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Senh Linsh on 17/1/10.
 */
public class LshFileUtils {


    /**
     * 检查文件是否是 SD卡文件, 如果是则需要检查权限
     */
    private static boolean checkFile(File file) {
        if (file == null) return false;
        boolean isStorageFile = file.getAbsolutePath().contains(Environment.getExternalStorageDirectory().getAbsolutePath());
        return !isStorageFile || checkPermission();
    }

    public static boolean checkPermission() {
        return LshPermissionUtils.hasStoragePermission();
    }

    /**
     * 获取SD卡以app包名命名的文件夹路径
     */
    public static String getPackageDir() {
        return Environment.getExternalStorageDirectory() + "/" + LshApplicationUtils.getContext().getPackageName() + "/";
    }

    /**
     * 判断文件夹不存在则创建
     */
    public static boolean makeDirs(File dir) {
        if (!checkFile(dir)) return false;

        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 判断文件夹不存在则创建
     */
    public static boolean makeParentDirs(File file) {
        if (!checkFile(file)) return false;

        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 读取文件, 默认编码UTF-8
     */
    public static StringBuilder readFile(File file) {
        return readFile(file, "UTF-8");
    }

    public static StringBuilder readFile(String filePath) {
        return readFile(new File(filePath), "UTF-8");
    }

    private static StringBuilder readFile(File file, String charsetName) {
        if (!checkFile(file) || !file.isFile()) {
            return null;
        }

        StringBuilder fileContent = new StringBuilder("");
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(reader);
        }
    }

    /**
     * 写入文件, 默认编码UTF-8, 默认覆盖源文件 (建议在子线程中执行写入文件)
     */
    public static boolean writeFile(File file, String content) {
        return writeFile(file, content, false);
    }

    public static boolean writeFile(String filePath, String content) {
        return writeFile(new File(filePath), content, false);
    }

    public static boolean writeFile(String filePath, List<String> contents) {
        return writeFile(new File(filePath), contents, false);
    }

    public static boolean writeFile(String filePath, String... contents) {
        return writeFile(new File(filePath), Arrays.asList(contents), false);
    }

    public static boolean writeFile(File file, String content, boolean append) {
        if (LshStringUtils.isEmpty(content) || !checkFile(file) || file.isDirectory()) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.append(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(writer);
        }
    }

    public static boolean writeFile(File file, List<String> contents, boolean append) {
        if (contents == null || !checkFile(file) || file.isDirectory()) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new FileWriter(file, append));
            for (int i = 0; i < contents.size(); i++) {
                if (i > 0) {
                    writer.newLine();
                }
                writer.append(contents.get(i));
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(writer);
        }
    }


    public static boolean delete(File file) {
        if (checkFile(file) && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 获取文件或文件夹的大小
     */
    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            return file.length();
        }
        long size = 0;
        for (File childFile : file.listFiles()) {
            size += getFileSize(childFile);
        }
        return size;
    }

    public static float getFileSize(File file, @Unit.FileSizeDef int unit) {
        float size = getFileSize(file);
        switch (unit) {
            case FileSize.GB:
                size /= 1024L * 1024L * 1024L;
                break;
            case FileSize.MB:
                size /= 1024L * 1024L;
                break;
            case FileSize.KB:
                size /= 1024L;
                break;
            case FileSize.B:
                break;
        }
        return size;
    }

    public static String getFormattedFileSize(File file) {
        return Formatter.formatFileSize(LshApplicationUtils.getContext(), getFileSize(file));
    }
}
