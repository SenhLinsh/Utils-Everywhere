package com.linsh.lshutils.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: Class 相关 (反射)
 *             如反射获取类实例 / 反射获取类方法以及字段 / 反射调用类方法等
 * </pre>
 */
public class ClassUtils {

    /**
     * 获取类的字节码对象 (反射)
     *
     * @param className 类名
     * @return 类名对应的类的字节码对象, 如果该类不存在或获取失败则返回 null
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
     * 获取无参构造的类的实例 (反射)
     *
     * @param className 类名
     * @return 类实例, 如果该类不存在或获取失败则返回 null
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
     * 获取有参构造的类的实例 (反射)
     *
     * @param className 类名
     * @param args      构造参数值, 构造参数类型直接获取传入的参数的类型
     * @return 类实例, 如果该类不存在或获取失败则返回 null
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
     * 获取有参构造的类的实例 (反射)
     *
     * @param className      类名
     * @param parameterTypes 构造参数类型
     * @param args           构造参数值
     * @return 类实例, 如果该类不存在或获取失败则返回 null
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
     * 获取对象指定的字段值(属性值) (通过反射)
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @return 字段的值
     */
    public static Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 给对象指定的字段设置值 (通过反射)
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @param value     字段值
     */
    public static void setField(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param args       方法参数值 (将直接获取值的类型作为方法参数类型)
     * @return 所调用的方法返回的参数
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
     * 调用对象指定的方法 (通过反射)
     *
     * @param obj            对象
     * @param methodName     方法名
     * @param parameterTypes 方法参数类型
     * @param args           方法参数值
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Object obj, String methodName, Class[] parameterTypes, Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (ArrayUtils.isAnyOneEmpty(parameterTypes, args))
            return obj.getClass().getDeclaredMethod(methodName).invoke(obj);
        return obj.getClass().getDeclaredMethod(methodName, parameterTypes).invoke(obj, args);
    }

    /**
     * 判断指定的类名是否存在
     *
     * @param className 类名
     * @return 是否存在
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
     * 获取指定字节码对象中泛型(首个)的类型
     *
     * @param clazz 字节码对象
     * @return 泛型类型
     */
    public static Type getGenericType(Class<?> clazz) {
        return getGenericType(clazz, 0);
    }

    /**
     * 获取指定字节码对象中第 index 个泛型的类型
     *
     * @param clazz 字节码对象
     * @param index 索引
     * @return 泛型类型
     */
    public static Type getGenericType(Class<?> clazz, int index) {
        return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }

}
