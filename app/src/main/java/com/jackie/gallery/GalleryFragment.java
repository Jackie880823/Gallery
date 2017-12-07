package com.jackie.gallery;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
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
import com.madxstudio.libs.tools.LogUtil;

import java.util.List;

/**
 * @author Jackie
 */
public class GalleryFragment extends Fragment implements MediaClickListener{
    private static final String TAG = "GalleryFragment";
    private FragmentGalleryBinding binding;
    private GalleryAdapter adapter;

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
        GalleryListViewModel viewModel = ViewModelProviders.of(this).get(GalleryListViewModel.class);
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
        // TODO: 04/12/2017 preview the is Media
    }
}
