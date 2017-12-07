package com.jackie.gallery;

import android.os.Bundle;

import com.madxstudio.libs.base.BaseActivity;

public class PreviewActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.media_preview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreviewPhotoFragment previewFragment = PreviewPhotoFragment.newInstance(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, previewFragment).commit();
    }
}
