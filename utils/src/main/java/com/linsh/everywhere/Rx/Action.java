package com.linsh.everywhere.Rx;

public interface Action<R, P> {

    R call(P p);
}