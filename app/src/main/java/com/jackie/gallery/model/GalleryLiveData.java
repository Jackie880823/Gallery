package com.jackie.gallery.model;

import android.arch.lifecycle.LiveData;

/**
 * Created on 05/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryLiveData extends LiveData<String> implements BucketListener {
    public GalleryLiveData() {}

    @Override
    public void onAddBucket(String bucket) {
        setValue(bucket);
    }

    @Override
    protected void onActive() {
        super.onActive();
        MediaLoader.get().setBucketListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
