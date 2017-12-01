package com.jackie.gallery.model;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.jackie.gallery.App;
import com.madxstudio.libs.tools.CloseableUtils;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * Created on 29/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class MediaEntity implements Parcelable {


    /**
     * 封装的数据是图片类型的{@link Uri}和文件路径
     */
    public final static String TYPE_IMAGE = "URI_TYPE_IMAGE";

    /**
     * 封装的数据是视频类型的{@link Uri}和文件路径
     */
    public final static String TYPE_VIDEO = "URI_TYPE_VIDEO";

    private boolean isSelect;


    /**
     * 手机本地储存的多媒体数据的{@link Uri}
     */
    private Uri contentUri;

    /**
     * 手机本地储存的多媒体数据的存储路径
     */
    private String path;

    /**
     * 标识封装多媒体数据的类型:{@link #TYPE_VIDEO}、{@link #TYPE_VIDEO}
     */
    private String type;

    /**
     * 视频的播放时长
     */
    private long duration;

    private long id;

    private long addedDate;

    private Uri thumbnailUri = Uri.EMPTY;

    public MediaEntity() {
    }

    public MediaEntity(Uri contentUri, @NonNull String path, String type, long id) {
        this.contentUri = contentUri;
        this.path = path;
        this.type = type;
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public void setContentUri(Uri contentUri) {
        this.contentUri = contentUri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

    public Uri getThumbnailUri() {
        if (Uri.EMPTY.equals(thumbnailUri)) {
            if (TYPE_VIDEO.equals(type)) {
                thumbnailUri = getVideoThumbnailUri(id);
            } else if (TYPE_IMAGE.equals(type)) {
                thumbnailUri = getImageThumbnailUri(id);
            }
        }
        return thumbnailUri;
    }

    public void setThumbnailUri(Uri thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }


    /**
     * 获取小图的{@link Uri}
     *
     * @param origId 视频{@value android.provider.MediaStore.Video.Media#_ID}
     * @return 返回小图的Uri
     */
    private Uri getVideoThumbnailUri(long origId) {
        Uri result = null;

        Log.i(TAG, "getVideoThumbnailUri& origId: " + origId);
        String[] columns = {MediaStore.Images.Thumbnails.DATA};
        String select = MediaStore.Video.Thumbnails.VIDEO_ID + " = " + origId;
        Cursor cursor = new CursorLoader(App.getContextInstance(), MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                columns, select, null, null).loadInBackground();


        String thumbnailsData = MediaStore.Video.Thumbnails.DATA;
        result =  getThumbnailUri(cursor, thumbnailsData);

        Log.i(TAG, "getVideoThumbnailUri& result: " + result);
        return result;
    }

    /**
     * 获取小图的{@link Uri}
     *
     * @param origId 原图{@value android.provider.MediaStore.Images.Media#_ID}
     * @return 返回小图的Uri
     */
    private Uri getImageThumbnailUri(long origId) {
        Uri result = null;

        Log.i(TAG, "getImageThumbnailUri& origId: " + origId);
        Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(App.getContextInstance().getContentResolver()
                , origId, MediaStore.Images.Thumbnails.MINI_KIND, null);
        String thumbnailsData = MediaStore.Images.Thumbnails.DATA;
        result =  getThumbnailUri(cursor, thumbnailsData);
        Log.i(TAG, "getImageThumbnailUri& result: " + result);
        return result;
    }

    private Uri getThumbnailUri(Cursor cursor, String thumbnailsData) {
        Uri result = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            String path = cursor.getString(cursor.getColumnIndex(thumbnailsData));
            File file = new File(path);
            if (file.exists() && !file.isDirectory() && file.canRead()) {
                result = Uri.parse("file://" + path);
            }
        }

        CloseableUtils.close(cursor);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.contentUri, flags);
        dest.writeString(this.path);
        dest.writeString(this.type);
        dest.writeLong(this.duration);
        dest.writeLong(this.id);
        dest.writeLong(this.addedDate);
        dest.writeParcelable(this.thumbnailUri, flags);
    }

    protected MediaEntity(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.contentUri = in.readParcelable(Uri.class.getClassLoader());
        this.path = in.readString();
        this.type = in.readString();
        this.duration = in.readLong();
        this.id = in.readLong();
        this.addedDate = in.readLong();
        this.thumbnailUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<MediaEntity> CREATOR = new Creator<MediaEntity>() {
        @Override
        public MediaEntity createFromParcel(Parcel source) {
            return new MediaEntity(source);
        }

        @Override
        public MediaEntity[] newArray(int size) {
            return new MediaEntity[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaEntity that = (MediaEntity) o;

        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
