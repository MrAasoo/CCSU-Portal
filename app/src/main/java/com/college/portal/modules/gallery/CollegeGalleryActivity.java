package com.college.portal.modules.gallery;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;

public class CollegeGalleryActivity extends AppCompat {

    private RelativeLayout tvPhotos, tvVideos;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_gallery);


        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.college_gallery));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        tvPhotos = findViewById(R.id.about_photo);
        tvVideos = findViewById(R.id.about_video);

        tvPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CollegeGalleryActivity.this, CollegeGalleryImageListActivity.class));
            }
        });


        tvVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CollegeGalleryActivity.this, CollegeGalleryVideoListActivity.class));
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mInternetBroadcastReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mInternetBroadcastReceiver, mIntentFilter);

        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());
    }

    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}