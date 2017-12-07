package com.jackie.gallery;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.jackie.gallery.model.MediaEntity;
import com.madxstudio.libs.tools.Constants;
import com.madxstudio.libs.tools.LogUtil;
import com.madxstudio.libs.tools.image.Configuration;
import com.madxstudio.libs.tools.image.ImageLoader;

/**
 * @author Jackie
 */
public class PreviewPhotoFragment extends Fragment {
    private static final String TAG = "PreviewPhotoFragment";
    private MediaEntity media;
    private ImageView preview;
    private VideoView video;

    public PreviewPhotoFragment() {
        // Required empty public constructor
    }

    public static PreviewPhotoFragment newInstance(Bundle bundle) {
        PreviewPhotoFragment fragment = new PreviewPhotoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        preview = view.findViewById(R.id.preview);
        video = view.findViewById(R.id.video);
        MediaController controller = new MediaController(getContext());
        video.setMediaController(controller);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        media = getArguments().getParcelable(Constants.EXTRA_ENTITY);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MediaEntity.TYPE_IMAGE.equals(media.getType())) {
            preview.setVisibility(View.VISIBLE);
            Configuration configuration = new Configuration();
            configuration.uri = media.getContentUri();
            ImageLoader.display(getContext(), preview, configuration);
            LogUtil.d(TAG, "preview is View");
        } else if (MediaEntity.TYPE_VIDEO.equals(media.getType())) {
            video.setVisibility(View.VISIBLE);
            video.setVideoURI(media.getContentUri());
            video.start();
            LogUtil.d(TAG, "video is View");
        } else {
            LogUtil.d(TAG, "video and preview both is not View");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (video.getVisibility() == View.VISIBLE) {
            video.pause();
        }
    }

}
