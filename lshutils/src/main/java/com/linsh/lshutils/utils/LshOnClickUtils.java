package com.linsh.lshutils.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Senh Linsh on 17/3/8.
 */

public class LshOnClickUtils {

    public static void setOnRecyclerViewClickListener(final RecyclerView recyclerView, final View.OnClickListener listener) {
        LshOnTouchUtils.asOnClick(recyclerView, listener);
    }

}
