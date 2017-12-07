package com.jackie.gallery.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.jackie.gallery.model.Bucket;
import com.jackie.gallery.model.GalleryLiveData;

import java.util.List;

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

    public void selectBucketGallery(Bucket bucket) {
        liveData.loadBucketGallery(bucket);
    }

    public List<Uri> getSelected() {
       return liveData.getSelected();
    }
}
