package com.linsh.lshutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LshViewHolder extends RecyclerView.ViewHolder {

    private LshViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    public LshViewHolder(int layout, ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    public abstract void initView(View itemView);
}