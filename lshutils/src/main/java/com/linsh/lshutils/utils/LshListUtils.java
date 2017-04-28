package com.linsh.lshutils.utils;

import com.linsh.lshutils.Rx.Action;

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

    public static <T> List<String> getStringList(List<T> list, Action<String, T> getStringAction) {
        List<String> stringList = new ArrayList<>();

        if (list != null && getStringAction != null) {
            for (T t : list) {
                stringList.add(getStringAction.call(t));
            }
        }
        return stringList;
    }
}
