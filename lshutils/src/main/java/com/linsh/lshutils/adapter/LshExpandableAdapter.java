package com.linsh.lshutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by linsh on 17/1/25.
 * <p>
 * 可展开RecyclerView的Adapter. 目前只能展开二级, 并且展开某一条目后之前展开的条目会自动关闭
 */

public abstract class LshExpandableAdapter<F, S> extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {

    private List<F> firstLevelData;
    private List<S> secondLevelData;
    private int firstLevelClickPosition = -1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return getSecondLevelHolder(parent);
        }
        return getFirstLevelHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        onBindExpandableViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getFirstLevelDataCount() + getSecondLevelDataCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (getSecondPosition(position) >= 0) {
            return 2;
        }
        return 1;
    }

    protected int getFirstPosition(int position) {
        if (position <= firstLevelClickPosition) {
            return position;
        } else if (position > firstLevelClickPosition + getSecondLevelDataCount()) {
            return position - getSecondLevelDataCount();
        } else {
            return -1;
        }
    }

    protected int getSecondPosition(int position) {
        if (getSecondLevelDataCount() > 0 && position > firstLevelClickPosition
                && position <= firstLevelClickPosition + getSecondLevelDataCount()) {
            return position - firstLevelClickPosition - 1;
        }
        return -1;
    }

    public void setData(List<F> firstLevelData) {
        this.firstLevelData = firstLevelData;
        notifyDataSetChanged();
    }

    protected List<F> getFirstLevelData() {
        return firstLevelData;
    }

    protected List<S> getSecondLevelData() {
        return secondLevelData;
    }

    public abstract List<S> getSecondData(int position);

    private int getFirstLevelDataCount() {
        return firstLevelData == null ? 0 : firstLevelData.size();
    }

    private int getSecondLevelDataCount() {
        return secondLevelData == null ? 0 : secondLevelData.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (getFirstPosition(position) >= 0) {
            boolean expand;
            if (position == firstLevelClickPosition) {
                // 点击已经打开的分组, 清除二级分组数据, 清楚点击位置
                secondLevelData = null;
                firstLevelClickPosition = -1;
                expand = false;
            } else {
                // 点击没有打开过的分组, 先判断该分组是否有数据
                List<S> secondData = getSecondData(getFirstPosition(position));
                if (secondData != null && secondData.size() > 0) {
                    // 有数据则打开该分组, 设置其点击位置
                    firstLevelClickPosition = getFirstPosition(position);
                    secondLevelData = secondData;
                    expand = true;
                } else {
                    // 没有数据则忽略
                    return;
                }
            }
            notifyDataSetChanged();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onFirstLevelItemClick(getFirstPosition(position), expand);
            }
        } else {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onSecondLevelItemClick(getSecondPosition(position));
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        boolean consumed = false;
        int position = (int) v.getTag();
        if (getFirstPosition(position) >= 0) {
            if (mOnItemLongClickListener != null) {
                consumed = mOnItemLongClickListener.onFirstLevelItemLongClick(v, getFirstPosition(position));
            }
        } else {
            if (mOnItemLongClickListener != null) {
                consumed = mOnItemLongClickListener.onSecondLevelItemLongClick(v, getSecondPosition(position));
            }
        }
        return consumed;
    }

    protected abstract RecyclerView.ViewHolder getFirstLevelHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getSecondLevelHolder(ViewGroup parent);

    protected abstract void onBindExpandableViewHolder(RecyclerView.ViewHolder holder, int position);

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onFirstLevelItemClick(int firstLevelPosition, boolean expand);

        void onSecondLevelItemClick(int secondLevelPosition);
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        boolean onFirstLevelItemLongClick(View view, int firstLevelPosition);

        boolean onSecondLevelItemLongClick(View view, int secondLevelPosition);
    }
}
