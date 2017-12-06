package com.jackie.gallery.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.jackie.gallery.model.GalleryLiveData;

/**
 * Created on 05/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryViewMode extends AndroidViewModel {
    private GalleryLiveData liveData;
    public GalleryViewMode(@NonNull Application application) {
        super(application);
    }

    public GalleryLiveData getData() {
        if (liveData == null) {
            liveData = new GalleryLiveData();
        }
        return liveData;
    }
}