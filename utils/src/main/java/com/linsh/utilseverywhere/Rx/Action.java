package com.linsh.utilseverywhere.Rx;

public interface Action<R, P> {

    R call(P p);
}