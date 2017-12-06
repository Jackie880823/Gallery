package com.jackie.gallery;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jackie.gallery.databinding.ItemBucketBinding;
import com.jackie.gallery.model.Bucket;
import com.madxstudio.libs.base.ViewHolder;
import com.madxstudio.libs.tools.image.Configuration;
import com.madxstudio.libs.tools.image.ImageLoader;

import java.util.List;

/**
 * Created on 06/12/2017.
 *
 * @author Jackie
 * @version 1.0
 */

public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.BucketHolder> {
    private Context mContext;
    private List<Bucket> mData;
    private BucketSelectListener selectListener;

    public BucketAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BucketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBucketBinding bucketBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout
                .item_bucket, parent, false);
        bucketBinding.setSelect(selectListener);
        return new BucketHolder(bucketBinding);
    }

    @Override
    public void onBindViewHolder(BucketHolder holder, int position) {
        Bucket bucket = mData.get(position);

        Configuration configuration = new Configuration();
        configuration.uri = bucket.getUri();
        configuration.scaleType = Configuration.CENTER_CROP;
        ImageLoader.display(mContext, holder.binding.image, configuration);
        holder.binding.setBucket(bucket);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setSelectListener(BucketSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public void setData(List<Bucket> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public static class BucketHolder extends ViewHolder<String> {
        final ItemBucketBinding binding;

        public BucketHolder(ItemBucketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
