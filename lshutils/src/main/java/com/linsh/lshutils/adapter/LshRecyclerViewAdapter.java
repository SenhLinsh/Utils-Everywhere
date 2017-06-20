package com.linsh.lshutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by linsh on 17/4/30.
 */
public abstract class LshRecyclerViewAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    private List<T> data;

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false), viewType);
    }

    protected abstract int getLayout();

    protected abstract H createViewHolder(View view, int viewType);

    @Override
    public void onBindViewHolder(H holder, int position) {
        onBindViewHolder(holder, data.get(position), position);
    }

    protected abstract void onBindViewHolder(H holder, T data, int position);

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

}
