package com.jackie.gallery;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jackie.gallery.databinding.GalleryItemBinding;

/**
 * Created on 29/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryItemHolder> {


    @Override
    public GalleryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GalleryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout
                .gallery_item, parent, false);
        return new GalleryItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(GalleryItemHolder holder, int position) {
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class GalleryItemHolder extends RecyclerView.ViewHolder {
        final GalleryItemBinding binding;
        public GalleryItemHolder(GalleryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
