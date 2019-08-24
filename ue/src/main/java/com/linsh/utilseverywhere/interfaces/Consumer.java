package com.linsh.utilseverywhere.interfaces;

public interface Consumer<T> {

    /**
     * 消费给定的值
     *
     * @param t 被消费的值
     * @throws Exception 抛出异常, 终止本次消费
     */
    void accept(T t) throws Exception;
}