package com.linsh.utilseverywhere;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/29
 *    desc   :
 * </pre>
 */
@RunWith(AndroidJUnit4.class)
public class DateUtilsInstrumentedTest {

    @org.junit.Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.init(appContext);
    }

}