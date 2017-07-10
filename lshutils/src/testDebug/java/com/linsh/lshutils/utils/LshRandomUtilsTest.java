package com.linsh.lshutils.utils;

import com.google.common.truth.Truth;

import org.junit.Test;

/**
 * Created by Senh Linsh on 17/7/6.
 */
public class LshRandomUtilsTest {

    @Test
    public void getInt() throws Exception {
        Truth.assertThat(LshRandomUtils.getInt(0)).isEqualTo(0);
        Truth.assertThat(LshRandomUtils.getInt(3)).isAnyOf(0, 1, 2, 3);
        Truth.assertThat(LshRandomUtils.getInt(2, 2)).isEqualTo(2);
        Truth.assertThat(LshRandomUtils.getInt(1, 3)).isAnyOf(1, 2, 3);
        Truth.assertThat(LshRandomUtils.getInt(-2, 0)).isAnyOf(-2, -1, 0);
    }

    @Test
    public void getNumberString() throws Exception {

    }

    @Test
    public void getLetterString() throws Exception {

    }

}