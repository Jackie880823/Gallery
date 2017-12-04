package com.jackie.gallery;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackie.gallery.databinding.FragmentPreviewBinding;
import com.jackie.gallery.model.MediaEntity;
import com.madxstudio.libs.tools.Constants;
import com.madxstudio.libs.tools.image.Configuration;
import com.madxstudio.libs.tools.image.ImageLoader;

/**
 * @author Jackie
 */
public class PreviewPhotoFragment extends Fragment {

    private FragmentPreviewBinding binding;

    public PreviewPhotoFragment() {
        // Required empty public constructor
    }

    public static PreviewPhotoFragment newInstance(MediaEntity media) {
        PreviewPhotoFragment fragment = new PreviewPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_ENTITY, media);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preview,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MediaEntity media = getArguments().getParcelable(Constants.EXTRA_ENTITY);

        Configuration configuration = new Configuration();
        configuration.scaleType = Configuration.CENTER_CROP;
        configuration.uri = media.getContentUri();
        ImageLoader.display(getContext(), binding.preview, configuration);
    }

}
