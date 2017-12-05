package com.jackie.gallery.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.jackie.gallery.model.GalleryListLiveData;

/**
 * Created on 30/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryListViewModel extends AndroidViewModel{
    GalleryListLiveData galleryData;
    public GalleryListViewModel(@NonNull Application application) {
        super(application);
    }

    public GalleryListLiveData getGalleryData(FragmentActivity fragmentActivity, boolean needVideo) {
        if (galleryData == null) {
            galleryData = GalleryListLiveData.get();
        }
        galleryData.setActivity(fragmentActivity);
        galleryData.setNeedVideo(needVideo);
        return galleryData;
    }

    public void loadBucket(String bucket) {
        galleryData.loadBucket(bucket);
    }
}
