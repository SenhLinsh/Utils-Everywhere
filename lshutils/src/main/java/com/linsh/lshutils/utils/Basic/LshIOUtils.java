package com.linsh.lshutils.utils.Basic;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Senh Linsh on 17/1/8.
 * <p>
 * 流对象处理的工具类
 */
public class LshIOUtils {

    /**
     * 关闭流对象
     */
    public static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭多个流对象的重载形式
     */
    public static void close(Closeable... streams) {
        for (Closeable stream : streams) {
            close(stream);
        }
    }

}
