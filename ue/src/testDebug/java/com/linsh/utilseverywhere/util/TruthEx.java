package com.linsh.utilseverywhere.util;

import com.google.common.truth.ThrowableSubject;
import com.google.common.truth.Truth;

import org.junit.Assert;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/19
 *    desc   :
 * </pre>
 */
public class TruthEx {

    public static ThrowableSubject assertThat(ThrowableRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            return Truth.assertThat(e);
        }
        return Truth.assertThat((Exception) null);
    }

    public static void assertFail() {
        Assert.fail();
    }
}
