package com.linsh.lshutils.utils.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Senh Linsh on 17/1/4.
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter {
    protected RecyclerView mRecyclerView;

    public BaseRecyclerViewAdapter(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    protected View findViewById(int id) {
        return mRecyclerView.findViewById(id);
    }

    protected View findViewWithTag(Object tag) {
        return mRecyclerView.findViewWithTag(tag);
    }
}
