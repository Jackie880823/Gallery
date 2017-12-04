package com.jackie.gallery.model;

import java.util.List;

/**
 * Created on 04/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public interface LoaderListener {
    /**
     * 加载到了数据，这个数据
     * @param data
     */
    void onLoad(List<MediaEntity> data);
}
