package com.senh.lshutils.utils;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

/** 集合工具类 */
public class LshListUtils {

	/** default join separator **/
	public static final String DEFAULT_JOIN_SEPARATOR = ",";

	private LshListUtils() {
		throw new AssertionError();
	}
	
	/** 判断集合是否为空 */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }


	/**
	 * 比较两个集合
	 * 
	 * <pre>
	 * isEquals(null, null) = true;
	 * isEquals(new ArrayList&lt;String&gt;(), null) = false;
	 * isEquals(null, new ArrayList&lt;String&gt;()) = false;
	 * isEquals(new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;()) = true;
	 * </pre>
	 * 
	 */
	public static <V> boolean isEquals(ArrayList<V> actual,
			ArrayList<V> expected) {
		if (actual == null) {
			return expected == null;
		}
		if (expected == null) {
			return false;
		}
		if (actual.size() != expected.size()) {
			return false;
		}

		for (int i = 0; i < actual.size(); i++) {
			if (!isEquals(actual.get(i), expected.get(i))) {
				return false;
			}
		}
		return true;
	}

	protected static boolean isEquals(Object actual, Object expected) {
		return actual == expected
				|| (actual == null ? expected == null : actual.equals(expected));
	}

	/** 将集合拼接成字符串，分隔符为"," */
	public static String join(List<String> list) {
		return join(list, DEFAULT_JOIN_SEPARATOR);
	}

	/**
	 * 通过指定的分隔符拼接集合
	 * @param separator 分隔符
	 */
	public static String join(List<String> list, char separator) {
		return join(list, new String(new char[] { separator }));
	}

	/**
	 * 拼接字符串，如果分割符为null，使用默认分割符，见
	 * {@link #DEFAULT_JOIN_SEPARATOR}
	 * 
	 * @param separator 用来分隔集合的
	 */
	public static String join(List<String> list, String separator) {
		return list == null ? "" : TextUtils.join(separator, list);
	}

	/** 反转集合 */
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
