package com.linsh.lshutils.Rx;

public interface Action<R, P> {

    R call(P p);

}