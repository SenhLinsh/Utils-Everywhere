package com.linsh.lshutils.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Senh Linsh on 16/11/29.
 */
public class LshEditTextUtils {

    public static void setEditTextChangeListener(EditText editText, final View deleteView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                deleteView.setVisibility(editable.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }
}
