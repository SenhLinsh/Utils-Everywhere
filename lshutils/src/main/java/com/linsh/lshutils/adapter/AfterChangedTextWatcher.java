package com.linsh.lshutils.adapter;

import android.text.TextWatcher;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 重写 TextWatcher afterTextChanged() 以外的其他方法
 * </pre>
 */
public abstract class AfterChangedTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
