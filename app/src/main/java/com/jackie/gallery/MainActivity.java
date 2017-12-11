package com.jackie.gallery;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.madxstudio.libs.tools.gallery.GalleryFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY_CODE = 1000;

    private List<Uri> uris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryFactory.init().limitCount(9).limitFileSize(20 * 1024 * 1024).needVideo(true).postHave()
                        .setSelected(uris).fetch(MainActivity.this, REQUEST_GALLERY_CODE);
                uris.clear();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {

            return;
        }
        switch (requestCode) {
            case REQUEST_GALLERY_CODE:
                Uri uri = data.getData();
                if (uri != null) {
                    uris.add(uri);
                } else if (data.getClipData() != null){
                    ClipData clipData = data.getClipData();
                    int itemCount = clipData.getItemCount();
                    for (int i = 0; i < itemCount; i++) {
                        uris.add(clipData.getItemAt(i).getUri());
                    }
                }
                break;
        }
    }
}
