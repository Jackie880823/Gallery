package com.jackie.gallery.model;

import android.net.Uri;

/**
 * Created on 06/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class Bucket {
    private String bucket;
    private Uri uri;
    private boolean selected;

    public Bucket(String bucket, Uri uri) {
        this.bucket = bucket;
        this.uri = uri;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bucket bucket1 = (Bucket) o;

        return bucket.equals(bucket1.bucket);
    }

    @Override
    public int hashCode() {
        return bucket.hashCode();
    }
}
