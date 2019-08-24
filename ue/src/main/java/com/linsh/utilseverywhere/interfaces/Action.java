package com.linsh.utilseverywhere.interfaces;

public interface Action<R, P> {

    R call(P p);
}