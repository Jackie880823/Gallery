package com.jackie.gallery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jackie.gallery.model.Bucket;
import com.jackie.gallery.viewmodel.GalleryViewMode;
import com.madxstudio.libs.base.BaseActivity;
import com.madxstudio.libs.tools.LogUtil;

import java.util.List;

/**
 * @author Jackie
 */
public class GalleryActivity extends BaseActivity implements BucketSelectListener {
    private static final String TAG = "GalleryActivity";
    private GalleryViewMode viewMode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, GalleryFragment.newInstance()).commit();
        }

        View headerView = navigationView.getHeaderView(0);
        RecyclerView recyclerView = headerView.findViewById(R.id.recycler);

        final BucketAdapter adapter = new BucketAdapter(this);
        adapter.setSelectListener(this);
//        adapter.addItem(0, new Bucket(getString(R.string.media_bucket_all), null));
        recyclerView.setAdapter(adapter);

        viewMode = ViewModelProviders.of(this).get(GalleryViewMode.class);
        viewMode.getData().observe(this, new Observer<List<Bucket>>() {
            @Override
            public void onChanged(@Nullable List<Bucket> buckets) {
                LogUtil.d(TAG, "changed bucket is " + buckets.size());
                adapter.setData(buckets);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(Bucket bucket) {
        viewMode.selectBucket(bucket);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
