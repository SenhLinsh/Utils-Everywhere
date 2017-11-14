package com.linsh.lshutils.tools;

import android.database.Cursor;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 简化 Cursor 操作的帮助类
 * </pre>
 */
public class LshCursorHelper {

    private Cursor mCursor;

    public LshCursorHelper(Cursor cursor) {
        mCursor = cursor;
    }

    /**
     * 获取 Cursor
     */
    public Cursor getCursor() {
        return mCursor;
    }

    /**
     * 获取指定列的 String 值
     *
     * @param column 列名
     * @return 列值
     */
    public String getString(String column) {
        int index = mCursor.getColumnIndex(column);
        return index == -1 ? null : mCursor.getString(index);
    }

    /**
     * 获取指定列的 Integer 值
     *
     * @param column 列名
     * @return 列值
     */
    public Integer getInt(String column) {
        int index = mCursor.getColumnIndex(column);
        return index == -1 ? null : mCursor.getInt(index);
    }

    /**
     * 获取指定列的 Long 值
     *
     * @param column 列名
     * @return 列值
     */
    public Long getLong(String column) {
        int index = mCursor.getColumnIndex(column);
        return index == -1 ? null : mCursor.getLong(index);
    }

    /**
     * 关闭 Cursor
     */
    public void close() {
        mCursor.close();
    }
}
