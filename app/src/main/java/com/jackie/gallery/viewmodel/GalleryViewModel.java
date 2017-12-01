package com.jackie.gallery.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

/**
 * Created on 30/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryViewModel extends AndroidViewModel{
    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }
}
