package com.linsh.utilseverywhere.interfaces;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/01/09
 *    desc   :
 * </pre>
 */
public interface Convertible<F, T> {

    T convert(F from);
}
