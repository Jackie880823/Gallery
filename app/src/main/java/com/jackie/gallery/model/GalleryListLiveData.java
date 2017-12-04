package com.jackie.gallery.model;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 01/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryListLiveData extends LiveData<List<MediaEntity>> implements LoaderListener{
    private static final String TAG = "ImageLiveData";
    private static GalleryListLiveData instance;
    private final LoaderManager manager;
    private FragmentActivity activity;
    private boolean needVideo;
    private List<MediaEntity> entities = new ArrayList<>();
    private final MediaLoader mediaLoader;

    private GalleryListLiveData(FragmentActivity activity, MediaLoader mediaLoader, boolean needVideo) {
        this.activity = activity;
        this.needVideo = needVideo;
        this.mediaLoader = mediaLoader;
        manager = activity.getSupportLoaderManager();
    }

    @MainThread
    public static GalleryListLiveData get(FragmentActivity activity, MediaLoader mediaLoader, boolean needVideo) {
        if (instance == null) {
            instance = new GalleryListLiveData(activity, mediaLoader, needVideo);
        }
        return instance;
    }

    @Override
    protected void onActive() {
        super.onActive();
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
        setValue(data);
    }
}
