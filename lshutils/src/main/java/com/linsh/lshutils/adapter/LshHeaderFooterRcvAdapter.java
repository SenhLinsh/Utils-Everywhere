package com.linsh.lshutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.linsh.lshutils.R;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 自动为 RecyclerView 添加头部和尾部的 Adapter
 * </pre>
 */
public abstract class LshHeaderFooterRcvAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H>
        implements View.OnClickListener, View.OnLongClickListener {

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_FOOTER = 2;
    private List<T> data;
    private boolean hasHeader;
    private boolean hasFooter;

    public LshHeaderFooterRcvAdapter(boolean hasHeader, boolean hasFooter) {
        this.hasHeader = hasHeader;
        this.hasFooter = hasFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader && position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (hasFooter && position == getItemCount() - 1) {
            return VIEW_TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        H viewHolder;
        if (viewType == VIEW_TYPE_HEADER) {
            viewHolder = onCreateHeaderViewHolder(parent);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            viewHolder = onCreateFooterViewHolder(parent);
        } else {
            viewHolder = onCreateItemViewHolder(parent, viewType);
        }
        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setOnLongClickListener(this);
        return viewHolder;
    }

    protected abstract H onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected abstract H onCreateHeaderViewHolder(ViewGroup parent);

    protected abstract H onCreateFooterViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(H holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_HEADER) {
            onBindHeaderViewHolder(holder);
            holder.itemView.setTag(R.id.tag_item_view, -1);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            onBindFooterViewHolder(holder);
            holder.itemView.setTag(R.id.tag_item_view, -2);
        } else {
            position = hasHeader ? position - 1 : position;
            onBindItemViewHolder(holder, data.get(position), position);
            holder.itemView.setTag(R.id.tag_item_view, position);
        }
    }

    protected abstract void onBindItemViewHolder(H holder, T t, int position);

    protected abstract void onBindHeaderViewHolder(H holder);

    protected abstract void onBindFooterViewHolder(H holder);

    @Override
    public int getItemCount() {
        return (data == null ? 0 : data.size()) + (hasHeader ? 1 : 0) + (hasFooter ? 1 : 0);
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
            int position = (int) tag;
            if (position == -1) {
                mOnItemClickListener.onHeaderClick(v);
            } else if (position == -2) {
                mOnItemClickListener.onFooterClick(v);
            } else {
                mOnItemClickListener.onItemClick(v, position);
            }
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public boolean hasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

        void onHeaderClick(View itemView);

        void onFooterClick(View itemView);
    }

    @Override
    public boolean onLongClick(View v) {
        Object tag = v.getTag(R.id.tag_item_view);
        if (mOnItemLongClickListener != null && tag != null && tag instanceof Integer) {
            int position = (int) tag;
            if (position == -1) {
                mOnItemLongClickListener.onHeaderLongClick(v);
            } else if (position == -2) {
                mOnItemLongClickListener.onFooterLongClick(v);
            } else {
                mOnItemLongClickListener.onItemLongClick(v, position);
            }
            return true;
        }
        return false;
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);

        void onHeaderLongClick(View itemView);

        void onFooterLongClick(View itemView);
    }
}
