package com.jackie.gallery;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackie.gallery.databinding.FragmentGalleryBinding;
import com.jackie.gallery.model.MediaEntity;
import com.jackie.gallery.viewmodel.GalleryListViewModel;
import com.madxstudio.libs.tools.Constants;
import com.madxstudio.libs.tools.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jackie
 */
public class GalleryFragment extends Fragment implements MediaClickListener {
    private static final String TAG = "GalleryFragment";
    private static final int REQUEST_PREVIEW_CODE = 1000;
    private FragmentGalleryBinding binding;
    private GalleryAdapter adapter;
    private GalleryListViewModel viewModel;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GalleryFragment.
     */
    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_PREVIEW_CODE:
                initSelectedUri(data);
                break;
            default:
                break;
        }
    }

    private void initSelectedUri(Intent data) {
        if (data == null) {
            return;
        }

        ClipData clipData = data.getClipData();
        if (clipData == null || clipData.getItemCount() < 1) {
            return;
        }
        List<Uri> selected = new LinkedList<>();
        for (int i = 0; i < clipData.getItemCount(); i++) {
            Uri uri = clipData.getItemAt(i).getUri();
            selected.add(uri);
        }
        viewModel.setSelectedUris(selected);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 3);
        binding.rec.setLayoutManager(manager);
        adapter = new GalleryAdapter(getContext());
        adapter.setClickListener(this);
        binding.rec.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(GalleryListViewModel.class);
        initSelectedUri(getActivity().getIntent());

        adapter.setSelectListener(viewModel.getSelectListener());
        viewModel.getGalleryData(getActivity()).observe(this, new Observer<List<MediaEntity>>() {
            @Override
            public void onChanged(@Nullable List<MediaEntity> mediaEntities) {
                adapter.setData(mediaEntities);
            }
        });
    }

    @Override
    public void onClick(MediaEntity mediaEntity) {
        LogUtil.d(TAG, "click item is " + mediaEntity.getContentUri());
        Intent intent = new Intent(getContext(), PreviewActivity.class);
        intent.putExtra(Constants.EXTRA_ENTITY, mediaEntity);
        startActivityForResult(intent, REQUEST_PREVIEW_CODE);
    }
}
