package com.jackie.gallery.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created on 29/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class MediaEntity implements Parcelable {
    private boolean isSelect;
    private Uri uri;

    public MediaEntity(boolean isSelect, @NonNull Uri uri) {
        this.isSelect = isSelect;
        this.uri = uri;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(@NonNull Uri uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaEntity that = (MediaEntity) o;

        return uri.equals(that.uri);
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.uri, flags);
    }

    protected MediaEntity(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Parcelable.Creator<MediaEntity> CREATOR = new Parcelable.Creator<MediaEntity>() {
        @Override
        public MediaEntity createFromParcel(Parcel source) {
            return new MediaEntity(source);
        }

        @Override
        public MediaEntity[] newArray(int size) {
            return new MediaEntity[size];
        }
    };
}
