package com.linsh.lshutils.utils.Basic;

import android.os.Environment;
import android.text.format.Formatter;

import com.linsh.lshutils.module.unit.FileSize;
import com.linsh.lshutils.module.unit.Unit;
import com.linsh.lshutils.utils.LshPermissionUtils;
import com.linsh.lshutils.utils.LshSDCardUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
        return !isStorageFile || LshSDCardUtils.isAvailable() && checkPermission();
    }

    public static boolean checkFileAndMakeDirs(File file) {
        if (!checkFile(file)) return false;
        if (!file.exists()) {
            File dir = file.getParentFile();
            return dir != null && (dir.exists() ? dir.isDirectory() : dir.mkdirs());
        }
        return !file.isDirectory();
    }

    public static boolean checkDirAndMakeDirs(File file) {
        if (!checkFile(file))
            return false;
        if (file.exists()) {
            return file.isDirectory();
        }
        return file.mkdirs();
    }

    /**
     * 检查文件权限
     */
    public static boolean checkPermission() {
        return LshPermissionUtils.Storage.checkPermission();
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

    //================================================ 读取文件 ================================================//

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

    //================================================ 写入文件 ================================================//

    /**
     * 将字符串写入文件
     */
    public static boolean writeFile(File file, String content) {
        return writeFile(file, content, false);
    }

    /**
     * 将字符串写入文件
     */
    public static boolean writeFile(File file, List<String> contents) {
        return writeFile(file, contents, false);
    }

    /**
     * 将字符串写入文件
     */
    public static boolean writeFile(File file, String... contents) {
        return writeFile(file, Arrays.asList(contents), false);
    }

    /**
     * 将字符串写入文件
     */
    public static boolean writeFile(File file, String content, boolean append) {
        if (LshStringUtils.isEmpty(content) || !checkFileAndMakeDirs(file)) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.append(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(writer);
        }
    }

    /**
     * 将字符串写入文件
     */
    public static boolean writeFile(File file, List<String> contents, boolean append) {
        if (contents == null || !checkFileAndMakeDirs(file)) {
            return false;
        }

        BufferedWriter writer = null;
        try {
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

    /**
     * 将输入流写入文件
     */
    public static boolean writeFile(File file, InputStream is) {
        return writeFile(file, is, false);
    }

    /**
     * 将输入流写入文件
     */
    public static boolean writeFile(File file, InputStream is, boolean append) {
        if (is == null || !checkFileAndMakeDirs(file)) return false;

        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            int sBufferSize = 8192;
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            LshIOUtils.close(is, os);
        }
    }

    /**
     * 将字节数组写入文件
     */
    public static boolean writeFile(File file, final byte[] bytes) {
        return writeFile(file, bytes, false);
    }

    /**
     * 将字节数组写入文件
     */
    public static boolean writeFile(final File file, final byte[] bytes, final boolean append) {
        if (bytes == null || !checkFileAndMakeDirs(file)) return false;

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            LshIOUtils.close(bos);
        }
    }

    //================================================ 其他文件操作 ================================================//

    /**
     * 删除文件
     */
    public static boolean deleteFile(File file) {
        if (checkFile(file) && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹
     */
    public static boolean deleteDir(File dir) {
        if (!checkFile(dir) || !dir.exists() || !dir.isDirectory())
            return false;
        if (!executeDeleteDir(dir))
            return false;
        return dir.delete();
    }

    private static boolean executeDeleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete())
                        return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * 重命名文件
     */
    public static boolean rename(File file, String newName) {
        if (file == null || !file.exists() || LshStringUtils.isEmpty(newName))
            return false;
        if (newName.equals(file.getName()))
            return true;

        File newFile = new File(file.getParent() + File.separator + newName);
        return !newFile.exists() && file.renameTo(newFile);
    }

    /**
     * 复制文件
     */
    public static boolean copy(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 移动文件
     */
    public static boolean move(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    public static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || !srcFile.exists() || !srcFile.isFile())
            return false;
        if (destFile == null || destFile.exists())
            return false;

        try {
            return writeFile(destFile, new FileInputStream(srcFile), false) && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        if (srcDir == null || !srcDir.exists() || !srcDir.isDirectory())
            return false;
        if (!checkFile(destDir) || (destDir.exists() && destDir.isFile()))
            return false;
        // 如果目标目录在源目录中则返回false
        if (destDir.getAbsolutePath().contains(srcDir.getAbsolutePath()))
            return false;

        if (!executeCopyOrMoveDir(srcDir, destDir, isMove))
            return false;
        return !isMove || deleteDir(srcDir);
    }

    private static boolean executeCopyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destDir, file.getName());
            if (file.isFile()) {
                // 如果操作失败返回false
                if (!copyOrMoveFile(file, oneDestFile, isMove))
                    return false;
            } else if (file.isDirectory()) {
                // 如果操作失败返回false
                if (!copyOrMoveDir(file, oneDestFile, isMove))
                    return false;
            }
        }
        return true;
    }

    /**
     * 获取文件名, 带扩展名
     */
    public static String getFileName(File file) {
        if (file == null) return null;

        return getFileName(file.getPath());
    }

    /**
     * 获取文件名, 带扩展名
     */
    public static String getFileName(String filePath) {
        if (LshStringUtils.isTrimEmpty(filePath)) return filePath;

        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取文件名, 不带扩展名
     */
    public static String getFileNameWithoutExtension(File file) {
        if (file == null) return null;

        return getFileNameWithoutExtension(file.getPath());
    }

    /**
     * 获取文件名, 不带扩展名
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (LshStringUtils.isTrimEmpty(filePath)) return filePath;

        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * 获取文件扩展名
     */
    public static String getFileExtension(File file) {
        if (file == null) return null;

        return getFileExtension(file.getPath());
    }

    /**
     * 获取文件扩展名
     */
    public static String getFileExtension(String filePath) {
        if (LshStringUtils.isTrimEmpty(filePath)) return filePath;

        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
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

    /**
     * 获取文件或文件夹的大小
     */
    public static float getFileSize(File file, @Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getFileSize(file), unit);
    }

    /**
     * 获取文件或文件夹的大小
     */
    public static String getFormattedFileSize(File file) {
        return Formatter.formatFileSize(LshApplicationUtils.getContext(), getFileSize(file));
    }
}
