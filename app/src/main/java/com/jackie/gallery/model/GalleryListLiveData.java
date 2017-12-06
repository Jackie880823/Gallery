package com.jackie.gallery.model;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;

import java.util.List;

/**
 * Created on 01/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryListLiveData extends LiveData<List<MediaEntity>> implements LoaderListener {
    private static final String TAG = "ImageLiveData";
    private static GalleryListLiveData instance;
    private LoaderManager manager;
    private boolean needVideo;
    private MediaLoader mediaLoader;

    private GalleryListLiveData() {
        mediaLoader = MediaLoader.get();
    }

    @MainThread
    public static GalleryListLiveData get() {
        if (instance == null) {
            instance = new GalleryListLiveData();
        }
        return instance;
    }

    @Override
    protected void onActive() {
        super.onActive();
        mediaLoader.setLoaderListener(this);
        manager.restartLoader(MediaLoader.IMAGE_LOADER, null, mediaLoader);
        if (needVideo) {
            manager.restartLoader(MediaLoader.VIDEO_LOADER, null, mediaLoader);
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        manager.destroyLoader(MediaLoader.IMAGE_LOADER);
        if (needVideo) {
            manager.destroyLoader(MediaLoader.VIDEO_LOADER);
        }
    }

    @Override
    public void onLoad(List<MediaEntity> data) {
        postValue(data);
    }

    public void setActivity(FragmentActivity activity) {
        manager = activity.getSupportLoaderManager();
    }

    public void setNeedVideo(boolean needVideo) {
        this.needVideo = needVideo;
    }

    public void loadBucket(String bucket) {
        mediaLoader.loadGallery(bucket);
    }
}
