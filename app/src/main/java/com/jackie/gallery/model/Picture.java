package com.jackie.gallery.model;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created on 30/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class Picture extends MediaEntity {
    public Picture(boolean isSelect, @NonNull Uri uri) {
        super(isSelect, uri);
    }
}
