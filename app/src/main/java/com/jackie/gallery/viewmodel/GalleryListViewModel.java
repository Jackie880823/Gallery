package com.jackie.gallery.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.jackie.gallery.model.GalleryListLiveData;

/**
 * Created on 30/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryListViewModel extends AndroidViewModel{
    GalleryListLiveData galleryListLiveData;
    public GalleryListViewModel(@NonNull Application application) {
        super(application);
    }
}
