package com.linsh.utilseverywhere;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/03/07
 *    desc   : 流操作相关
 * </pre>
 */
public class StreamUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 获取流中的文本
     *
     * @param inputStream 输入流
     * @deprecated 见 {@link StreamUtils#readAsStringQuietly(InputStream)}
     */
    @Deprecated
    public static String getText(InputStream inputStream) {
        return getText(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 获取流中的文本
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     * @deprecated 见 {@link StreamUtils#readAsStringQuietly(InputStream, String)}
     */
    @Deprecated
    public static String getText(InputStream inputStream, String charsetName) {
        return readAsStringQuietly(inputStream, charsetName);
    }

    /**
     * 将流写入文件
     *
     * @param file 文件
     * @param is   输入流
     * @return 是否写入成功
     * @deprecated 见 {@link StreamUtils#writeQuietly(InputStream, File)}
     */
    @Deprecated
    public static boolean saveFile(InputStream is, File file) {
        return FileUtils.writeStream(file, is);
    }

    /**
     * 以文本形式读流
     *
     * @param inputStream 输入流
     */
    public static String readAsString(InputStream inputStream) throws IOException {
        return readAsString(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 以文本形式读流
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     */
    public static String readAsString(InputStream inputStream, String charsetName) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(inputStream, charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (fileContent.length() != 0) {
                    fileContent.append(StringUtils.lineSeparator());
                }
                fileContent.append(line);
            }
            return fileContent.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 以文本形式读流
     *
     * @param inputStream 输入流
     * @return 读取失败时返回 null
     */
    public static String readAsStringQuietly(InputStream inputStream) {
        return readAsStringQuietly(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 以文本形式读流
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     * @return 读取失败时返回 null
     */
    public static String readAsStringQuietly(InputStream inputStream, String charsetName) {
        try {
            return readAsString(inputStream, charsetName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将输入流写入到文件
     *
     * @param inputStream 输入流
     * @param file        目标文件
     */
    public static void write(InputStream inputStream, File file) throws IOException {
        write(inputStream, file, false);
    }

    /**
     * 将输入流写入到文件
     *
     * @param inputStream 输入流
     * @param file        目标文件
     * @param append      是否追加模式
     */
    public static void write(InputStream inputStream, File file, boolean append) throws IOException {
        ExceptionUtils.checkNotNull(inputStream, "inputStream == null");
        ExceptionUtils.checkNotNull(file, "file == null");
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] data = new byte[8 * 1024];
            int len;
            while ((len = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, len);
            }
        } finally {
            try {
                inputStream.close();
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }

    /**
     * 将输入流写入到输出流
     *
     * @param inputStream  输入流
     * @param outputStream 目标文件
     */
    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        ExceptionUtils.checkNotNull(inputStream, "inputStream == null");
        ExceptionUtils.checkNotNull(outputStream, "outputStream == null");
        try {
            byte[] data = new byte[8 * 1024];
            int len;
            while ((len = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, len);
            }
        } finally {
            try {
                inputStream.close();
            } finally {
                outputStream.close();
            }
        }
    }


    /**
     * 将输入流写入到文件
     *
     * @param inputStream 输入流
     * @param file        目标文件
     * @return 是否成功
     */
    public static boolean writeQuietly(InputStream inputStream, File file) {
        return writeQuietly(inputStream, file, false);
    }

    /**
     * 将输入流写入到文件
     *
     * @param inputStream 输入流
     * @param file        目标文件
     * @param append      是否追加模式
     * @return 是否成功
     */
    public static boolean writeQuietly(InputStream inputStream, File file, boolean append) {
        try {
            write(inputStream, file, append);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
