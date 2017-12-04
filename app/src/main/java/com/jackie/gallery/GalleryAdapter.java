package com.jackie.gallery;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jackie.gallery.databinding.GalleryItemBinding;
import com.jackie.gallery.model.MediaEntity;
import com.madxstudio.libs.base.BindRecyclerAdapter;
import com.madxstudio.libs.base.ViewHolder;
import com.madxstudio.libs.tools.LogUtil;
import com.madxstudio.libs.tools.image.Configuration;
import com.madxstudio.libs.tools.image.ImageLoader;

import java.util.List;

/**
 * Created on 29/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryAdapter extends BindRecyclerAdapter<MediaEntity> {
    private static final String TAG = "GalleryAdapter";
    private MediaSelectListener selectListener;
    private MediaClickListener clickListener;

    public GalleryAdapter(Context context) {
        super(context);
    }

    public GalleryAdapter(Context context, List<MediaEntity> mData) {
        super(context, mData);
    }

    @Override
    public GalleryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GalleryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout
                .gallery_item, parent, false);
        if (selectListener != null) {
            binding.setSelect(selectListener);
        }

        if (clickListener != null) {
            binding.setClick(clickListener);
        }
        return new GalleryItemHolder(binding);
    }

    public void setSelectListener(MediaSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public void setClickListener(MediaClickListener clickListener) {
        this.clickListener = clickListener;
    }

    static class GalleryItemHolder extends ViewHolder<MediaEntity> {
        final GalleryItemBinding binding;
        public GalleryItemHolder(GalleryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindEntity(MediaEntity entity) {
            super.bindEntity(entity);
            binding.setMedia(entity);
            Configuration configuration = new Configuration();
            configuration.scaleType = Configuration.CENTER_CROP;
            configuration.uri = entity.getContentUri();
            ImageLoader.display(itemView.getContext(), binding.image, configuration);
            binding.executePendingBindings();
        }
    }
}
