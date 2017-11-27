package com.linsh.lshutils.utils;

import com.linsh.lshutils.Rx.Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 集合相关
 *             API  : 判空 / 反转 / 拼接 / 转字符串 等
 * </pre>
 */
public class ListUtils {

    /**
     * 判断集合是否为空
     *
     * @param list 集合
     * @return 是否为空
     */
    public static <T> boolean isEmpty(List<T> list) {
        return (list == null || list.size() == 0);
    }

    /**
     * 反转集合
     *
     * @param list 集合
     * @return 反转的新集合
     */
    public static <T> List<T> invertList(List<T> list) {
        if (isEmpty(list)) {
            return list;
        }
        List<T> invertList = new ArrayList<>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            invertList.add(list.get(i));
        }
        return invertList;
    }

    /**
     * 将指定的集合转成 String 集合, 即将集合的元素转成或抽取成 String
     *
     * @param list            源集合
     * @param getStringAction 将元素转成 String 的可执行任务
     * @return 新的 String 集合
     */
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

    /**
     * 拼接集合中所有的元素
     *
     * @param list    集合
     * @param divider 元素之间的连接符
     * @return 拼接后的字符串
     */
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

    /**
     * 将可迭代对象转为字符串表示, 如 [a, b, c]
     *
     * @param iterable 可迭代对象
     * @return 字符串
     */
    public static String toString(Iterable iterable) {
        return toString(iterable == null ? null : iterable.iterator());
    }

    /**
     * 将迭代器对象转为字符串表示, 如 [a, b, c]
     *
     * @param iterator 迭代器对象
     * @return 字符串
     */
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
