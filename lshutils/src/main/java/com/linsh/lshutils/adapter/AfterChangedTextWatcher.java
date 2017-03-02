package com.linsh.lshutils.adapter;

import android.text.TextWatcher;

/**
 * Created by Senh Linsh on 16/11/30.
 */
public abstract class AfterChangedTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
