package com.linsh.utilseverywhere;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.text.format.Formatter;

import com.linsh.utilseverywhere.interfaces.Consumer;
import com.linsh.utilseverywhere.module.unit.FileSize;
import com.linsh.utilseverywhere.module.unit.Unit;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 文件或文件夹操作相关
 *             如 读写文件 / 复制移动删除重命名文件或文件夹 / 获取文件扩展名或大小 等
 * </pre>
 */
public class FileUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private FileUtils() {
    }

    /**
     * 检查指定文件或文件夹是否可用
     * <p>判断的标准:</p>
     * <br>1.如果是外部文件, 判断外部存储是否可用以及是否有外部储存权限, 通过即为可用, 否则为不可用
     * <br>2.如果是内部文件, 则为可用
     *
     * @param file 文件或文件夹对象
     * @return true 为可用, false 为不可用
     */
    private static boolean checkFile(File file) {
        if (file == null) return false;
        boolean isStorageFile = file.getAbsolutePath().contains(Environment.getExternalStorageDirectory().getAbsolutePath());
        return !isStorageFile || SDCardUtils.isAvailable() && checkPermission();
    }

    /**
     * 检查文件是否可用, 如果可用将自动创建父文件夹
     * <p>检查的标准:</p>
     * <br>1.文件不可用或没有权限, 返回 false
     * <br>2.文件不存在, 如果父文件夹已存在, 返回 true; 如果父文件夹不存在将自动创建, 创建成功返回 true, 失败返回 false
     * <br>3.文件存在, 对象为文件对象返回 true, 为文件夹对象返回 false
     *
     * @param file 指定文件
     * @return true 检查通过, false 为不通过
     */
    public static boolean checkFileAndMakeDirs(File file) {
        if (!checkFile(file)) return false;
        if (!file.exists()) {
            File dir = file.getParentFile();
            return dir != null && (dir.exists() ? dir.isDirectory() : dir.mkdirs());
        }
        return !file.isDirectory();
    }

    /**
     * 检查文件夹是否可用, 如果可用将自动创建该文件夹及其父文件夹
     * <p>检查的标准:</p>
     * <br>1.文件不可用或没有权限, 返回 false
     * <br>2.文件不存在, 将创建该文件夹及其父文件夹, 创建成功返回 true, 失败返回 false
     * <br>3.文件存在, 对象为文件夹对象返回 true, 为文件对象返回 false
     *
     * @param file 指定文件夹
     * @return true 检查通过, false 为不通过
     */
    public static boolean checkDirAndMakeDirs(File file) {
        if (!checkFile(file))
            return false;
        if (file.exists()) {
            return file.isDirectory();
        }
        return file.mkdirs();
    }

    /**
     * 检查文件权限 (即外部存储的读写权限)
     * <p>如果没有权限, 将自动发起权限申请</p>
     *
     * @return true 为拥有权限, false 为没有权限
     */
    public static boolean checkPermission() {
        boolean check = UEPermission.Storage.check();
        if (!check) {
            Activity activity = ActivityLifecycleUtils.getTopActivitySafely();
            if (activity != null) {
                PermissionUtils.requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, null);
            }
        }
        return check;
    }

    /**
     * 获取外部储存中以 APP 包名命名的文件夹路径
     *
     * @return 文件夹路径
     */
    public static String getPackageDir() {
        return Environment.getExternalStorageDirectory() + "/" + ContextUtils.get().getPackageName() + "/";
    }

    /**
     * 创建文件夹及其父文件夹
     *
     * @param dir 文件夹对象
     * @return 成功创建返回 true, 否则返回 false
     */
    public static boolean makeDirs(File dir) {
        if (!checkFile(dir)) return false;

        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 创建父文件夹
     *
     * @param file 文件或文件夹对象
     * @return 成功创建返回 true, 否则返回 false
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
     * 读取文件, 默认编码 UTF-8
     *
     * @param file 文件对象
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readFile(File file) {
        return readFile(file, DEFAULT_CHARSET);
    }

    /**
     * 读取文件, 默认编码 UTF-8
     *
     * @param filePath 文件路径
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readFile(String filePath) {
        return readFile(new File(filePath), DEFAULT_CHARSET);
    }

    /**
     * 读取文件
     *
     * @param file        文件对象
     * @param charsetName 编码名称
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readFile(File file, String charsetName) {
        if (!checkFile(file) || !file.isFile()) {
            return null;
        }

        StringBuilder fileContent = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (fileContent.length() != 0) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 以集合形式读取文件, 每一行为一个元素, 默认编码 UTF-8
     *
     * @param file 文件对象
     * @return 文本集合, 每一个元素代表一行
     */
    public static List<String> readFileAsList(File file) {
        return readFileAsList(file, DEFAULT_CHARSET);
    }

    /**
     * 以集合形式读取文件, 每一行为一个元素
     *
     * @param file        文件对象
     * @param charsetName 编码名称
     * @return 文本集合, 每一个元素代表一行
     */
    public static List<String> readFileAsList(File file, String charsetName) {
        if (!checkFile(file) || !file.isFile()) {
            return null;
        }

        List<String> contents = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
            return contents;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //================================================ 写入文件 ================================================//

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 文本内容
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String content) {
        return writeFile(file, content, false);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 文本内容
     * @param append  是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String content, boolean append) {
        return writeFile(file, content, append, false);
    }

    /**
     * 将字符串写入文件
     *
     * @param file           文件
     * @param content        文本内容
     * @param append         是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @param endWithNewLine 是否在末尾添加换行
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String content, boolean append, boolean endWithNewLine) {
        if (StringUtils.isEmpty(content) || !checkFileAndMakeDirs(file)) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.append(content);
            if (endWithNewLine)
                writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容集合
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, List<String> contents) {
        return writeFile(file, contents, false);
    }

    /**
     * 将字符串数组写入文件, 数组中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容数组
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String... contents) {
        return writeFile(file, Arrays.asList(contents), false);
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容集合
     * @param append   是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
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
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param consumer 用于自定义写入操作的消费者
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, Consumer<BufferedWriter> consumer) {
        return writeFile(file, consumer, false);
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param consumer 用于自定义写入操作的消费者
     * @param append   是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, Consumer<BufferedWriter> consumer, boolean append) {
        if (consumer == null || !checkFileAndMakeDirs(file)) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            consumer.accept(writer);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred. ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将输入流写入文件
     *
     * @param file 文件
     * @param is   输入流
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, InputStream is) {
        return writeFile(file, is, false);
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
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
            IOUtils.close(is, os);
        }
    }

    /**
     * 将字节数组写入文件
     *
     * @param file  文件
     * @param bytes 字节数组
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, byte[] bytes) {
        return writeFile(file, bytes, false);
    }

    /**
     * 将字节数组写入文件
     *
     * @param file   文件
     * @param bytes  字节数组
     * @param append 是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
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
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //================================================ 其他文件操作 ================================================//

    /**
     * 删除文件
     *
     * @param file 文件
     * @return 是否删除成功
     */
    public static boolean deleteFile(File file) {
        if (checkFile(file) && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹及文件夹内的文件
     *
     * @param dir 文件夹
     * @return 是否删除成功
     */
    public static boolean deleteDir(File dir) {
        if (!checkFile(dir) || !dir.exists() || !dir.isDirectory())
            return false;
        if (!executeDeleteDir(dir))
            return false;
        return dir.delete();
    }

    /**
     * 递归执行删除文件夹操作
     */
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
     * 重命名文件或文件夹
     *
     * @param file    文件或文件夹
     * @param newName 新文件名或文件夹名
     * @return 是否成功
     */
    public static boolean rename(File file, String newName) {
        if (file == null || !file.exists() || StringUtils.isEmpty(newName))
            return false;
        if (newName.equals(file.getName()))
            return true;

        File newFile = new File(file.getParent() + File.separator + newName);
        return !newFile.exists() && file.renameTo(newFile);
    }

    /**
     * 复制文件
     *
     * @param srcFile  原文件对象
     * @param destFile 目标文件对象
     * @return 是否成功
     */
    public static boolean copy(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 移动文件
     *
     * @param srcFile  原文件对象
     * @param destFile 目标文件对象
     * @return 是否成功
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

    /**
     * 复制或移动文件夹
     *
     * @param srcDir  原文件夹
     * @param destDir 目标文件夹
     * @param isMove  是否为移动
     * @return 是否成功
     */
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

    /**
     * 递归执行复制或移动文件夹的操作
     */
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
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(File file) {
        if (file == null) return null;

        return getFileName(file.getPath());
    }

    /**
     * 获取文件名, 带扩展名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isTrimEmpty(filePath)) return filePath;

        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取文件名, 不带扩展名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileNameWithoutExtension(File file) {
        if (file == null) return null;

        return getFileNameWithoutExtension(file.getPath());
    }

    /**
     * 获取文件名, 不带扩展名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (StringUtils.isTrimEmpty(filePath)) return filePath;

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
     *
     * @param file 文件
     * @return 文件扩展名
     */
    public static String getFileExtension(File file) {
        if (file == null) return null;

        return getFileExtension(file.getPath());
    }

    /**
     * 获取文件扩展名
     *
     * @param filePath 文件路径
     * @return 文件扩展名
     */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isTrimEmpty(filePath)) return filePath;

        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    /**
     * 获取文件或文件夹的大小
     *
     * @param file 文件或文件夹
     * @return 大小, 单位为 B
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
     * 指定单位下, 获取文件或文件夹的大小
     *
     * @param file 文件或文件夹
     * @param unit 单位
     * @return 指定单位下的大小
     */
    public static float getFileSize(File file, @Unit.FileSizeDef int unit) {
        return FileSize.formatByte(getFileSize(file), unit);
    }

    /**
     * 获取文件或文件夹格式化后的大小, 单位根据算法自动进行调整
     *
     * @param file 文件或文件夹
     * @return 格式化后的大小
     */
    public static String getFormattedFileSize(File file) {
        return Formatter.formatFileSize(ContextUtils.get(), getFileSize(file));
    }
}
