package com.linsh.utilseverywhere;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Stack;

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
     * @see ClassUtils#getClass(String)
     */
    @Deprecated
    public static Class<?> getReflectedClass(String className) {
        try {
            return getClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类的字节码对象 (反射)
     *
     * @param className 类名
     * @return 类名对应的类的字节码对象, 如果该类不存在或获取失败则返回 null
     */
    public static Class<?> getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    /**
     * 获取无参构造的类的实例 (反射)
     *
     * @param className 类名
     * @return 类实例, 如果该类不存在或获取失败则返回 null
     */
    @Deprecated
    public static Object getInstance(String className) {
        try {
            return newInstance(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @see ClassUtils#newInstance(Class)
     */
    @Deprecated
    public static Object getInstance(Class<?> clazz) {
        try {
            return newInstance(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @see ClassUtils#newInstance(String, Object...)
     */
    @Deprecated
    public static Object getInstance(String className, Object... args) {
        try {
            return newInstance(className, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @see ClassUtils#newInstance(String, Class[], Object[])
     */
    @Deprecated
    public static Object getInstance(String className, Class[] parameterTypes, Object[] args) {
        try {
            return newInstance(className, parameterTypes, args);
        } catch (Exception e) {
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
    public static Object newInstance(String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(className).newInstance();
    }

    /**
     * 获取无参构造的类的实例 (反射)
     *
     * @param clazz 类
     * @return 类实例, 如果该类不存在或获取失败则返回 null
     */
    public static Object newInstance(Class<?> clazz)
            throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    /**
     * 获取有参构造的类的实例 (反射)
     *
     * @param className 类名
     * @param args      构造参数值, 构造参数类型直接获取传入的参数的类型
     * @return 类实例, 如果该类不存在或获取失败则返回 null
     */
    public static Object newInstance(String className, Object... args)
            throws ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        return newInstance(Class.forName(className), args);
    }

    /**
     * 获取有参构造的类的实例 (反射)
     *
     * @param clazz 类
     * @param args  构造参数值, 构造参数类型直接获取传入的参数的类型
     * @return 类实例, 如果该类不存在或获取失败则返回 null
     */
    public static Object newInstance(Class<?> clazz, Object... args)
            throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (args == null) return clazz.newInstance();
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        return clazz.getConstructor(parameterTypes)
                .newInstance(args);
    }

    /**
     * 获取有参构造的类的实例 (反射)
     *
     * @param className      类名
     * @param parameterTypes 构造参数类型
     * @param args           构造参数值
     * @return 类实例, 如果该类不存在或获取失败则返回 null
     */
    public static Object newInstance(String className, Class[] parameterTypes, Object[] args)
            throws ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        return newInstance(Class.forName(className), parameterTypes, args);
    }

    /**
     * 获取有参构造的类的实例 (反射)
     *
     * @param clazz          类
     * @param parameterTypes 构造参数类型
     * @param args           构造参数值
     * @return 类实例, 如果该类不存在或获取失败则返回 null
     */
    public static Object newInstance(Class<?> clazz, Class[] parameterTypes, Object[] args)
            throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        if (parameterTypes == null || args == null) return clazz.newInstance();
        return clazz.getConstructor(parameterTypes)
                .newInstance(args);
    }

    /**
     * 获取对象指定的字段值(属性值) (通过反射)
     * <p>
     * 注意: 该方法无法获取继承于父类的属性, 如果需要获取父类的属性,
     * 请使用 {@link ClassUtils#getField(Class, Object, String)}
     * 来传入包含该方法的字节码对象
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @return 字段的值
     */
    public static Object getField(Object obj, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(obj, fieldName, false);
    }

    /**
     * 获取对象指定的字段值(属性值) (通过反射)
     *
     * @param obj        对象
     * @param fieldName  字段名
     * @param accessible 当没有访问权限时, 是否强行反射
     * @return 字段的值
     */
    public static Object getField(Object obj, String fieldName, boolean accessible)
            throws NoSuchFieldException, IllegalAccessException {
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
    public static Object getField(Class<?> clazz, Object obj, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(clazz, obj, fieldName, false);
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
    public static Object getField(Class<?> clazz, Object obj, String fieldName, boolean accessible)
            throws NoSuchFieldException, IllegalAccessException {
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
    public static void setField(Object obj, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        setField(obj, fieldName, value, false);
    }

    /**
     * 给对象指定的字段设置值 (通过反射)
     *
     * @param obj        对象
     * @param fieldName  字段名
     * @param value      字段值
     * @param accessible 当没有访问权限时, 是否强行反射
     */
    public static void setField(Object obj, String fieldName, Object value, boolean accessible)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        NoSuchFieldException exception = null;
        while (clazz != null) {
            try {
                setField(clazz, obj, fieldName, value, accessible);
                return;
            } catch (NoSuchFieldException e) {
                if (exception == null) {
                    exception = e;
                }
                clazz = clazz.getSuperclass();
            }
        }
        if (exception != null)
            throw exception;
        throw new IllegalArgumentException("未知错误");
    }

    /**
     * 给对象指定的字段设置值 (通过反射)
     *
     * @param clazz     该字段所在的字节码对象
     * @param obj       对象
     * @param fieldName 字段名
     * @param value     字段值
     */
    public static void setField(Class<?> clazz, Object obj, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        setField(clazz, obj, fieldName, value, false);
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
    public static void setField(Class<?> clazz, Object obj, String fieldName,
                                Object value, boolean accessible)
            throws NoSuchFieldException, IllegalAccessException {
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
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, IllegalArgumentException {
        return invokeMethod(obj, methodName, null, false);
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
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, IllegalArgumentException {
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
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, IllegalArgumentException {
        return invokeMethod(obj, methodName, args, false);
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
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, IllegalArgumentException {
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
        return invokeMethod(obj, methodName, parameterTypes, args, false);
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
                return invokeMethod(clazz, obj, methodName, parameterTypes, args, accessible);
            } catch (NoSuchMethodException e) {
                if (exception == null) {
                    exception = e;
                }
                clazz = clazz.getSuperclass();
            }
        }
        if (exception != null)
            throw exception;
        throw new IllegalArgumentException("未知错误");
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
    public static Object invokeMethod(Class<?> clazz, Object obj, String methodName,
                                      Class[] parameterTypes, Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(clazz, obj, methodName, parameterTypes, args, false);
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
     * 获取指定字节码对象中泛型(首个)的类型
     *
     * @param clazz 字节码对象
     * @return 泛型类型
     */
    @Deprecated
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
    @Deprecated
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

    /**
     * 获取指定字节码对象对于目标字节码的泛型的实现类型
     *
     * @param clazz     字节码对象
     * @param typeClass 目标字节码对象
     * @return 泛型类型, 如果没有实现该泛型则返回 null
     */
    public static <T> Type getGenericType(Class<? extends T> clazz, Class<T> typeClass) {
        return getGenericType(clazz, typeClass, 0);
    }

    /**
     * 获取指定字节码对象对于目标字节码的泛型的实现类型
     *
     * @param clazz     字节码对象
     * @param typeClass 目标字节码对象
     * @param index     泛型在目标字节码对象中的索引
     * @return 泛型类型, 如果没有实现该泛型则返回 null
     */
    public static <T> Type getGenericType(Class<? extends T> clazz, Class<T> typeClass, int index) {
        if (clazz == typeClass) {
            return null;
        }
        TypeVariable[] typeParameters = typeClass.getTypeParameters();
        if (index >= typeParameters.length) {
            return null;
        }
        TypeVariable typeParameter = typeParameters[index];
        Class curClass = clazz;
        Stack<Class> stack = new Stack<>();
        if (typeClass.isInterface()) {
            out:
            while (curClass != null) {
                stack.add(curClass);
                Class<?>[] interfaces = curClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    if (anInterface == typeClass) {
                        Type[] genericInterfaces = curClass.getGenericInterfaces();
                        for (Type genericInterface : genericInterfaces) {
                            if (genericInterface instanceof ParameterizedType
                                    && ((ParameterizedType) genericInterface).getRawType() == typeClass) {
                                Type type = ((ParameterizedType) genericInterface).getActualTypeArguments()[index];
                                if (type instanceof TypeVariable) {
                                    typeParameter = (TypeVariable) type;
                                } else {
                                    return type;
                                }
                            }
                        }
                        break out;
                    }
                }
                curClass = curClass.getSuperclass();
            }
        } else {
            while (curClass != null) {
                stack.add(curClass);
                curClass = curClass.getSuperclass();
                if (curClass == typeClass) {
                    stack.add(curClass);
                    break;
                }
            }
        }
        int curIndex = index;
        while (!stack.isEmpty()) {
            curClass = stack.pop();
            typeParameters = curClass.getTypeParameters();
            for (int i = 0; i < typeParameters.length; i++) {
                if (typeParameters[i] == typeParameter) {
                    curIndex = i;
                }
            }
            if (!stack.isEmpty()) {
                curClass = stack.peek();
                Type genericSuperclass = curClass.getGenericSuperclass();
                if (genericSuperclass instanceof ParameterizedType) {
                    Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[curIndex];
                    if (type instanceof TypeVariable) {
                        typeParameter = (TypeVariable) type;
                    } else {
                        return type;
                    }
                }
            }
        }
        return null;
    }
}