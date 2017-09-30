package com.linsh.lshutils.utils;

import com.linsh.lshutils.Rx.Action;

import java.util.ArrayList;
import java.util.Iterator;
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
                String item = getStringAction.call(t);
                if (item != null) {
                    stringList.add(item);
                }
            }
        }
        return stringList;
    }

    public static <T> String joint(List<T> list, String divider) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                builder.append(divider);
            }
            builder.append(list.get(i));
        }
        return builder.toString();
    }

    public static String toString(Iterable iterable) {
        return toString(iterable == null ? null : iterable.iterator());
    }

    public static String toString(Iterator iterator) {
        if (iterator == null) return "null";
        if (!iterator.hasNext()) return "[]";
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (; ; ) {
            Object element = iterator.next();
            builder.append(element);
            if (!iterator.hasNext())
                return builder.append(']').toString();
            builder.append(',').append(' ');
        }
    }
}
