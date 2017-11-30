package com.jackie.gallery;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackie.gallery.databinding.FragmentPreviewBinding;
import com.jackie.gallery.entities.MediaEntity;
import com.madxstudio.libs.tools.Constants;

public class PreviewPhotoFragment extends Fragment {

    public PreviewPhotoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PreviewPhotoFragment newInstance(MediaEntity media) {
        PreviewPhotoFragment fragment = new PreviewPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_ENTITY, media);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPreviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preview,
                container, false);
        return binding.getRoot();
    }

}
