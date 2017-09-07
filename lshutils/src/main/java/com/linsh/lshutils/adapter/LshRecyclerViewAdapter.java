package com.linsh.lshutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.lshutils.R;

import java.util.List;

/**
 * Created by linsh on 17/4/30.
 */
public abstract class LshRecyclerViewAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H>
        implements View.OnClickListener, View.OnLongClickListener {

    private List<T> data;

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return createViewHolder(view, viewType);
    }

    protected abstract int getLayout();

    protected abstract H createViewHolder(View view, int viewType);

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.itemView.setTag(R.id.tag_item_view, position);
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

    @Override
    public void onClick(View v) {
        Object tag = v.getTag(R.id.tag_item_view);
        if (mOnItemClickListener != null && tag != null && tag instanceof Integer) {
            mOnItemClickListener.onItemClick((Integer) tag);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public boolean onLongClick(View v) {
        Object tag = v.getTag(R.id.tag_item_view);
        if (mOnItemLongClickListener != null && tag != null && tag instanceof Integer) {
            mOnItemLongClickListener.onItemLongClick((Integer) tag);
            return true;
        }
        return false;
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
}
