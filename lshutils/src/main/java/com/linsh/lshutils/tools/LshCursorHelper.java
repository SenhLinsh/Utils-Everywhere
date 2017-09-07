package com.linsh.lshutils.tools;

import android.database.Cursor;

/**
 * Created by Senh Linsh on 17/9/6.
 */

public class LshCursorHelper {

    private Cursor mCursor;

    public LshCursorHelper(Cursor cursor) {
        mCursor = cursor;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public String getString(String column) {
        int index = mCursor.getColumnIndex(column);
        return index == -1 ? null : mCursor.getString(index);
    }

    public Integer getInt(String column) {
        int index = mCursor.getColumnIndex(column);
        return index == -1 ? null : mCursor.getInt(index);
    }

    public Long getLong(String column) {
        int index = mCursor.getColumnIndex(column);
        return index == -1 ? null : mCursor.getLong(index);
    }

    public void close() {
        mCursor.close();
    }
}
