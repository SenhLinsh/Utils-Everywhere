package com.linsh.lshutils.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Senh Linsh on 17/3/14.
 */

public class LshClassUtils {

    /**
     * 获取类的字节码对象
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
     * 获取类实例
     */
    public static Object getInstance(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类实例
     */
    public static Object getInstance(String className, Object... args) {
        try {
            Class<?> clazz = Class.forName(className);
            if (args == null) return clazz.newInstance();
            Class[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
            return clazz.getConstructor(parameterTypes)
                    .newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类实例
     */
    public static Object getInstance(String className, Class[] parameterTypes, Object[] args) {
        try {
            Class<?> clazz = Class.forName(className);
            if (parameterTypes == null || args == null) return clazz.newInstance();
            return clazz.getConstructor(parameterTypes)
                    .newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字段值
     */
    public static Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 设置字段值
     */
    public static void setField(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, String methodName, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (args == null || args.length == 0)
            return obj.getClass().getDeclaredMethod(methodName).invoke(obj);
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        return obj.getClass().getDeclaredMethod(methodName, parameterTypes).invoke(obj, args);
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, String methodName, Class[] parameterTypes, Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (LshArrayUtils.isAnyOneEmpty(parameterTypes, args))
            return obj.getClass().getDeclaredMethod(methodName).invoke(obj);
        return obj.getClass().getDeclaredMethod(methodName, parameterTypes).invoke(obj, args);
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
