package com.linsh.utilseverywhere;

import com.google.common.truth.Truth;
import com.linsh.utilseverywhere.util.TestBean;
import com.linsh.utilseverywhere.util.TruthEx;

import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/22
 *    desc   :
 * </pre>
 */
public class ClassUtilsTest {

    @Test
    public void isClassExist() {
        Truth.assertThat(ClassUtils.isClassExist("java.lang.String")).isTrue();
        Truth.assertThat(ClassUtils.isClassExist("java.lang.String0")).isFalse();
    }

    @Test
    public void getClass1() throws Exception {
        Truth.assertThat(ClassUtils.getClass("java.lang.String")).isEqualTo(String.class);
        try {
            ClassUtils.getClass("java.lang.String0");
            TruthEx.assertFail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void newInstance() throws Exception {
        Truth.assertThat(ClassUtils.newInstance("java.lang.String")).isInstanceOf(String.class);
        Truth.assertThat(ClassUtils.newInstance(String.class)).isInstanceOf(String.class);
        Truth.assertThat(ClassUtils.newInstance(String.class, "test")).isEqualTo("test");
        Truth.assertThat(ClassUtils.newInstance(String.class,
                new Class[]{char[].class, int.class, int.class},
                new Object[]{new char[]{'t', 'e', 's', 't'}, 1, 2})).isEqualTo("es");
    }

    @Test
    public void getField() throws Exception {
        MyList2 myList2 = new MyList2();
        StringBuilder builder = new StringBuilder("test");
        Truth.assertThat(ClassUtils.getField(myList2, "i")).isEqualTo(1);
        Truth.assertThat(ClassUtils.getField(builder, "value", true)).isInstanceOf(char[].class);
        Truth.assertThat(ClassUtils.getField(builder, "count", true)).isEqualTo(4);
        Truth.assertThat(ClassUtils.getField(MyList.class, myList2, "k")).isEqualTo(3);
        try {
            Truth.assertThat(ClassUtils.getField(myList2, "j")).isEqualTo(2);
            TruthEx.assertFail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void setField() throws Exception {
        MyList2 myList2 = new MyList2();
        ClassUtils.setField(myList2, "i", myList2.i * 10);
        Truth.assertThat(myList2.i).isEqualTo(10);
        ClassUtils.setField(myList2, "j", 20, true);
        Truth.assertThat(myList2.j).isEqualTo(20);
        ClassUtils.setField(myList2, "k", 30);
        Truth.assertThat(myList2.k).isEqualTo(30);
    }

    @Test
    public void invokeMethod() throws Exception {
        StringBuilder builder = new StringBuilder("test");
        Truth.assertThat(ClassUtils.invokeMethod(builder, "length")).isEqualTo(4);
        ClassUtils.invokeMethod(builder, "append", new Object[]{"1"});
        Truth.assertThat(builder.toString()).isEqualTo("test1");
        ClassUtils.invokeMethod(builder, "insert",
                new Class[]{int.class, char.class}, new Object[]{0, '0'});
        Truth.assertThat(builder.toString()).isEqualTo("0test1");

        TestBean bean = new TestBean();
        Truth.assertThat(ClassUtils.invokeMethod(bean, "getL", true))
                .isEqualTo(100);
        ClassUtils.invokeMethod(bean, "setSuperStr", new Object[]{"666"});
        Truth.assertThat(ClassUtils.invokeMethod(bean, "getSuperStr")).isEqualTo("666");
        ClassUtils.invokeMethod(bean, "setSuperMyStr", new Object[]{"777"}, true);
        Truth.assertThat(ClassUtils.invokeMethod(bean, "getSuperMyStr", true)).isEqualTo("777");

        try {
            Truth.assertThat(ClassUtils.invokeMethod(bean, "getSuperMyStr")).isEqualTo("777");
            TruthEx.assertFail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void getGenericType() {
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return null;
            }
        };
        Truth.assertThat(ClassUtils.getGenericType(MyList.class, ArrayList.class)).isEqualTo(String.class);
        Truth.assertThat(ClassUtils.getGenericType(MyList2.class, ArrayList.class)).isEqualTo(String.class);
        Truth.assertThat(ClassUtils.getGenericType(MyList2.class, MyType.class)).isEqualTo(Long.class);
        Truth.assertThat(ClassUtils.getGenericType(function.getClass(), Function.class)).isEqualTo(String.class);
        Truth.assertThat(ClassUtils.getGenericType(function.getClass(), Function.class, 1)).isEqualTo(Integer.class);
    }

    private interface MyType<T> {
        T getType();
    }

    private static class MyList<T> extends ArrayList<String> implements MyType<T> {
        public int k = 3;

        @Override
        public T getType() {
            return null;
        }

        private int getInt() {
            return 0;
        }
    }

    private static class MyList2 extends MyList<Long> {
        public int i = 1;
        private int j = 2;
    }

}