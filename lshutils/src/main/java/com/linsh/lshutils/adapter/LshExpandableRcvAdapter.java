package com.linsh.lshutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 可展开RecyclerView的Adapter. 目前只能展开二级, 并且展开某一条目后之前展开的条目会自动关闭
 * </pre>
 */
public abstract class LshExpandableRcvAdapter<F, S> extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {

    public static final int VIEW_TYPE_FIRST_LEVEL = 1;
    public static final int VIEW_TYPE_SECOND_LEVEL = 2;
    private List<F> firstLevelData;
    private List<S> secondLevelData;
    private int mLastFirstLevelClickPosition = -1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SECOND_LEVEL) {
            return getSecondLevelHolder(parent);
        }
        return getFirstLevelHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
            return VIEW_TYPE_SECOND_LEVEL;
        }
        return VIEW_TYPE_FIRST_LEVEL;
    }

    protected int getFirstPosition(int position) {
        if (position <= mLastFirstLevelClickPosition) {
            return position;
        } else if (position > mLastFirstLevelClickPosition + getSecondLevelDataCount()) {
            return position - getSecondLevelDataCount();
        } else {
            return -1;
        }
    }

    protected int getSecondPosition(int position) {
        if (getSecondLevelDataCount() > 0 && position > mLastFirstLevelClickPosition
                && position <= mLastFirstLevelClickPosition + getSecondLevelDataCount()) {
            return position - mLastFirstLevelClickPosition - 1;
        }
        return -1;
    }

    public int getExpandedPosition() {
        return mLastFirstLevelClickPosition;
    }

    protected void setExpandedPosition(int position) {
        mLastFirstLevelClickPosition = position;
    }

    public void setData(List<F> firstLevelData) {
        this.firstLevelData = firstLevelData;
        this.secondLevelData = null;
    }

    public void setData(List<F> firstLevelData, int expandedPosition) {
        if (firstLevelData == null) {
            this.firstLevelData = null;
            this.secondLevelData = null;
            return;
        }

        this.firstLevelData = firstLevelData;
        mLastFirstLevelClickPosition = expandedPosition;
        if (expandedPosition >= 0) {
            this.secondLevelData = getSecondData(expandedPosition);
        } else {
            this.secondLevelData = null;
        }
    }

    public List<F> getData() {
        return firstLevelData;
    }

    public List<F> getFirstLevelData() {
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
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) v.getLayoutParams();
        int position = layoutParams.getViewAdapterPosition();
        int firstPosition = getFirstPosition(position);
        if (firstPosition >= 0) {
            boolean expand;
            if (position == mLastFirstLevelClickPosition) {
                close();
                expand = false;
            } else {
                expand(firstPosition);
                expand = true;
            }
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onFirstLevelItemClick(firstLevelData.get(firstPosition), firstPosition, expand);
            }
        } else {
            if (mOnItemClickListener != null) {
                int secondPosition = getSecondPosition(position);
                mOnItemClickListener.onSecondLevelItemClick(secondLevelData.get(secondPosition), mLastFirstLevelClickPosition, secondPosition);
            }
        }
    }

    // 关闭分组
    public void close() {
        // 清除二级分组数据, 清除点击位置
        int removeSize = secondLevelData == null ? 0 : secondLevelData.size();
        int oldExpandedPosition = mLastFirstLevelClickPosition;
        secondLevelData = null;
        mLastFirstLevelClickPosition = -1;
        if (removeSize > 0) {
            notifyItemRangeRemoved(oldExpandedPosition + 1, removeSize);
        }
    }

    public void expand(int firstPosition) {
        // 点击没有打开过的分组, 先判断该分组是否有数据
        List<S> secondData = getSecondData(firstPosition);
        if (secondData != null && secondData.size() > 0) {
            if (secondLevelData != null && secondLevelData.size() > 0) {
                notifyItemRangeRemoved(mLastFirstLevelClickPosition + 1, secondLevelData.size());
            }
            // 有数据则打开该分组, 设置其点击位置
            mLastFirstLevelClickPosition = firstPosition;
            secondLevelData = secondData;
            notifyItemRangeInserted(mLastFirstLevelClickPosition + 1, secondData.size());
        } else {
            // 没有数据则忽略
            return;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        boolean consumed = false;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) v.getLayoutParams();
        int position = layoutParams.getViewAdapterPosition();
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

    public void setOnItemClickListener(OnItemClickListener<F, S> listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener<F, S> {
        void onFirstLevelItemClick(F firstLevelData, int firstLevelPosition, boolean expand);

        void onSecondLevelItemClick(S SecondLevelData, int firstLevelPosition, int secondLevelPosition);
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
