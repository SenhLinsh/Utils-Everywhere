package com.linsh.utilseverywhere.interfaces;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/02/23
 *    desc   :
 * </pre>
 */
public interface Function<R, P> {

    R call(P p);
}