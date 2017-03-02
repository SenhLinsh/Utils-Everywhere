package com.linsh.lshutils.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Senh Linsh on 17/1/8.
 * <p>
 * 单例工具类，懒汉式
 */
public abstract class LshSingletonUtils {

    private static Map<String, Object> instances = new HashMap<>();

    public static <T> T getInstance(Class<T> classOfT) {
        try {
            Object instance = instances.get(classOfT.getName());
            if (instance == null) {
                synchronized (LshSingletonUtils.class) {
                    if (instances.get(classOfT.getName()) == null) {
                        instance = classOfT.newInstance();
                        instances.put(classOfT.getName(), instance);
                    }
                }
            }
            return (T) instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
