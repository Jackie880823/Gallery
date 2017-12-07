package com.jackie.gallery.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.jackie.gallery.MediaSelectListener;
import com.jackie.gallery.model.GalleryListLiveData;
import com.jackie.gallery.model.MediaEntity;

/**
 * Created on 30/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryListViewModel extends AndroidViewModel implements MediaSelectListener {
    private GalleryListLiveData galleryData;

    public GalleryListViewModel(@NonNull Application application) {
        super(application);
        galleryData = new GalleryListLiveData();
    }

    public GalleryListLiveData getGalleryData(FragmentActivity fragmentActivity) {
        galleryData.setActivity(fragmentActivity);
        return galleryData;
    }

    public MediaSelectListener getSelectListener() {
        return this;
    }

    @Override
    public boolean select(MediaEntity mediaEntity, boolean isSelected) {
        return galleryData.selectedMedia(mediaEntity, isSelected);
    }
}
