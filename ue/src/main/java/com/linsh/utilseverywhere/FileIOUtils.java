package com.linsh.utilseverywhere;

import com.linsh.utilseverywhere.interfaces.Consumer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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
 *    date   : 2021/01/30
 *    desc   : 文件IO操作相关
 *
 *            与 {@link FileUtils} 的 read, write 等方法的区别
 *
 *            FileUtils 的 IO 读写操作会进行判断校验和抓异常等的操作, 该类在使用上的便利性增加, 但是无法获取
 *            相关异常信息, 进而影响开发时对异常情况的判断和处理.
 *
 *            考虑到如果在 FileUtils 上继续增加抛异常的 IO 操作将会大大增加该类的复杂性, 因此单独增加 FileIOUtils
 *            类, 对 IO 读写等相关操作将不再中断或抓取异常, 交由应用开发时进行处理.
 *
 *            因此, 在 IO 操作相关的代码开发, 建议不再使用 FileUtils 的 read, write 相关方法, 而是使用
 *            FileIOUtils 提供的方法
 * </pre>
 */
public class FileIOUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 创建父文件夹
     *
     * @param file 文件或文件夹对象
     * @return 成功创建返回 true, 否则返回 false
     */
    private static boolean makeParentDirs(File file) {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            return parentFile.mkdirs();
        }
        return false;
    }

    //================================================ 读取文件 ================================================//

    /**
     * 读取文件, 默认编码 UTF-8
     *
     * @param filePath 文件路径
     * @return 文本内容, 读取失败返回 null
     */
    public static String readAsString(String filePath) throws IOException {
        return readAsStringBuilder(filePath).toString();
    }

    /**
     * 读取文件, 默认编码 UTF-8
     *
     * @param file 文件对象
     * @return 文本内容, 读取失败返回 null
     */
    public static String readAsString(File file) throws IOException {
        return readAsStringBuilder(file).toString();
    }

    /**
     * 读取文件
     *
     * @param file        文件对象
     * @param charsetName 编码名称
     * @return 文本内容, 读取失败返回 null
     */
    public static String readAsString(File file, String charsetName) throws IOException {
        return readAsStringBuilder(file, charsetName).toString();
    }

    /**
     * 读取文件, 默认编码 UTF-8
     *
     * @param file 文件对象
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readAsStringBuilder(File file) throws IOException {
        return readAsStringBuilder(file, DEFAULT_CHARSET);
    }

    /**
     * 读取文件, 默认编码 UTF-8
     *
     * @param filePath 文件路径
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readAsStringBuilder(String filePath) throws IOException {
        return readAsStringBuilder(new File(filePath), DEFAULT_CHARSET);
    }

    /**
     * 读取文件
     *
     * @param file        文件对象
     * @param charsetName 编码名称
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readAsStringBuilder(File file, String charsetName) throws IOException {
        ExceptionUtils.checkNotNull(file);
        BufferedReader reader = null;
        try {
            StringBuilder fileContent = new StringBuilder();
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (fileContent.length() != 0) {
                    fileContent.append(StringUtils.lineSeparator());
                }
                fileContent.append(line);
            }
            return fileContent;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 以集合形式读取文件, 每一行为一个元素, 默认编码 UTF-8
     *
     * @param file 文件对象
     * @return 文本集合, 每一个元素代表一行
     */
    public static List<String> readLines(File file) throws IOException {
        return readLines(file, DEFAULT_CHARSET);
    }

    /**
     * 以集合形式读取文件, 每一行为一个元素
     *
     * @param file        文件对象
     * @param charsetName 编码名称
     * @return 文本集合, 每一个元素代表一行
     */
    public static List<String> readLines(File file, String charsetName) throws IOException {
        ExceptionUtils.checkNotNull(file);
        BufferedReader reader = null;
        try {
            List<String> contents = new ArrayList<>();
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
            return contents;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    //================================================ 写入文件 ================================================//

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 文本内容
     */
    public static void writeString(File file, String content) throws IOException {
        writeString(file, content, false);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 文本内容
     * @param append  是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     */
    public static void writeString(File file, String content, boolean append) throws IOException {
        writeString(file, content, append, false);
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
    private static void writeString(File file, String content, boolean append, boolean endWithNewLine) throws IOException {
        ExceptionUtils.checkNotNull(file);
        makeParentDirs(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.append(content);
            if (endWithNewLine)
                writer.newLine();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 将单行写入文件
     *
     * @param file    文件
     * @param content 文本内容
     */
    public static void writeLine(File file, String content) throws IOException {
        writeLine(file, content, false);
    }

    /**
     * 将单行写入文件
     *
     * @param file    文件
     * @param content 文本内容
     * @param append  是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     */
    public static void writeLine(File file, String content, boolean append) throws IOException {
        writeString(file, content, append, true);
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容集合
     * @return 是否写入成功
     */
    public static void writeLines(File file, List<String> contents) throws IOException {
        writeLines(file, contents, false);
    }

    /**
     * 将字符串数组写入文件, 数组中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容数组
     * @return 是否写入成功
     */
    public static void writeLines(File file, String... contents) throws IOException {
        writeLines(file, Arrays.asList(contents), false);
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容集合
     * @param append   是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static void writeLines(File file, List<String> contents, boolean append) throws IOException {
        ExceptionUtils.checkNotNull(file);
        if (contents == null || contents.size() == 0) return;
        makeParentDirs(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            for (int i = 0; i < contents.size(); i++) {
                if (i > 0) {
                    writer.newLine();
                }
                writer.append(contents.get(i));
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 自定义文本写入操作
     *
     * @param file     文件
     * @param consumer 用于自定义写入操作的消费者
     */
    public static void writeCustom(File file, Consumer<BufferedWriter> consumer) throws IOException {
        writeCustom(file, consumer, false);
    }

    /**
     * 自定义文本写入操作
     *
     * @param file     文件
     * @param consumer 用于自定义写入操作的消费者
     * @param append   是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     */
    public static void writeCustom(File file, Consumer<BufferedWriter> consumer, boolean append) throws IOException {
        ExceptionUtils.checkNotNull(file);
        if (consumer == null) return;
        makeParentDirs(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            consumer.accept(writer);
        } finally {
            if (writer != null) {
                writer.close();
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
    public static void writeStream(File file, InputStream is) throws IOException {
        writeStream(file, is, false);
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static void writeStream(File file, InputStream is, boolean append) throws IOException {
        ExceptionUtils.checkNotNull(file);
        ExceptionUtils.checkNotNull(is);
        makeParentDirs(file);
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            int sBufferSize = 8192;
            byte[] data = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
            }
        } finally {
            is.close();
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 将字节数组写入文件
     *
     * @param file  文件
     * @param bytes 字节数组
     * @return 是否写入成功
     */
    public static void writeBytes(File file, byte[] bytes) throws IOException {
        writeBytes(file, bytes, false);
    }

    /**
     * 将字节数组写入文件
     *
     * @param file   文件
     * @param bytes  字节数组
     * @param append 是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static void writeBytes(File file, byte[] bytes, boolean append) throws IOException {
        ExceptionUtils.checkNotNull(file);
        ExceptionUtils.checkNotNull(bytes);
        makeParentDirs(file);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return 是否删除成功
     */
    public static boolean deleteFile(File file) throws Exception {
        return file.delete();
    }

    /**
     * 删除文件夹及文件夹内的文件
     *
     * @param dir 文件夹
     * @return 是否删除成功
     */
    public static boolean deleteDir(File dir) throws Exception {
        if (!executeDeleteDir(dir))
            return false;
        return dir.delete();
    }

    /**
     * 递归执行删除文件夹操作
     */
    private static boolean executeDeleteDir(File dir) throws Exception {
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
}
