package com.linsh.lshutils.view.albumview;

import android.content.Context;
import android.util.AttributeSet;

import com.linsh.lshutils.module.Image;

/**
 * Created by Senh Linsh on 17/6/26.
 */

public class AlbumView extends BaseAlbumView<Image> {

    public AlbumView(Context context) {
        super(context);
    }

    public AlbumView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlbumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setView(Image image, ViewHolder viewHolder, int position) {
        image.setImage(viewHolder.ivPhoto);
    }
}
