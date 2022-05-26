package com.college.portal.activity;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;
import com.squareup.picasso.Picasso;

public class CollegeGalleryImageActivity extends AppCompatActivity {

    // For System ui
    private View decorView;

    //views
    private ImageView galleryImage;
    private TextView imageDescription, postDate;
    private RelativeLayout contentHolder;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;


    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_gallery_image);
        decorView = getWindow().getDecorView();

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);


        // Views
        contentHolder = findViewById(R.id.content_holder);
        galleryImage = findViewById(R.id.gallery_image);
        imageDescription = findViewById(R.id.image_description);
        postDate = findViewById(R.id.post_date);


        postDate.setText(String.format(getString(R.string.post_date_placeholder), getIntent().getStringExtra(AppApi.MEDIA_POST)));
        imageDescription.setText(getIntent().getStringExtra(AppApi.MEDIA_DESCRIPTION));
        Picasso.get().load(getIntent().getStringExtra(AppApi.MEDIA_PATH)).placeholder(R.drawable.place_holder_image).into(galleryImage);

        // hide system ui on image click listener
        galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    decorView.setSystemUiVisibility(hideSystemUI());
                    contentHolder.setVisibility(View.GONE);
                    flag = false;
                } else {
                    decorView.setSystemUiVisibility(showSystemUI());
                    contentHolder.setVisibility(View.VISIBLE);
                    flag = true;
                }
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


    //For hiding system ui
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemUI());
        }
    }

    public int hideSystemUI() {
        return View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }

    public int showSystemUI() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    }

}