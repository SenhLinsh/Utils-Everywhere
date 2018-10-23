package com.linsh.utilseverywhere;

import com.google.common.truth.Truth;
import com.linsh.utilseverywhere.util.ThrowableRunnable;
import com.linsh.utilseverywhere.util.TruthEx;

import org.junit.Test;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/19
 *    desc   :
 * </pre>
 */
public class ArrayUtilsTest {

    @Test
    public void isEmpty() {
        Truth.assertThat(ArrayUtils.isEmpty(null)).isTrue();
        Truth.assertThat(ArrayUtils.isEmpty(new Integer[0])).isTrue();
        Truth.assertThat(ArrayUtils.isEmpty(new Integer[1])).isFalse();
    }

    @Test
    public void isAllEmpty() {
        Truth.assertThat(ArrayUtils.isAllEmpty(new Integer[0], new Integer[]{})).isTrue();
        Truth.assertThat(ArrayUtils.isAllEmpty(new Integer[0], new Integer[0])).isTrue();
        Truth.assertThat(ArrayUtils.isAllEmpty(new Integer[0], new Integer[]{2})).isFalse();
        Truth.assertThat(ArrayUtils.isAllEmpty(new Integer[2], new Integer[]{2})).isFalse();
    }

    @Test
    public void isAnyOneEmpty() {
        Truth.assertThat(ArrayUtils.isAnyOneEmpty(new String[0])).isTrue();
        Truth.assertThat(ArrayUtils.isAnyOneEmpty(new String[0], new String[3])).isTrue();
        Truth.assertThat(ArrayUtils.isAnyOneEmpty(new String[2], new String[3])).isFalse();
        Truth.assertThat(ArrayUtils.isAnyOneEmpty(new String[3])).isFalse();
    }

    @Test
    public void joint() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                ArrayUtils.joint(null, "");
            }
        }).isInstanceOf(NullPointerException.class);
        Truth.assertThat(ArrayUtils.joint(new String[]{"A", "B", "C"}, "")).isEqualTo("ABC");
        Truth.assertThat(ArrayUtils.joint(new String[]{"a", "b", "c"}, "-")).isEqualTo("a-b-c");
    }

    @Test
    public void joint1() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                ArrayUtils.joint(null, 2, "");
            }
        }).isInstanceOf(NullPointerException.class);
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                ArrayUtils.joint(new String[]{"1"}, -1, "");
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(ArrayUtils.joint(new Integer[]{1, 2, 3}, 1, "-")).isEqualTo("1");
        Truth.assertThat(ArrayUtils.joint(new Boolean[]{false, true, false}, 2, ":")).isEqualTo("false:true");
    }

    @Test
    public void asList() {
        Truth.assertThat(ArrayUtils.asList(new Integer[]{1, 2, 3})).hasSize(3);
        Truth.assertThat(ArrayUtils.asList(new Integer[]{1, 2, 3})).containsExactly(1, 2, 3);
    }

    @Test
    public void toList() {
        Truth.assertThat(ArrayUtils.toList(new Integer[]{1, 2, 3})).hasSize(3);
        Truth.assertThat(ArrayUtils.toList(new Integer[]{1, 2, 3})).containsExactly(1, 2, 3);
    }

    @Test
    public void copy() {
        int[] src = {1, 2, 3};
        int[] src2 = {1, 2, 3, 4, 5, 6, 7};
        int[] dest = new int[5];
        ArrayUtils.copy(src, dest);
        Truth.assertThat(dest).asList().containsExactly(1, 2, 3, 0, 0);
        ArrayUtils.copy(src2, dest);
        Truth.assertThat(dest).asList().containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    public void getCopy() {
        Truth.assertThat(ArrayUtils.getCopy(null)).isNull();
        Truth.assertThat(ArrayUtils.getCopy(new int[]{1, 2, 3})).asList().containsExactly(1, 2, 3);
    }

    @Test
    public void getCopy1() {
        Truth.assertThat(ArrayUtils.getCopy(null)).isNull();
        Truth.assertThat(ArrayUtils.getCopy(new int[]{1, 2, 3}, 2)).asList().containsExactly(1, 2);
    }

    @Test
    public void mergeArrays() {
        Truth.assertThat(ArrayUtils.mergeArrays(String.class, new String[]{"1"}, new String[]{"2"}))
                .asList().containsExactly("1", "2");
        Truth.assertThat(ArrayUtils.mergeArrays(Integer.class, new Integer[]{2}, new Integer[]{1}))
                .asList().containsExactly(2, 1);
    }

    @Test
    public void toString1() {
        Truth.assertThat(ArrayUtils.toString(new int[]{1, 2, 3})).isEqualTo("[1, 2, 3]");
        Truth.assertThat(ArrayUtils.toString(new Integer[]{1, 2, 3})).isEqualTo("[1, 2, 3]");
    }
}