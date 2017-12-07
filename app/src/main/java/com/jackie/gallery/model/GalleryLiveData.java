package com.jackie.gallery.model;

import android.arch.lifecycle.LiveData;
import android.net.Uri;

import com.jackie.gallery.R;
import com.madxstudio.libs.BaseApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 05/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryLiveData extends LiveData<List<Bucket>> implements BucketListener {

    private MediaLoader mediaLoader;
    private List<Bucket> bucketList;

    public GalleryLiveData() {
    }

    @Override
    public void onAddBuckets(List<Bucket> buckets) {
        Bucket all = new Bucket(BaseApp.getInstance().getString(R.string.media_bucket_all), null);

        moveIndex(buckets, all, 0);
        Bucket video = new Bucket(BaseApp.getInstance().getString(R.string.media_bucket_videos), null);
        moveIndex(buckets, video, 1);
        bucketList = buckets;
        setValue(bucketList);
    }

    private void moveIndex(List<Bucket> buckets, Bucket item, int index) {
        int position = buckets.indexOf(item);
        if (position > -1) {
            item = buckets.get(position);
            buckets.remove(position);
        } else {
            return;
        }

        if (index > -1 && index <= buckets.size()) {
            buckets.add(index, item);
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        mediaLoader = MediaLoader.get();
        mediaLoader.setBucketListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    public void loadBucketGallery(Bucket bucket) {
        for (Bucket entity : bucketList) {
            entity.setSelected(entity.equals(bucket));
        }
        postValue(bucketList);
        mediaLoader.loadGallery(bucket.getBucket());
    }

    public List<Uri> getSelected() {
        List<MediaEntity> selectedList = mediaLoader.getSelectedList();
        List<Uri> uris = new ArrayList<>(selectedList.size());
        for (MediaEntity mediaEntity : selectedList) {
            uris.add(mediaEntity.getContentUri());
        }
        return uris;
    }
}
