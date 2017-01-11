package com.senh.lshutils.utils_ex;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.senh.lshutils.utils.Basic.LshIOUtils;

import java.io.BufferedReader;
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
import java.util.List;

/**
 * 文件处理工具类
 */
public class FileUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * 读取文件
     *
     * @param file 指定文件
     * @return 写入内容为空返回false，其余返回true
     */
    public static StringBuilder readFile(File file) {
        return readFile(file, "UTF-8");
    }

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return 写入内容为空返回false，其余返回true
     */
    public static StringBuilder readFile(String filePath) {
        File file = new File(filePath);
        return readFile(file, "UTF-8");
    }

    /**
     * 读取文件
     *
     * @param file        指定文件
     * @param charsetName 可被支持的 {@link java.nio.charset.Charset </code>字符集<code>}
     * @return 如果文件不存在，返回null，其余返回文件内容
     */
    public static StringBuilder readFile(File file, String charsetName) {
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

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
     * 读取文件到List集合，每一个元素为一行，默认使用UTF-8
     */
    public static List<String> readFileToList(File file) {
        return readFileToList(file, "UTF-8");
    }

    /**
     * 读取文件到List集合，每一个元素为一行
     *
     * @param file        文件路径
     * @param charsetName 字符集
     * @return 文件不存在返回null，其余返回文件内容
     */
    public static List<String> readFileToList(File file, String charsetName) {
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(reader);
        }
    }

    /**
     * 写入文件，默认覆盖原文件
     */
    public static boolean writeFile(File file, String content) {
        return writeFile(file, content, false);
    }

    /**
     * 以集合形式写入文本文件，每一个元素另起一行，默认覆盖源文件
     */
    public static boolean writeFile(File file, List<String> contentList) {
        return writeFile(file, contentList, false);
    }

    /**
     * 写入文件
     *
     * @param file    文件
     * @param content 文字内容
     * @param append  true：从尾部追加，false：覆盖原文件
     * @return return 写入内容为空返回false，其余返回true
     */
    public static boolean writeFile(File file, String content, boolean append) {
        if (StringUtils.isEmpty(content) || file == null || file.isDirectory()) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            fileWriter = new FileWriter(file, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(fileWriter);
        }
    }

    /**
     * 写入文件
     *
     * @param file        文件
     * @param contentList 通过集合封装的文字内容，每个元素另起一行
     * @param append      true：从尾部追加，false：覆盖原文件
     * @return return 写入内容为空返回false，其余返回true
     */
    public static boolean writeFile(File file, List<String> contentList, boolean append) {
        if (ListUtils.isEmpty(contentList) || file == null || file.isDirectory()) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            fileWriter = new FileWriter(file, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(fileWriter);
        }
    }


    /**
     * 以流形式写入文件，默认覆盖源文件
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, false);
    }

    /**
     * 以流形式写入文件
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * 以流形式写入文件
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(o, stream);
        }
    }

    /**
     * 移动文件
     *
     * @param sourceFilePath 源文件路径
     * @param destFilePath   目标文件路径
     */
    public static void moveFile(String sourceFilePath, String destFilePath) {
        if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
            throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
        }
        moveFile(new File(sourceFilePath), new File(destFilePath));
    }

    /**
     * 移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     */
    public static void moveFile(File srcFile, File destFile) {
        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            deleteFile(srcFile.getAbsolutePath());
        }
    }

    /**
     * 复制文件
     *
     * @param sourceFilePath 源文件路径
     * @param destFilePath   目标文件路径
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * 获取文件名，不包括扩展名
     * <p>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath 文件路径
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * 获取文件名，包括扩展名
     * <p>
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath 文件路径
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * 获取文件路径的文件夹名
     * <p>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     */
    public static String getFolderName(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 获取文件扩展名
     * <p>
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * 通过文件夹路径，创建文件夹
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (StringUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExist(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * 判断文件夹是否存在
     */
    public static boolean isFolderExist(String directoryPath) {
        if (StringUtils.isBlank(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * 删除文件或者文件夹
     * <ul>
     * <li>路径为null或空，返回true</li>
     * <li>路径不存在，返回true</li>
     * <li>如果文件存在，递归删除，返回true</li>
     * <ul>
     */
    public static boolean deleteFile(String path) {
        if (StringUtils.isBlank(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * 获取文件大小
     * <ul>
     * <li>如果路径为null或为空，返回-1</li>
     * <li>如果路径存在并且是文件，返回文件大小，其余返回-1</li>
     * <ul>
     */
    public static long getFileSize(String path) {
        if (StringUtils.isBlank(path)) {
            return -1;
        }

        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * 将归属地查询的数据库拷贝到内存
     */
    public static boolean copyAssetsToFilesDir(Context context, String DatabaseName) {
        File file = new File(context.getFilesDir(), DatabaseName);
        return copyAssets(context, DatabaseName, file);
    }

    /**
     * 把Assets里的文件拷贝到指定的文件夹下
     */
    public static boolean copyAssets(Context context, String DatabaseName, String dirPath) {
        File file = new File(dirPath);
        return copyAssets(context, DatabaseName, file);
    }

    /**
     * 把Assets里的文件拷贝至指定的文件
     */
    public static boolean copyAssets(Context context, String DatabaseName, File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            AssetManager manager = context.getAssets();
            is = manager.open(DatabaseName);
            fos = new FileOutputStream(file);
            int len;
            byte[] arr = new byte[1024];
            while ((len = is.read(arr)) != -1) {
                fos.write(arr, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            LshIOUtils.close(is, fos);
        }
    }

}
