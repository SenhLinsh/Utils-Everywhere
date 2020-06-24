package com.linsh.utilseverywhere;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
        return readAsStringBuilder(inputStream, charsetName).toString();
    }

    /**
     * 以文本形式读流
     *
     * @param inputStream 输入流
     */
    public static StringBuilder readAsStringBuilder(InputStream inputStream) throws IOException {
        return readAsStringBuilder(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 以文本形式读流
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     */
    public static StringBuilder readAsStringBuilder(InputStream inputStream, String charsetName) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
            String line;
            while ((line = reader.readLine()) != null) {
                if (builder.length() != 0) {
                    builder.append(StringUtils.lineSeparator());
                }
                builder.append(line);
            }
            return builder;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 以文本行形式读流
     *
     * @param inputStream 输入流
     */
    public static List<String> readLines(InputStream inputStream) throws IOException {
        return readLines(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 以文本行形式读流
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     */
    public static List<String> readLines(InputStream inputStream, String charsetName) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
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
     * 以文本形式读流
     *
     * @param inputStream 输入流
     */
    public static StringBuilder readAsStringBuilderQuietly(InputStream inputStream) {
        return readAsStringBuilderQuietly(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 以文本形式读流
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     */
    public static StringBuilder readAsStringBuilderQuietly(InputStream inputStream, String charsetName) {
        try {
            return readAsStringBuilder(inputStream, charsetName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 以文本行形式读流
     *
     * @param inputStream 输入流
     */
    public static List<String> readLinesQuietly(InputStream inputStream) {
        return readLinesQuietly(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 以文本行形式读流
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     */
    public static List<String> readLinesQuietly(InputStream inputStream, String charsetName) {
        try {
            return readLines(inputStream, charsetName);
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
     * @param outputStream 输出流
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
     * 将文本写入到输出流
     *
     * @param content      文本
     * @param outputStream 输出流
     */
    public static void write(String content, OutputStream outputStream) throws IOException {
        try {
            outputStream.write(content.getBytes());
        } finally {
            if (outputStream != null)
                outputStream.close();
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将文本写入到输出流
     *
     * @param content      文本
     * @param outputStream 输出流
     * @return 是否成功
     */
    public static boolean writeQuietly(String content, OutputStream outputStream) {
        try {
            write(content, outputStream);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
