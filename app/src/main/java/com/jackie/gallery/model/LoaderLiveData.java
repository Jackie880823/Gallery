package com.jackie.gallery.model;

import android.arch.lifecycle.LiveData;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.MainThread;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.madxstudio.libs.base.BaseActivity;

import java.util.List;

/**
 * Created on 01/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class LoaderLiveData extends LiveData<List<MediaEntity>> implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "ImageLiveData";
    private static final int IMAGE_LOADER = 10000;
    private static final int VIDEO_LOADER = 20000;
    private final LoaderManager manager;
    private BaseActivity activity;
    private boolean needVideo;

    private LoaderLiveData(BaseActivity activity, boolean needVideo) {
        this.activity = activity;
        this.needVideo = needVideo;
        manager = activity.getSupportLoaderManager();
    }

    @MainThread
    public static LoaderLiveData get(BaseActivity activity, boolean needVideo) {
        return new LoaderLiveData(activity, needVideo);
    }

    @Override
    protected void onActive() {
        super.onActive();
        manager.restartLoader(IMAGE_LOADER, null, this);
        if (needVideo) {
            manager.restartLoader(VIDEO_LOADER, null, this);
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        manager.destroyLoader(IMAGE_LOADER);
        if (needVideo) {
            manager.destroyLoader(VIDEO_LOADER);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case IMAGE_LOADER:
                return new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            case VIDEO_LOADER:
                return new CursorLoader(activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            return;
        }
        switch (loader.getId()) {
            case IMAGE_LOADER:

                break;
            case VIDEO_LOADER:
                break;
            default:
                setValue(null);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        setValue(null);
    }

}
