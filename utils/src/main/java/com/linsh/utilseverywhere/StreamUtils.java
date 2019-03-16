package com.linsh.utilseverywhere;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/03/07
 *    desc   :
 * </pre>
 */
public class StreamUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 获取流中的文本
     *
     * @param inputStream 输入流
     */
    public static String getText(InputStream inputStream) {
        return getText(inputStream, DEFAULT_CHARSET);
    }

    /**
     * 获取流中的文本
     *
     * @param inputStream 输入流
     * @param charsetName 字符集
     */
    public static String getText(InputStream inputStream, String charsetName) {
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
        } catch (IOException e) {
            return null;
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
     * 将流写入文件
     *
     * @param file 文件
     * @param is   输入流
     * @return 是否写入成功
     */
    public static boolean saveFile(InputStream is, File file) {
        return FileUtils.writeStream(file, is);
    }
}
