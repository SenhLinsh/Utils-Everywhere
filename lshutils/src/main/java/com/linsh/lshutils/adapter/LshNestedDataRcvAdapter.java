package com.linsh.lshutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 为嵌套数据准备的 Adapter 基类, 如 List<List<String>> 加载其中的 String 数据
 * </pre>
 */
public abstract class LshNestedDataRcvAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    private List<T> mFirstLevelData;
    private List<NestedData> mItems;

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false), viewType);
    }

    protected abstract int getLayout();

    protected abstract H getViewHolder(View view, int viewType);

    @Override
    public void onBindViewHolder(H holder, int position) {
        NestedData nestedData = mItems.get(position);

        onBindViewHolder(holder, mFirstLevelData.get(nestedData.firstLevelPosition),
                nestedData.firstLevelPosition, nestedData.secondLevelPosition);
    }

    protected abstract void onBindViewHolder(
            H holder, T firstLevelData, int firstLevelPosition, int secondLevelPosition);

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    protected abstract int getSecondLevelDataSize(int firstLevelPosition);

    public void setData(List<T> data) {
        this.mFirstLevelData = data;

        ArrayList<NestedData> items = new ArrayList<>();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                int size = getSecondLevelDataSize(i);
                for (int j = 0; j < size; j++) {
                    items.add(new NestedData(i, j));
                }
            }
        }
        mItems = items;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mFirstLevelData;
    }

    protected int getFirstLevelPosition(int position) {
        return mItems.get(position).firstLevelPosition;
    }

    protected int getSecondLevelPosition(int position) {
        return mItems.get(position).secondLevelPosition;
    }


    private class NestedData {
        private int firstLevelPosition;
        private int secondLevelPosition;

        private NestedData(int firstLevelPosition, int secondLevelPosition) {
            this.firstLevelPosition = firstLevelPosition;
            this.secondLevelPosition = secondLevelPosition;
        }
    }

}
