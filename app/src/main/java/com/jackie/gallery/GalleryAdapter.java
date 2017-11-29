package com.jackie.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.madxstudio.libs.base.BaseRecyclerAdapter;

/**
 * Created on 29/11/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class GalleryAdapter extends BaseRecyclerAdapter {
    public GalleryAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
