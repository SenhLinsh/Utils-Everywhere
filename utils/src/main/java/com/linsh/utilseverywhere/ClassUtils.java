package com.linsh.utilseverywhere;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private ClassUtils() {
    }

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
     * <p>
     * 注意: 该方法无法获取继承于父类的属性, 如果需要获取父类的属性, 请使用 {@link ClassUtils#getField(Class, Object, String)}
     * 来传入包含该方法的字节码对象
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @return 字段的值
     */
    public static Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getField(obj, fieldName, true);
    }

    /**
     * 获取对象指定的字段值(属性值) (通过反射)
     *
     * @param obj        对象
     * @param fieldName  字段名
     * @param accessible 当没有访问权限时, 是否强行反射
     * @return 字段的值
     */
    public static Object getField(Object obj, String fieldName, boolean accessible) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        NoSuchFieldException exception = null;
        while (clazz != null) {
            try {
                return getField(clazz, obj, fieldName, accessible);
            } catch (NoSuchFieldException e) {
                if (exception == null) {
                    exception = e;
                }
                clazz = clazz.getSuperclass();
            }
        }
        if (exception != null)
            throw exception;
        throw new RuntimeException("未知错误");
    }

    /**
     * 获取对象指定的字段值(属性值) (通过反射)
     *
     * @param clazz     该字段所在的字节码对象
     * @param obj       对象
     * @param fieldName 字段名
     * @return 字段的值
     */
    public static Object getField(Class<?> clazz, Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getField(clazz, obj, fieldName, true);
    }

    /**
     * 获取对象指定的字段值(属性值) (通过反射)
     *
     * @param clazz      该字段所在的字节码对象
     * @param obj        对象
     * @param fieldName  字段名
     * @param accessible 当没有访问权限时, 是否强行反射
     * @return 字段的值
     */
    public static Object getField(Class<?> clazz, Object obj, String fieldName, boolean accessible) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(accessible);
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
        setField(obj.getClass(), obj, fieldName, value, true);
    }

    /**
     * 给对象指定的字段设置值 (通过反射)
     *
     * @param obj        对象
     * @param fieldName  字段名
     * @param value      字段值
     * @param accessible 当没有访问权限时, 是否强行反射
     */
    public static void setField(Object obj, String fieldName, Object value, boolean accessible) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        NoSuchFieldException exception = null;
        while (clazz != null) {
            try {
                setField(obj.getClass(), obj, fieldName, value, accessible);
            } catch (NoSuchFieldException e) {
                if (exception == null) {
                    exception = e;
                }
                clazz = clazz.getSuperclass();
            }
        }
        if (exception != null)
            throw exception;
        throw new RuntimeException("未知错误");
    }

    /**
     * 给对象指定的字段设置值 (通过反射)
     *
     * @param clazz     该字段所在的字节码对象
     * @param obj       对象
     * @param fieldName 字段名
     * @param value     字段值
     */
    public static void setField(Class<?> clazz, Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        setField(clazz, obj, fieldName, value, true);
    }

    /**
     * 给对象指定的字段设置值 (通过反射)
     *
     * @param clazz      该字段所在的字节码对象
     * @param obj        对象
     * @param fieldName  字段名
     * @param value      字段值
     * @param accessible 当没有访问权限时, 是否强行反射
     */
    public static void setField(Class<?> clazz, Object obj, String fieldName, Object value, boolean accessible) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(accessible);
        field.set(obj, value);
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param obj        对象
     * @param methodName 方法名
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Object obj, String methodName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        return invokeMethod(obj, methodName, null, true);
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param accessible 当没有访问权限时, 是否强行反射
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Object obj, String methodName, boolean accessible)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        return invokeMethod(obj, methodName, null, accessible);
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param args       方法参数值 (将直接获取值的类型作为方法参数类型)
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Object obj, String methodName, Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        return invokeMethod(obj, methodName, args, true);
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param args       方法参数值 (将直接获取值的类型作为方法参数类型)
     * @param accessible 当没有访问权限时, 是否强行反射
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Object obj, String methodName, Object[] args, boolean accessible)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        if (args == null || args.length == 0)
            return invokeMethod(obj, methodName, null, null, accessible);
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Class<?> clazz = args[i].getClass();
            if (clazz == null)
                throw new IllegalArgumentException("传入的方法参数值不能为空");
            parameterTypes[i] = clazz;
        }
        return invokeMethod(obj, methodName, parameterTypes, args, accessible);
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
        return invokeMethod(obj, methodName, parameterTypes, args, true);
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param obj            对象
     * @param methodName     方法名
     * @param parameterTypes 方法参数类型
     * @param args           方法参数值
     * @param accessible     当没有访问权限时, 是否强行反射
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Object obj, String methodName, Class[] parameterTypes, Object[] args, boolean accessible)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        NoSuchMethodException exception = null;
        while (clazz != null) {
            try {
                return invokeMethod(obj.getClass(), obj, methodName, parameterTypes, args, accessible);
            } catch (NoSuchMethodException e) {
                if (exception == null) {
                    exception = e;
                }
                clazz = clazz.getSuperclass();
            }
        }
        if (exception != null)
            throw exception;
        throw new RuntimeException("未知错误");
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param clazz          该方法所在的字节码对象
     * @param obj            对象
     * @param methodName     方法名
     * @param parameterTypes 方法参数类型
     * @param args           方法参数值
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Class<?> clazz, Object obj, String methodName, Class[] parameterTypes, Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(clazz, obj, methodName, parameterTypes, args, true);
    }

    /**
     * 调用对象指定的方法 (通过反射)
     *
     * @param clazz          该方法所在的字节码对象
     * @param obj            对象
     * @param methodName     方法名
     * @param parameterTypes 方法参数类型
     * @param args           方法参数值
     * @param accessible     当没有访问权限时, 是否强行反射
     * @return 所调用的方法返回的参数
     */
    public static Object invokeMethod(Class<?> clazz, Object obj, String methodName,
                                      Class[] parameterTypes, Object[] args, boolean accessible)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (parameterTypes == null || args == null) {
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(accessible);
            return method.invoke(obj);
        }
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(accessible);
        return method.invoke(obj, args);
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
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments.length > index) {
                return actualTypeArguments[index];
            }
        }
        return null;
    }

}