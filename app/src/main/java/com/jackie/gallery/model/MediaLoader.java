package com.jackie.gallery.model;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.jackie.gallery.Constants;
import com.jackie.gallery.R;
import com.madxstudio.libs.BaseApp;
import com.madxstudio.libs.tools.CloseableUtils;
import com.madxstudio.libs.tools.DeviceUtils;
import com.madxstudio.libs.tools.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 04/12/2017.
 * 用于解析从{@link LoaderManager}加载到的图片或视频的{@link Cursor}。这个类为
 *
 * @author Jackie
 * @version 1.0
 */

public class MediaLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * 加载手机中所有图片数据的标识
     */
    static final int IMAGE_LOADER = 10000;
    /**
     * 加载手机中所有视频的数据
     */
    static final int VIDEO_LOADER = 20000;
    private static final String TAG = "MediaLoader";
    private static MediaLoader instance;
    private List<MediaEntity> entities = new ArrayList<>();
    private List<MediaEntity> selectedList = new ArrayList<>();
    private LoaderListener loaderListener;
    private BucketListener bucketListener;
    private Map<String, List<MediaEntity>> mediaMap;
    private String bucketName;

    /**
     * 单个{@link MediaEntity}大小限制
     */
    private long fileLimit = Long.MAX_VALUE;
    /**
     * 选择{@link MediaEntity}的总体大小限制
     */
    private long totalLimit = Long.MAX_VALUE;
    /**
     * 选择{@link MediaEntity}的数量限制
     */
    private int countLimit = Integer.MAX_VALUE;

    private long currentTotal = 0L;

    private MediaLoader() {
        init();
    }

    public static MediaLoader get() {
        if (instance == null) {
            instance = new MediaLoader();
        }
        return instance;
    }

    private void init() {
        mediaMap = new HashMap<>();
        String all = BaseApp.getInstance().getString(R.string.media_bucket_all);
        bucketName = all;
        mediaMap.put(all, entities);
    }

    public void setArgument(@NonNull Bundle argument) {
        fileLimit = argument.getLong(Constants.EXTRACT_FILE_LIMIT, Long.MAX_VALUE);
        totalLimit = argument.getLong(Constants.EXTRACT_TOTAL_LIMIT, Long.MAX_VALUE);
        countLimit = argument.getInt(Constants.EXTRACT_COUNT_LIMIT, Integer.MAX_VALUE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case IMAGE_LOADER:
                return new CursorLoader(BaseApp.getInstance(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                        null, null,
                        null);
            case VIDEO_LOADER:
                return new CursorLoader(BaseApp.getInstance(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                        null, null, null);
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
                entities.addAll(loadImages(data));
                break;
            case VIDEO_LOADER:
                entities.addAll(loadVideos(data));
                break;
            default:
                entities.clear();
        }
        if (TextUtils.isEmpty(bucketName)) {
            return;
        }
        List<MediaEntity> list = mediaMap.get(bucketName);
        if (loaderListener != null && list != null) {
            Collections.sort(list, new MediaEntity.Sort());
            loaderListener.onLoad(list);
        }
        if (bucketListener != null) {
            finishBuckets();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mediaMap.clear();
        entities.clear();
        selectedList.clear();
        init();
    }

    /**
     * 加载图片的URI到内存列表
     */
    private List<MediaEntity> loadImages(Cursor data) {
        if (data == null || data.isClosed()) {
            return new ArrayList<>();
        }
        List<MediaEntity> result = new ArrayList<>(data.getCount());
        int uriColumnIndex = data.getColumnIndex(MediaStore.Images.ImageColumns._ID);
        int bucketColumnIndex = data.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int pathColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);
        int addedDateColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);
        List<MediaEntity> list = new ArrayList<>(data.getCount());
        while (data.moveToNext()) {
            String path;
            String bucket;
            long id;
            long addedDate;
            Uri contentUri;
            path = data.getString(pathColumnIndex);
            bucket = data.getString(bucketColumnIndex);
            id = data.getLong(uriColumnIndex);
            addedDate = data.getLong(addedDateColumnIndex);
            contentUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + File.separator + id);

            if (!TextUtils.isEmpty(path) && !path.contains(DeviceUtils.getDiskCacheDir(BaseApp.getInstance()))) {
                LogUtil.d(TAG, "query a images: " + contentUri);
                MediaEntity mediaData = new MediaEntity(contentUri, path, MediaEntity.TYPE_IMAGE, 0);
                mediaData.setId(id);
                mediaData.setAddedDate(addedDate);
                result.add(mediaData);
                if (!mediaMap.keySet().contains(bucket)) {
                    list = new ArrayList<>();
                    mediaMap.put(bucket, list);
                }
                list.add(mediaData);
            }
        }
        CloseableUtils.close(data);
        return result;
    }

    /**
     * 加载视频的URI到内存列表
     */
    private List<MediaEntity> loadVideos(Cursor data) {
        if (data == null || data.isClosed()) {
            return new ArrayList<>();
        }
        List<MediaEntity> result = new ArrayList<>(data.getCount());
        String bucket = BaseApp.getInstance().getString(R.string.media_bucket_videos);
        mediaMap.put(bucket, result);

        int uriColumnIndex = data.getColumnIndex(MediaStore.Video.VideoColumns._ID);
        int pathColumnIndex = data.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
        int durationColumnIndex = data.getColumnIndex(MediaStore.Video.VideoColumns.DURATION);
        int addedDateColumnIndex = data.getColumnIndex(MediaStore.Video.VideoColumns.DATE_ADDED);

        while (data.moveToNext()) {
            String path;
            Uri contentUri;
            long duration;
            long id;
            long addedDate;

            duration = data.getLong(durationColumnIndex);
            path = data.getString(pathColumnIndex);
            id = data.getLong(uriColumnIndex);
            addedDate = data.getLong(addedDateColumnIndex);
            contentUri = Uri.parse(MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString() + File.separator + id);

            if (!TextUtils.isEmpty(path)) {
                LogUtil.d(TAG, "query a video: " + contentUri);
                MediaEntity mediaData = new MediaEntity(contentUri, path, MediaEntity.TYPE_VIDEO, duration);
                mediaData.setAddedDate(addedDate);
                mediaData.setId(id);
                result.add(mediaData);
            }

        }
        CloseableUtils.close(data);
        return result;
    }

    /**
     * 加载指定{@code bucket}目录下的media
     * @param bucket 目录
     */
    public void loadGallery(String bucket) {
        this.bucketName = bucket;
        List<MediaEntity> list = mediaMap.get(this.bucketName);
        if (list != null) {
            Collections.sort(list, new MediaEntity.Sort());
            loaderListener.onLoad(list);
        }
    }

    public void setLoaderListener(LoaderListener loaderListener) {
        this.loaderListener = loaderListener;
        loadGallery(bucketName);
    }

    public void removeLoaderListener(LoaderListener loaderListener) {
        loaderListener = null;
    }

    /**
     * 设置添加了目录的监听
     *
     * @param bucketListener
     */
    public void setBucketListener(@NonNull BucketListener bucketListener) {
        this.bucketListener = bucketListener;
        finishBuckets();
    }

    private void finishBuckets() {
        Set<String> keySet = mediaMap.keySet();
        List<Bucket> buckets = new ArrayList<>(keySet.size());
        for (String s : keySet) {
            List<MediaEntity> entities = mediaMap.get(s);
            if (!entities.isEmpty()) {
                Bucket bucket = new Bucket(s, entities.get(0).getContentUri());
                bucket.setSelected(s.equals(bucketName));
                buckets.add(bucket);
            }
        }
        bucketListener.onAddBuckets(buckets);
    }

    public boolean selectedMedia(MediaEntity mediaEntity, boolean isSelected) {
        boolean result = false;
        if (!isSelected) {
            result = selectedList.remove(mediaEntity);
            currentTotal = result ? currentTotal - mediaEntity.getFileSize() : currentTotal;
            currentTotal = currentTotal < 0L ? 0L : currentTotal;
        } else if (checkLimit(mediaEntity)) {
            result = selectedList.add(mediaEntity);
            mediaEntity.setSelect(result);

            // 根据添加的结果来计算当前添加的大小
            currentTotal = result ? currentTotal + mediaEntity.getFileSize() : currentTotal;
        }
        return result;
    }

    /**
     * 检测是否超出了限制
     * @param mediaEntity
     * @return
     */
    private boolean checkLimit(MediaEntity mediaEntity) {
        if (selectedList.size() > countLimit) { // 数量超出限制
            return false;
        } else if (mediaEntity.getFileSize() > fileLimit) { // 单个大小超出限制
            return false;
        } else if (currentTotal + mediaEntity.getFileSize() > totalLimit) { // 总的大小超出限制
            return false;
        } else {
            return true;
        }
    }

    public List<MediaEntity> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<Uri> uris) {
        for (Uri uri : uris) {
            addSelectUri(uri);
        }
    }

    private void addSelectUri(Uri uri) {
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setContentUri(uri);
        int indexOf = this.selectedList.indexOf(mediaEntity);
        if (indexOf > -1)  {
            selectedMedia(this.selectedList.get(indexOf), true);
        }
    }
}
