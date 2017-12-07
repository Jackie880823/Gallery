package com.jackie.gallery.model;

import android.arch.lifecycle.LiveData;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;

import com.jackie.gallery.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 01/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryListLiveData extends LiveData<List<MediaEntity>> implements LoaderListener {
    private static final String TAG = "ImageLiveData";
    private LoaderManager manager;
    private boolean needVideo;
    private MediaLoader mediaLoader;

    public GalleryListLiveData() {
        mediaLoader = MediaLoader.get();
    }

    @Override
    protected void onActive() {
        super.onActive();
        mediaLoader.setLoaderListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mediaLoader.removeLoaderListener(this);
    }

    @Override
    public void onLoad(List<MediaEntity> data) {
        postValue(data);
    }

    public void setActivity(FragmentActivity activity) {
        Bundle extras = activity.getIntent().getExtras();
        if (extras != null) {
            needVideo = extras.getBoolean(Constants.EXTRACT_NEED_VOIDE);
            mediaLoader.setArgument(extras);
        }

        if (manager == null) {
            manager = activity.getSupportLoaderManager();
            manager.restartLoader(MediaLoader.IMAGE_LOADER, null, mediaLoader);
            if (needVideo) {
                manager.restartLoader(MediaLoader.VIDEO_LOADER, null, mediaLoader);
            }
        }
    }

    public boolean selectedMedia(MediaEntity mediaEntity, boolean isSelected) {
        return mediaLoader.selectedMedia(mediaEntity, isSelected);
    }

    public List<Uri> getSelectedUri() {
        List<MediaEntity> selectedList = mediaLoader.getSelectedList();
        List<Uri> uris = new ArrayList<>(selectedList.size());
        for (MediaEntity mediaEntity : selectedList) {
            uris.add(mediaEntity.getContentUri());
        }
        return uris;
    }

    public void setSelectedUris(List<Uri> selected) {
        mediaLoader.setSelectedList(selected);
    }
}
