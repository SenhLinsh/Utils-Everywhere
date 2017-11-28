package com.linsh.utilseverywhere.module.unit;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 文件大小的单位
 * </pre>
 */
public class Unit {

    @Retention(SOURCE)
    @IntDef({FileSize.B, FileSize.KB, FileSize.MB, FileSize.GB})
    public @interface FileSizeDef {
    }
}
