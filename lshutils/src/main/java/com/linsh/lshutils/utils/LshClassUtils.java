package com.linsh.lshutils.utils;

/**
 * Created by Senh Linsh on 17/3/14.
 */

public class LshClassUtils {

    /**
     * 反射获取类实例
     */
    public static Class<?> getReflectedClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断类名是否存在
     */
    public static boolean isClassExist(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
