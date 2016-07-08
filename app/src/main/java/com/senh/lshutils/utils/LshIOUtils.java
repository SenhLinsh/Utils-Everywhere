package com.senh.lshutils.utils;

import java.io.Closeable;
import java.io.IOException;

/** 流对象处理的工具类 */
public class LshIOUtils {

    private LshIOUtils() {
        throw new AssertionError();
    }


    /** 关闭流对象 */
	public static void close(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 关闭多个流对象的重载形式 */
	public static void close(Closeable... streams) {
		for (Closeable stream : streams) {
			close(stream);
		}
	}

}
