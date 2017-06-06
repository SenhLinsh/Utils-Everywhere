package com.linsh.lshutils.module.unit;

/**
 * Created by Senh Linsh on 17/6/2.
 */

public class FileSize {

    public static final int B = 0;
    public static final int KB = 1;
    public static final int MB = 2;
    public static final int GB = 3;

    public static float format(float size, @Unit.FileSizeDef int srcUnit, @Unit.FileSizeDef int destUnit) {
        return (float) (size * Math.pow(1024L, (srcUnit - destUnit)));
    }

    public static float formatByte(long size, @Unit.FileSizeDef int unit) {
        return format(size, B, unit);
    }
}
