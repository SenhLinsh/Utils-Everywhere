package com.linsh.utilseverywhere.util;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/23
 *    desc   :
 * </pre>
 */
public class TestBean extends TestSuperBean<Integer> {

    public int i = 1;
    public String str;
    public final long l = 100;
    private char c;
    private final boolean b = true;

    public TestBean() {
    }

    public TestBean(int i, char c, String str) {
        this.i = i;
        this.str = str;
        this.c = c;
    }

    public int getI() {
        return i;
    }

    private long getL() {
        return l;
    }
}
