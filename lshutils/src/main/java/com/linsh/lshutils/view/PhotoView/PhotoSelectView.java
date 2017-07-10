package com.linsh.lshutils.view.PhotoView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.linsh.lshutils.R;
import com.linsh.lshutils.module.SelectableImage;
import com.linsh.lshutils.utils.Basic.LshToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Senh Linsh on 17/6/26.
 */

public class PhotoSelectView extends BasePhotoView<SelectableImage> implements AdapterView.OnItemClickListener {

    private int selectedCount;
    private int selectedLimit;

    public PhotoSelectView(Context context) {
        super(context);
        init();
    }

    public PhotoSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SelectableImage image = mAdapter.getData().get(position);
        boolean selected = image.isSelected();
        if (selected) {
            if (selectedCount >= selectedLimit) {
                LshToastUtils.show(String.format("最多能选 %d 张", selectedLimit));
                return;
            }
            selectedCount++;
        } else {
            selectedCount--;
        }
        image.setSelected(!selected);
        mAdapter.notifyDataSetChanged();
    }

    public List<SelectableImage> getSelectedPhotos() {
        ArrayList<SelectableImage> list = new ArrayList<>();
        for (SelectableImage selectableImage : mAdapter.getData()) {
            if (selectableImage.isSelected()) {
                list.add(selectableImage);
            }
        }
        return list;
    }

    @Override
    protected void setView(SelectableImage selectableImage, ViewHolder viewHolder, int position) {
        if (selectableImage.isSelected()) {
            viewHolder.vMask.setVisibility(View.VISIBLE);
            viewHolder.vMask.setBackgroundResource(R.drawable.ic_done_white);
        } else {
            viewHolder.vMask.setVisibility(View.GONE);
            viewHolder.vMask.setBackgroundColor(Color.TRANSPARENT);
        }
        selectableImage.setImage(viewHolder.ivPhoto);
    }
}
