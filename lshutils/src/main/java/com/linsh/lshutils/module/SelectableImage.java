package com.linsh.lshutils.module;

/**
 * Created by Senh Linsh on 17/6/26.
 */

public interface SelectableImage extends Image {

    void setSelected(boolean isSelected);

    boolean isSelected();
}
