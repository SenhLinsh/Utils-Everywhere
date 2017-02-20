package com.linsh.lshutils.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Senh Linsh on 17/1/8.
 */
public class LshListUtils {
    /**
     * 判断集合是否为空
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    /**
     * 反转集合
     */
    public static <V> List<V> invertList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return sourceList;
        }

        List<V> invertList = new ArrayList<V>(sourceList.size());
        for (int i = sourceList.size() - 1; i >= 0; i--) {
            invertList.add(sourceList.get(i));
        }
        return invertList;
    }
}
