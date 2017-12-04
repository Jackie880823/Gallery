package com.jackie.gallery;

import com.jackie.gallery.model.MediaEntity;

/**
 * Created on 04/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public interface MediaSelectListener {
    void select(MediaEntity mediaEntity, boolean isSelected);
}
