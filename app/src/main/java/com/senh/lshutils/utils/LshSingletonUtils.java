package com.senh.lshutils.utils;

/** 单例工具类，懒汉式，直接继承即可 */
public abstract class LshSingletonUtils<T> {

    private T instance;

    protected abstract T newInstance();

    public final T getInstance() {
        if (instance == null) {
            synchronized (LshSingletonUtils.class) {
                if (instance == null) {
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
