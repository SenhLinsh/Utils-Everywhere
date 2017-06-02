package com.linsh.lshutils.module.unit;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Senh Linsh on 17/6/2.
 */

public class Unit {

    @Retention(SOURCE)
    @IntDef({FileSize.B, FileSize.KB, FileSize.MB, FileSize.GB})
    public @interface FileSizeDef {}
}
