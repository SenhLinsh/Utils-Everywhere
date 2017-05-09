package com.linsh.lshutils.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    /**
     * 获取泛型类型
     */
    public static Type getGenericType(Class<?> clazz) {
        return getGenericType(clazz, 0);
    }

    /**
     * 获取第index个的泛型的类型
     */
    public static Type getGenericType(Class<?> clazz, int index) {
        return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }

}
