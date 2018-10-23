package com.linsh.utilseverywhere.util;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/23
 *    desc   :
 * </pre>
 */
public class TestSuperBean<T> {

    protected T t;
    protected T myT;
    private String superStr;
    private String superMyStr;

    private void setMyT(T t) {
        this.myT = t;
    }

    private T getMyT() {
        return myT;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public void setSuperStr(String str) {
        this.superStr = str;
    }

    public String getSuperStr() {
        return superStr;
    }

    private String getSuperMyStr() {
        return superMyStr;
    }

    private void setSuperMyStr(String superMyStr) {
        this.superMyStr = superMyStr;
    }
}
